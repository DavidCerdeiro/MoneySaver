package com.TFG.app.backend.bill.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.TFG.app.backend.bill.repository.BillRepository;
import com.TFG.app.backend.spending.entity.Spending;
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

    public Bill getBillById(int id) {
        return billRepository.findById(id).orElse(null);
    }

    public Bill getBillBySpending(Spending spending) {
        return billRepository.findBySpending(spending).orElse(null);
    }
}
