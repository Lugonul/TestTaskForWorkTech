package com.meshakin.unit.service;

import com.meshakin.dto.AuthorDto;
import com.meshakin.entity.Author;
import com.meshakin.mapper.AuthorMapper;
import com.meshakin.repository.AuthorRepository;
import com.meshakin.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    AuthorRepository authorRepository;

    @Mock
    AuthorMapper authorMapper;

    @InjectMocks
    AuthorService authorService;

    @Test
    void createAuthor_shouldReturnAuthor() {
        Long id = 1L;
        String name = "test";

        Author authorForSave = new Author();
        authorForSave.setName(name);

        Author savedAuthor = new Author();
        savedAuthor.setId(id);
        savedAuthor.setName(name);

        AuthorDto authorDtoForSave = new AuthorDto(null, name);
        AuthorDto savedAuthorDto = new AuthorDto(id, name);

        when(authorRepository.save(authorForSave)).thenReturn(authorForSave);
        when(authorMapper.toEntity(authorDtoForSave)).thenReturn(authorForSave);
        when(authorMapper.toDto(authorForSave)).thenReturn(savedAuthorDto);

        AuthorDto result = authorService.create(authorDtoForSave);

        assertEquals(savedAuthorDto, result);

    }

    @Test
    void getAuthorById_whenAuthorExists_thenReturnAuthor() {
        Long id = 1L;
        String name = "test";

        Author author = new Author();
        author.setId(id);
        author.setName(name);

        AuthorDto authorDto = new AuthorDto(id, name);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(authorDto);

        AuthorDto result = authorService.read(id);

        assertEquals(authorDto, result);
    }

    @Test
    void getAuthorById_whenAuthorNotExists_thenReturnException() {
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authorService.read(id));

    }

    @Test
    void readAllAuthors_whenAuthorExists_thenReturnAuthors() {
        List<Author> authors = new ArrayList<>();
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("test1");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("test2");

        authors.add(author1);
        authors.add(author2);

        AuthorDto authorDto1 = new AuthorDto(1L, "test1");
        AuthorDto authorDto2 = new AuthorDto(2L, "test2");

        List<AuthorDto> authorDtos = new ArrayList<>();
        authorDtos.add(authorDto1);
        authorDtos.add(authorDto2);

        when(authorRepository.findAll()).thenReturn(authors);
        when(authorMapper.toDto(author1)).thenReturn(authorDto1);
        when(authorMapper.toDto(author2)).thenReturn(authorDto2);

        List <AuthorDto> result = authorService.readAll();
        assertEquals(authorDtos, result);
    }

    @Test
    void readAllAuthors_whenAuthorNotExists_thenReturnEmptyList() {
        List<Author> authors = new ArrayList<>();
        List<AuthorDto> authorDtos = new ArrayList<>();
        when(authorRepository.findAll()).thenReturn(authors);

        List <AuthorDto> result = authorService.readAll();

        assertEquals(authorDtos, result);
    }

    @Test
    void updateAuthor_whenAuthorExists_shouldReturnUpdatedAuthor() {
        Long id = 1L;
        String name = "test";
        String newName = "newName";

        Author author = new Author();
        author.setId(id);
        author.setName(name);

        Author authorForUpdate = new Author();
        authorForUpdate.setId(id);
        authorForUpdate.setName(newName);

        AuthorDto authorDtoForUpdate = new AuthorDto(id, newName);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorMapper.toEntity(authorDtoForUpdate)).thenReturn(authorForUpdate);
        when(authorMapper.toDto(authorForUpdate)).thenReturn(authorDtoForUpdate);

        AuthorDto result = authorService.update(authorDtoForUpdate);
        assertEquals(authorDtoForUpdate, result);
    }

    @Test
    void updateAuthor_whenAuthorDoesNotExists_shouldReturnException () {
        Long id = 1L;
        String newName = "newName";

        AuthorDto authorDtoForUpdate = new AuthorDto(id, newName);

        when(authorRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> authorService.update(authorDtoForUpdate));
    }

    @Test
    void deleteAuthor_whenAuthorExists_shouldDeleteAuthor() {
        Long id = 1L;
        String name = "test";
        Author author = new Author();
        author.setId(id);
        author.setName(name);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        authorService.delete(author.getId());
    }

    @Test
    void deleteAuthor_whenAuthorDoesNotExists_shouldThrowException() {
        Long id = 1L;
        String name = "test";
        Author author = new Author();
        author.setId(id);
        author.setName(name);

        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> authorService.delete(author.getId()));
    }

    @Test
    void findAuthorByName_whenAuthorExists_shouldReturnAuthor() {
        Long id = 1L;
        String name = "test";

        Author author = new Author();
        author.setId(id);
        author.setName(name);

        AuthorDto foundAuthorDto = new AuthorDto(id, name);

        when(authorRepository.findByName(name)).thenReturn(author);
        when(authorMapper.toDto(author)).thenReturn(foundAuthorDto);

        AuthorDto result = authorService.findByName(name);
        assertEquals(foundAuthorDto, result);
    }

}
