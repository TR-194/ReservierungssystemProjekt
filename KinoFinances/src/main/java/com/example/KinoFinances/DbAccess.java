package com.example.KinoFinances;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DbAccess {
    @Autowired
    private KinoRepository repo;

    public void SavePayment(Payment payment){
        repo.insert(payment);
    }

    public List<Payment> GetAllPayments(){
        return repo.findAll();
    }
}
