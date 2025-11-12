package com.meshakin.mapper;

import com.meshakin.dto.GenreDto;
import com.meshakin.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDto toDto(Genre genre);


    Genre toEntity(GenreDto genreDto);
}

