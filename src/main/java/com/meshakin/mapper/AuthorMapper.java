package com.meshakin.mapper;

import com.meshakin.dto.AuthorDto;
import com.meshakin.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper{

    AuthorDto toDto(Author author);

    Author toEntity(AuthorDto authorDto);
}
