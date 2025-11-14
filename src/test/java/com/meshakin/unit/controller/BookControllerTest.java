package com.meshakin.unit.controller;

import com.meshakin.controller.BookController;
import com.meshakin.dto.id.BookDtoWithId;
import com.meshakin.dto.without.id.BookDtoWithoutId;
import com.meshakin.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    @Mock
    private BookService bookService;

    @Spy
    @InjectMocks
    private BookController bookController;

    @Test
    void createBook_shouldReturnCreatedBook() {
        BookDtoWithoutId bookDtoWithoutId = new BookDtoWithoutId("test", "authorTest", "genreTest");
        BookDtoWithId bookDtoWithId = new BookDtoWithId(1L, "test", "authorTest", "genreTest");

        when(bookService.create(bookDtoWithoutId)).thenReturn(bookDtoWithId);
        doReturn("test").when(bookController).getCurrentUsername();

        BookDtoWithId result = bookController.saveBook(bookDtoWithoutId).getBody();

        Assertions.assertEquals(bookDtoWithId, result);
        verify(bookService).create(bookDtoWithoutId);
    }

    @Test
    void getBook_shouldReturnBook() {
        Long bookId = 1L;
        BookDtoWithId bookDtoWithId = new BookDtoWithId(bookId, "test", "authorTest", "genreTest");

        when(bookService.read(bookId)).thenReturn(bookDtoWithId);
        doReturn("test").when(bookController).getCurrentUsername();

        BookDtoWithId result = bookController.getBookById(bookId);

        Assertions.assertEquals(bookDtoWithId, result);
        verify(bookService).read(bookId);
    }

    @Test
    void getAllBooks_shouldReturnBooks() {
        BookDtoWithId bookDtoWithId = new BookDtoWithId(1L, "test", "authorTest", "genreTest");
        BookDtoWithId bookDtoWithId2 = new BookDtoWithId(2L, "test2", "authorTest2", "genreTest2");
        List<BookDtoWithId> books = new ArrayList<>();
        books.add(bookDtoWithId);
        books.add(bookDtoWithId2);

        when(bookService.readAll()).thenReturn(books);
        doReturn("test").when(bookController).getCurrentUsername();

        List<BookDtoWithId> result = bookController.getAllBooks();


        Assertions.assertEquals(books, result);
        verify(bookService).readAll();
    }

    @Test
    void updateBook_shouldReturnUpdatedBook() {
        BookDtoWithId bookDtoWithId = new BookDtoWithId(1L, "test", "authorTest", "genreTest");

        when(bookService.update(bookDtoWithId)).thenReturn(bookDtoWithId);
        doReturn("test").when(bookController).getCurrentUsername();

        BookDtoWithId result = bookController.updateBook(bookDtoWithId);

        Assertions.assertEquals(bookDtoWithId, result);
        verify(bookService).update(bookDtoWithId);
    }

    @Test
    void deleteBook_shouldDeleteBook() {
        Long bookId = 1L;

        doReturn("test").when(bookController).getCurrentUsername();

        bookController.deleteBook(bookId);

        verify(bookService).delete(bookId);
    }

}
