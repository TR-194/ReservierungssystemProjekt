package com.kino.reservierungssystem.mapper;

import com.kino.reservierungssystem.dto.AuffuehrungDTO;
import com.kino.reservierungssystem.model.Auffuehrung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuffuehrungMapper {
    AuffuehrungMapper INSTANCE = Mappers.getMapper(AuffuehrungMapper.class);

    @Mapping(source = "film.id", target = "filmId")
    @Mapping(source = "kinosaal.id", target = "kinosaalId")
    @Mapping(source = "preismodell.id", target = "preismodellId")
    AuffuehrungDTO fromEntity(Auffuehrung auffuehrung);

    @Mapping(source = "filmId", target = "film.id")
    @Mapping(source = "kinosaalId", target = "kinosaal.id")
    @Mapping(source = "preismodellId", target = "preismodell.id")
    Auffuehrung toEntity(AuffuehrungDTO auffuehrungDTO);
}