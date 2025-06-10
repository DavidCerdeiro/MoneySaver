package com.TFG.app.backend.bill.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.TFG.app.backend.bill.repository.BillRepository;
import com.TFG.app.backend.bill.entity.Bill;

@Service
public class BillService {
    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }
}
