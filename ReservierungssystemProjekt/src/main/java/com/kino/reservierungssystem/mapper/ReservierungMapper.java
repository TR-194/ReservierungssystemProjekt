package com.kino.reservierungssystem.mapper;

import com.kino.reservierungssystem.dto.ReservierungDTO;
import com.kino.reservierungssystem.model.Reservierung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ReservierungMapperHelper.class)
public interface ReservierungMapper {
    ReservierungMapper INSTANCE = Mappers.getMapper(ReservierungMapper.class);

    @Mapping(source = "kunde.id", target = "kundeId")
    @Mapping(source = "auffuehrung.id", target = "auffuehrungId")
    @Mapping(source = "sitzplaetze", target = "sitzplatzIds", qualifiedByName = "sitzplatzListToIds")
    ReservierungDTO fromEntity(Reservierung reservierung);

    @Mapping(source = "kundeId", target = "kunde.id")
    @Mapping(source = "auffuehrungId", target = "auffuehrung.id")
    @Mapping(source = "sitzplatzIds", target = "sitzplaetze", qualifiedByName = "idsToSitzplatzList")
    Reservierung toEntity(ReservierungDTO reservierungDTO);
}