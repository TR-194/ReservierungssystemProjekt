package com.kino.reservierungssystem.mapper;

import com.kino.reservierungssystem.dto.FilmDTO;
import com.kino.reservierungssystem.model.Film;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FilmMapper {
    FilmMapper INSTANCE = Mappers.getMapper(FilmMapper.class);

    FilmDTO fromEntity(Film film);
    Film toEntity(FilmDTO filmDTO);
}