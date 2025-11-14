package com.meshakin.unit.controller;

import com.meshakin.controller.AuthorController;
import com.meshakin.dto.id.AuthorDtoWithId;
import com.meshakin.dto.without.id.AuthorDtoWithoutId;
import com.meshakin.entity.Author;
import com.meshakin.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {
    @Mock
    private AuthorService authorService;

    @InjectMocks
    @Spy
    private AuthorController authorController;

    @Test
    void createAuthor_shouldReturnCreatedAuthor() {
        AuthorDtoWithoutId  authorDtoWithoutId = new AuthorDtoWithoutId("test");
        AuthorDtoWithId authorDtoWithId = new AuthorDtoWithId(1L, "test");

        when(authorService.create(authorDtoWithoutId)).thenReturn(authorDtoWithId);

        AuthorDtoWithId result = authorController.saveAuthor(authorDtoWithoutId).getBody();

        Assertions.assertEquals(authorDtoWithId, result);
        verify(authorService).create(authorDtoWithoutId);
    }

    @Test
    void getAuthor_shouldReturnAuthor() {
        Long authorId = 1L;
        AuthorDtoWithId authorDtoWithId = new AuthorDtoWithId(authorId, "test");

        when(authorService.read(authorId)).thenReturn(authorDtoWithId);

        AuthorDtoWithId result = authorController.getAuthorById(authorId);

        Assertions.assertEquals(authorDtoWithId, result);
        verify(authorService).read(authorId);
    }

    @Test
    void getAllAuthors_shouldReturnAuthors() {
        AuthorDtoWithId authorDtoWithId = new AuthorDtoWithId(1L, "test");
        AuthorDtoWithId authorDtoWithId2 = new AuthorDtoWithId(2L, "test2");
        List<AuthorDtoWithId> authors = new ArrayList<>();
        authors.add(authorDtoWithId);
        authors.add(authorDtoWithId2);

        when(authorService.readAll()).thenReturn(authors);

        List<AuthorDtoWithId> result = authorController.getAllAuthors();


        Assertions.assertEquals(authors, result);
        verify(authorService).readAll();
    }

    @Test
    void updateAuthor_shouldReturnUpdatedAuthor() {
        AuthorDtoWithId authorDtoWithId = new AuthorDtoWithId(1L, "test");

        when(authorService.update(authorDtoWithId)).thenReturn(authorDtoWithId);

        AuthorDtoWithId result = authorController.updateAuthor(authorDtoWithId);

        Assertions.assertEquals(authorDtoWithId, result);
        verify(authorService).update(authorDtoWithId);
    }

    @Test
    void deleteAuthor_shouldDeleteAuthor() {
        Long authorId = 1L;

        authorController.deleteAuthor(authorId);

        verify(authorService).delete(authorId);
    }

}
