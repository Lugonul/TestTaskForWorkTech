package com.meshakin.unit.service;

import com.meshakin.dto.id.AuthorDtoWithId;
import com.meshakin.dto.without.id.AuthorDtoWithoutId;
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

        AuthorDtoWithoutId authorDtoWithoutId = new AuthorDtoWithoutId( name);
        AuthorDtoWithId savedAuthorDtoWithId = new AuthorDtoWithId(id, name);

        when(authorRepository.save(authorForSave)).thenReturn(authorForSave);
        when(authorMapper.toEntityWithoutId(authorDtoWithoutId)).thenReturn(authorForSave);
        when(authorMapper.toDto(authorForSave)).thenReturn(savedAuthorDtoWithId);

        AuthorDtoWithId result = authorService.create(authorDtoWithoutId);

        assertEquals(savedAuthorDtoWithId, result);

    }

    @Test
    void getAuthorById_whenAuthorExists_thenReturnAuthor() {
        Long id = 1L;
        String name = "test";

        Author author = new Author();
        author.setId(id);
        author.setName(name);

        AuthorDtoWithId authorDtoWithId = new AuthorDtoWithId(id, name);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(authorDtoWithId);

        AuthorDtoWithId result = authorService.read(id);

        assertEquals(authorDtoWithId, result);
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

        AuthorDtoWithId authorDtoWithId1 = new AuthorDtoWithId(1L, "test1");
        AuthorDtoWithId authorDtoWithId2 = new AuthorDtoWithId(2L, "test2");

        List<AuthorDtoWithId> authorDtoWithIds = new ArrayList<>();
        authorDtoWithIds.add(authorDtoWithId1);
        authorDtoWithIds.add(authorDtoWithId2);

        when(authorRepository.findAll()).thenReturn(authors);
        when(authorMapper.toDto(author1)).thenReturn(authorDtoWithId1);
        when(authorMapper.toDto(author2)).thenReturn(authorDtoWithId2);

        List <AuthorDtoWithId> result = authorService.readAll();
        assertEquals(authorDtoWithIds, result);
    }

    @Test
    void readAllAuthors_whenAuthorNotExists_thenReturnEmptyList() {
        List<Author> authors = new ArrayList<>();
        List<AuthorDtoWithId> authorDtoWithIds = new ArrayList<>();
        when(authorRepository.findAll()).thenReturn(authors);

        List <AuthorDtoWithId> result = authorService.readAll();

        assertEquals(authorDtoWithIds, result);
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

        AuthorDtoWithId authorDtoWithIdForUpdate = new AuthorDtoWithId(id, newName);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorMapper.toEntity(authorDtoWithIdForUpdate)).thenReturn(authorForUpdate);
        when(authorMapper.toDto(authorForUpdate)).thenReturn(authorDtoWithIdForUpdate);

        AuthorDtoWithId result = authorService.update(authorDtoWithIdForUpdate);
        assertEquals(authorDtoWithIdForUpdate, result);
    }

    @Test
    void updateAuthor_whenAuthorDoesNotExists_shouldReturnException () {
        Long id = 1L;
        String newName = "newName";

        AuthorDtoWithId authorDtoWithIdForUpdate = new AuthorDtoWithId(id, newName);

        when(authorRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> authorService.update(authorDtoWithIdForUpdate));
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

        AuthorDtoWithId foundAuthorDtoWithId = new AuthorDtoWithId(id, name);

        when(authorRepository.findByName(name)).thenReturn(author);
        when(authorMapper.toDto(author)).thenReturn(foundAuthorDtoWithId);

        AuthorDtoWithId result = authorService.findByName(name);
        assertEquals(foundAuthorDtoWithId, result);
    }

}
