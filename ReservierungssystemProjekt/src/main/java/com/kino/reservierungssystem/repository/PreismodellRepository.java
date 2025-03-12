package com.kino.reservierungssystem.repository;

import com.kino.reservierungssystem.model.Preismodell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreismodellRepository extends JpaRepository<Preismodell, Long> {
}