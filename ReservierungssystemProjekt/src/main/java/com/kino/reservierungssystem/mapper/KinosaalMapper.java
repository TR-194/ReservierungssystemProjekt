package com.kino.reservierungssystem.mapper;

import com.kino.reservierungssystem.dto.KinosaalDTO;
import com.kino.reservierungssystem.model.Kinosaal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface KinosaalMapper {
    KinosaalMapper INSTANCE = Mappers.getMapper(KinosaalMapper.class);

    @Mapping(source = "sitzreihen", target = "sitzreihen")
    KinosaalDTO fromEntity(Kinosaal kinosaal);

    @Mapping(source = "sitzreihen", target = "sitzreihen")
    Kinosaal toEntity(KinosaalDTO kinosaalDTO);
}