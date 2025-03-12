package com.kino.reservierungssystem.repository;

import com.kino.reservierungssystem.model.Sitzplatz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SitzplatzRepository extends JpaRepository<Sitzplatz, Long> {
}
