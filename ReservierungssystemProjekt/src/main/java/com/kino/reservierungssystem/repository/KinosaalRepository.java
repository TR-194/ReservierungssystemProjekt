package com.kino.reservierungssystem.repository;

import com.kino.reservierungssystem.model.Kinosaal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KinosaalRepository extends JpaRepository<Kinosaal, Long> {
}
