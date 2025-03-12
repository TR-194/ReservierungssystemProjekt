package com.kino.reservierungssystem.mapper;

import com.kino.reservierungssystem.dto.SitzplatzDTO;
import com.kino.reservierungssystem.model.Sitzplatz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SitzplatzMapper {
    SitzplatzMapper INSTANCE = Mappers.getMapper(SitzplatzMapper.class);

    @Mapping(source = "platzNr", target = "nummer")
    @Mapping(source = "status", target = "status")
    SitzplatzDTO fromEntity(Sitzplatz sitzplatz);

    @Mapping(source = "nummer", target = "platzNr")
    @Mapping(source = "status", target = "status")
    Sitzplatz toEntity(SitzplatzDTO sitzplatzDTO);
}