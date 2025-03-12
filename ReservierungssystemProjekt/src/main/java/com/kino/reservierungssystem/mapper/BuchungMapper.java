package com.kino.reservierungssystem.mapper;

import com.kino.reservierungssystem.dto.BuchungDTO;
import com.kino.reservierungssystem.model.Buchung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = BuchungMapperHelper.class)
public interface BuchungMapper {
    BuchungMapper INSTANCE = Mappers.getMapper(BuchungMapper.class);

    @Mapping(source = "kunde.id", target = "kundeId")
    @Mapping(source = "auffuehrung.id", target = "auffuehrungId")
    @Mapping(source = "sitzplaetze", target = "sitzplatzIds", qualifiedByName = "sitzplatzListToIds")
    @Mapping(source = "zahlung.id", target = "zahlungId")
    BuchungDTO fromEntity(Buchung buchung);

    @Mapping(source = "kundeId", target = "kunde.id")
    @Mapping(source = "auffuehrungId", target = "auffuehrung.id")
    @Mapping(source = "sitzplatzIds", target = "sitzplaetze", qualifiedByName = "idsToSitzplatzList")
    @Mapping(source = "zahlungId", target = "zahlung.id")
    Buchung toEntity(BuchungDTO buchungDTO);
}