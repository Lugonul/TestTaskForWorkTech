package com.meshakin.mapper;

import com.meshakin.dto.id.GenreDtoWithId;
import com.meshakin.dto.without.id.GenreDtoWithoutId;
import com.meshakin.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDtoWithId toDto(Genre genre);


    Genre toEntity(GenreDtoWithId genreDtoWithId);

    Genre toEntityWithoutId(GenreDtoWithoutId genreDtoWithoutId);
}

