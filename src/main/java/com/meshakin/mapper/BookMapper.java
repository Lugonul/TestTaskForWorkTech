package com.meshakin.mapper;

import com.meshakin.dto.id.BookDtoWithId;
import com.meshakin.dto.without.id.BookDtoWithoutId;
import com.meshakin.entity.Author;
import com.meshakin.entity.Book;
import com.meshakin.entity.Genre;
import com.meshakin.service.AuthorService;
import com.meshakin.service.GenreService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    AuthorService authorService;
    @Autowired
    AuthorMapper authorMapper;
    @Autowired
    GenreService genreService;
    @Autowired
    GenreMapper genreMapper;

    @Mapping(target = "authorName", source = "author.name")
    @Mapping(target = "genreName", source = "genre.name")
    public abstract BookDtoWithId toDto(Book book);


    @Mapping(target = "author", source = "bookDtoWithId", qualifiedByName = "getAuthor")
    @Mapping(target = "genre", source = "bookDtoWithId", qualifiedByName = "getGenre")
    public abstract Book toEntity(BookDtoWithId bookDtoWithId);

    @Mapping(target = "author", source = "bookDtoWithoutId", qualifiedByName = "getAuthorWithoutId")
    @Mapping(target = "genre", source = "bookDtoWithoutId", qualifiedByName = "getGenreWithoutId")
    public abstract Book toEntityWithoutId(BookDtoWithoutId bookDtoWithoutId);


    @Named("getAuthor")
    protected Author getAuthor(BookDtoWithId dto) {
        return authorMapper.toEntity(authorService.findByName(dto.authorName()));
    }

    @Named("getGenre")
    protected Genre getGenre(BookDtoWithId dto) {
        return genreMapper.toEntity(genreService.findByName(dto.genreName()));
    }

    @Named("getAuthorWithoutId")
    protected Author getAuthorWithoutId(BookDtoWithoutId dto) {
        return authorMapper.toEntity(authorService.findByName(dto.authorName()));
    }

    @Named("getGenreWithoutId")
    protected Genre getGenreWithoutId(BookDtoWithoutId dto) {
        return genreMapper.toEntity(genreService.findByName(dto.genreName()));
    }

}
