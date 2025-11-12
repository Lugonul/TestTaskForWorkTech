package com.meshakin.mapper;

import com.meshakin.dto.BookDto;
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
    public abstract BookDto toDto(Book book);


    @Mapping(target = "author", source = "bookDto", qualifiedByName = "getAuthor")
    @Mapping(target = "genre", source = "bookDto", qualifiedByName = "getGenre")
    public abstract Book toEntity(BookDto bookDto);


    @Named("getAuthor")
    protected Author getAuthor(BookDto dto) {
        return authorMapper.toEntity(authorService.findByName(dto.authorName()));
    }

    @Named("getGenre")
    protected Genre getGenre(BookDto dto) {
        return genreMapper.toEntity(genreService.findByName(dto.genreName()));
    }

}
