package com.example.KinoFinances;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface KinoRepository extends MongoRepository<Payment, String> {

}
