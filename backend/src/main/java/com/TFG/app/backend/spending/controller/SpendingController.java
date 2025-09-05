package com.TFG.app.backend.spending.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.type_periodic.service.Type_PeriodicService;
import com.TFG.app.backend.periodic_spending.service.Periodic_SpendingService;
import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.establishment.service.EstablishmentService;
import com.TFG.app.backend.user.service.UserService;

import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.bill.entity.Bill;
import com.TFG.app.backend.bill.service.BillService;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.category.service.CategoryService;
import com.TFG.app.backend.periodic_spending.entity.Periodic_Spending;
import com.TFG.app.backend.spending.dto.AddSpendingRequest;
import com.TFG.app.backend.spending.dto.AllSpendingFromUserMonthAndYearResponse;
import com.TFG.app.backend.spending.dto.ProcessFileResponse;
import com.TFG.app.backend.infraestructure.config.JwtService;
import com.TFG.app.backend.spending.dto.SpendingResponse;
import com.google.cloud.documentai.v1.GcsDocument;
import com.google.cloud.documentai.v1.Document;
import com.google.cloud.documentai.v1.DocumentProcessorServiceClient;
import com.google.cloud.documentai.v1.DocumentProcessorServiceSettings;
import com.google.cloud.documentai.v1.ProcessRequest;
import com.google.cloud.documentai.v1.ProcessResponse;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/spendings")
public class SpendingController {
    private final SpendingService spendingService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final Type_PeriodicService typePeriodicService;
    private final Periodic_SpendingService periodicSpendingService;
    private final EstablishmentService establishmentService;
    private final JwtService jwtService;
    private final BillService billService;

    @Value("${documentai.project-id}")
    private String documentAiProjectId;

    @Value("${documentai.location}")
    private String documentAiLocation;

    @Value("${documentai.processor-id}")
    private String documentAiProcessorId;
    
    public SpendingController(SpendingService spendingService, UserService userService, CategoryService categoryService, Type_PeriodicService typePeriodicService, Periodic_SpendingService periodicSpendingService, EstablishmentService establishmentService, JwtService jwtService, BillService billService) {
        this.spendingService = spendingService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.typePeriodicService = typePeriodicService;
        this.periodicSpendingService = periodicSpendingService;
        this.establishmentService = establishmentService;
        this.jwtService = jwtService;
        this.billService = billService;
    }

    /**
     * Endpoint to add a new spending
     * @param token
     * @param spendingRequest
     * @throws IOException
     * @return:
     * - 201: Created SpendingResponse If the spending is successfully added
     * - 400: Bad Request If the request is invalid
     * - 401: Unauthorized if token is missing or invalid
     * - 404: Not Found If the user is not found
     */
    @PostMapping
    public ResponseEntity<SpendingResponse> addSpending(@CookieValue(name = "accessToken", required = false) String token, @RequestBody AddSpendingRequest spendingRequest) throws IOException {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       
        Spending spending = new Spending();
        Category category = categoryService.getCategoryFromId(spendingRequest.getIdCategory());

        if(spendingRequest.getEstablishment() != null) {
            if(spendingRequest.getEstablishment().getId() == 0) {
                String nameEstablishment = spendingRequest.getEstablishment().getName().toUpperCase();

                spending.setEstablishment(establishmentService.newEstablishment(nameEstablishment));
            } else {
                spending.setEstablishment(establishmentService.findById(spendingRequest.getEstablishment().getId()));
            }
        }

        spending.setName(spendingRequest.getName());
        spending.setCategory(category);
        spending.setAmount(BigDecimal.valueOf(spendingRequest.getAmount()).setScale(2, RoundingMode.HALF_UP));
        spending.setDate(LocalDate.parse(spendingRequest.getDate()));
        spending.setIsPeriodic(spendingRequest.isPeriodic());
        Spending savedSpending = spendingService.createSpending(spending);

        if(savedSpending != null && spendingRequest.isPeriodic()) {
            Periodic_Spending periodicSpending = new Periodic_Spending();
            periodicSpending.setSpending(savedSpending);
            periodicSpending.setTypePeriodic(typePeriodicService.getTypePeriodicById(spendingRequest.getTypePeriodic()));
            LocalDate expiration = LocalDate.parse(spendingRequest.getExpirationDate());
            periodicSpending.setExpiration(expiration);
            periodicSpending.setLastPayment(expiration);
            periodicSpendingService.createPeriodicSpending(periodicSpending);
        }

        return savedSpending != null
            ? new ResponseEntity<>(new SpendingResponse(savedSpending, null), HttpStatus.CREATED)
            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Endpoint to get all spendings by user, month and year
     * @param token
     * @param month
     * @param year
     * @return:
     * - 200: OK with a list of SpendingResponse if the spendings are successfully retrieved
     * - 401: Unauthorized if token is missing or invalid
     * - 404: Not Found if the user is not found
     */
    @GetMapping("/{year}/{month}")
    public ResponseEntity<AllSpendingFromUserMonthAndYearResponse> getSpendings(
        @CookieValue(name = "accessToken", required = false) String token,
        @PathVariable("month") int month,
        @PathVariable("year") int year) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Spending> spendings = spendingService.getAllSpendingsByUserMonthAndYear(user.getId().intValue(), month, year);
        List<SpendingResponse> spendingResponses = new ArrayList<>();

        LocalDate date = LocalDate.of(year, month, 1);
        for (Spending spending : spendings) {
            boolean isDeleted = spending.getCategory().getDeletedAt() != null &&
                   !spending.getCategory().getDeletedAt().isAfter(date);
            String categoryName = isDeleted ? "Deleted" : spending.getCategory().getName();
            String categoryIcon = isDeleted ? "x" : spending.getCategory().getIcon();
            Bill bill = billService.getBillBySpending(spending);
            spendingResponses.add(new SpendingResponse(
                    spending.getId(),
                    spending.getName(),
                    spending.getAmount(),
                    spending.getDate(),
                    categoryName,
                    categoryIcon,
                    spending.getIsPeriodic(),
                    spending.getEstablishment() != null ? spending.getEstablishment().getName() : "",
                    bill != null ? bill.getId() : null
            ));
        }

        return new ResponseEntity<>(new AllSpendingFromUserMonthAndYearResponse(spendingResponses), HttpStatus.OK);
    }

    /**
     * Endpoint to process a file
     * @param token
     * @param fileContent
     * @throws IOException 
     * @return:
     * - 200: OK ProcessFileResponse if the file is successfully processed
     * - 401: Unauthorized if the user is not authenticated
     * - 404: Not Found if the user is not found
     */
    @PostMapping("/documents")
    public ResponseEntity<ProcessFileResponse> processFile(
        @CookieValue(name = "accessToken", required = false) String token,
        @RequestParam("file") MultipartFile fileContent) throws IOException {

        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Upload file to Google Cloud Storage
        String bucketName = "filesmoneysaver";
        String gcsFileName = "uploads/" + UUID.randomUUID() + "-" + fileContent.getOriginalFilename();

        // Create a new blob in Google Cloud Storage
        // A blob is an object stored in Google Cloud Storage
        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobId blobId = BlobId.of(bucketName, gcsFileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
            .setContentType(fileContent.getContentType())
            .build();

        // Upload the file content to the newly created blob
        storage.create(blobInfo, fileContent.getBytes());
        String gcsUri = String.format("gs://%s/%s", bucketName, gcsFileName);

        // Process the file with Document AI
        String processorName = String.format("projects/%s/locations/%s/processors/%s", documentAiProjectId, documentAiLocation, documentAiProcessorId);

        String endpoint = String.format("%s-documentai.googleapis.com:443", documentAiLocation);
        DocumentProcessorServiceSettings settings = DocumentProcessorServiceSettings.newBuilder()
            .setEndpoint(endpoint)
            .build();

        // Create a Document AI client
        DocumentProcessorServiceClient client = DocumentProcessorServiceClient.create(settings);
        ProcessRequest request = ProcessRequest.newBuilder()
            .setName(processorName)
            .setGcsDocument(GcsDocument.newBuilder()
                .setGcsUri(gcsUri)
                .setMimeType(fileContent.getContentType())
                .build()
            ).build();

        ProcessResponse response = client.processDocument(request);
        Document document = response.getDocument();

        String supplierNameStr = null;
        String receiptDateStr = null;
        String totalAmountStr = null;

        // Extract relevant information from the document
        for (Document.Entity entity : document.getEntitiesList()) {
            switch (entity.getType()) {
                case "supplier_name" -> supplierNameStr = entity.getMentionText();
                case "receipt_date" -> receiptDateStr = entity.getMentionText();
                case "total_amount" -> totalAmountStr = entity.getMentionText();
            }
        }

        if (supplierNameStr != null && !supplierNameStr.isEmpty()) {
            supplierNameStr = supplierNameStr.toUpperCase();
        }
        Establishment establishment = establishmentService.findByName(supplierNameStr);
        Category category = null;
        if(establishment != null){
            category = spendingService.getCategoryByEstablishment(establishment.getId());
        }

        LocalDate parsedDate = spendingService.parseFlexibleDate(receiptDateStr);

        Float parsedAmount = null;
        if (totalAmountStr != null && !totalAmountStr.isEmpty()) {
            parsedAmount = Float.parseFloat(totalAmountStr.replace(",", "."));
        }

        ProcessFileResponse result = new ProcessFileResponse(
            supplierNameStr,
            parsedDate,
            parsedAmount,
            establishment != null ? establishment.getId() : 0,
            supplierNameStr,
            category != null ? category.getId() : null
        );
        return ResponseEntity.ok(result);
    }
}
    

