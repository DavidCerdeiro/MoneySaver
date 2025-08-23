package com.TFG.app.backend.bill.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.TFG.app.backend.bill.entity.Bill;
import com.TFG.app.backend.bill.service.BillService;
import com.TFG.app.backend.infraestructure.config.JwtService;
import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.service.UserService;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;
    private final SpendingService spendingService;
    private final JwtService jwtService;
    private final UserService userService;
    public BillController(BillService billService, SpendingService spendingService, JwtService jwtService, UserService userService) {
        this.billService = billService;
        this.spendingService = spendingService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<Bill> addBill(@CookieValue(name = "accessToken", required = false) String token,
            @RequestParam("file") MultipartFile fileContent, @RequestParam("idSpending") Integer idSpending) {

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
        try{
            String bucketName = "filesmoneysaver";
            String gcsFileName = "bills/" + UUID.randomUUID() + "-" + fileContent.getOriginalFilename();

            Storage storage = StorageOptions.getDefaultInstance().getService();
            BlobId blobId = BlobId.of(bucketName, gcsFileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(fileContent.getContentType())
                    .build();

            storage.create(blobInfo, fileContent.getBytes());
            String gcsUri = String.format("gs://%s/%s", bucketName, gcsFileName);

            Bill bill = new Bill();
            bill.setSpending(spendingService.getSpendingById(idSpending));
            bill.setFileRoute(gcsUri);
            billService.createBill(bill);

            return ResponseEntity.ok(bill);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }

        
    }
}
