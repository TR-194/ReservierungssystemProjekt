package com.kino.reservierungssystem.mapper;

import com.kino.reservierungssystem.model.Sitzplatz;
import com.kino.reservierungssystem.repository.SitzplatzRepository;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuchungMapperHelper {

    @Autowired
    private SitzplatzRepository sitzplatzRepository;

    @Named("sitzplatzListToIds")
    public List<Long> sitzplatzListToIds(List<Sitzplatz> sitzplaetze) {
        return sitzplaetze.stream().map(Sitzplatz::getId).collect(Collectors.toList());
    }

    @Named("idsToSitzplatzList")
    public List<Sitzplatz> idsToSitzplatzList(List<Long> ids) {
        return sitzplatzRepository.findAllById(ids);
    }
}