package com.meshakin.mapper;

import com.meshakin.dto.id.AuthorDtoWithId;
import com.meshakin.dto.without.id.AuthorDtoWithoutId;
import com.meshakin.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper{

    AuthorDtoWithId toDto(Author author);

    Author toEntity(AuthorDtoWithId authorDtoWithId);

    Author toEntityWithoutId(AuthorDtoWithoutId authorDtoWithoutId);
}
