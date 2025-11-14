package com.meshakin.unit.service;

import com.meshakin.dto.id.BookDtoWithId;
import com.meshakin.dto.without.id.BookDtoWithoutId;
import com.meshakin.entity.Author;
import com.meshakin.entity.Book;
import com.meshakin.entity.Genre;
import com.meshakin.mapper.BookMapper;
import com.meshakin.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.meshakin.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    BookRepository bookRepository;

    @Mock
    BookMapper bookMapper;

    @InjectMocks
    BookService bookService;

    @Test
    void createBook_shouldReturnBook() {
        Long id = 1L;
        String name = "test";

        String authorString = "test";
        Author author = new Author();
        author.setId(id);
        author.setName(authorString);

        String genreString = "test";
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(genreString);


        Book bookForSave = new Book();
        bookForSave.setName(name);
        bookForSave.setAuthor(author);
        bookForSave.setGenre(genre);

        Book savedBook = new Book();
        savedBook.setId(id);
        savedBook.setName(name);
        savedBook.setAuthor(author);
        savedBook.setGenre(genre);

        BookDtoWithoutId bookDtoWithoutId = new BookDtoWithoutId(name, authorString, genreString);
        BookDtoWithId savedBookDtoWithId = new BookDtoWithId(id, name, authorString, genreString);

        when(bookRepository.save(bookForSave)).thenReturn(bookForSave);
        when(bookMapper.toEntityWithoutId(bookDtoWithoutId)).thenReturn(bookForSave);
        when(bookMapper.toDto(bookForSave)).thenReturn(savedBookDtoWithId);

        BookDtoWithId result = bookService.create(bookDtoWithoutId);

        assertEquals(savedBookDtoWithId, result);
        verify(bookRepository).save(bookForSave);

    }

    @Test
    void getBookById_whenBookExists_thenReturnBook() {
        Long id = 1L;
        String name = "test";
        String authorString = "test";
        Author author = new Author();
        author.setId(id);
        author.setName(authorString);
        String genreString = "test";
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(genreString);

        Book book = new Book();
        book.setId(id);
        book.setName(name);


        BookDtoWithId bookDtoWithId = new BookDtoWithId(id, name, authorString, genreString);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDtoWithId);

        BookDtoWithId result = bookService.read(id);

        assertEquals(bookDtoWithId, result);
        verify(bookRepository).findById(id);
    }

    @Test
    void getBookById_whenBookNotExists_thenReturnException() {
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.read(id));
        verify(bookRepository).findById(id);

    }

    @Test
    void readAllBooks_whenBookExists_thenReturnBooks() {
        Long id = 1L;

        String authorString = "test";
        Author author = new Author();
        author.setId(id);
        author.setName(authorString);

        String genreString = "test";
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(genreString);

        List<Book> books = new ArrayList<>();

        Book book1 = new Book();
        book1.setId(1L);
        book1.setName("test1");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setName("test2");

        books.add(book1);
        books.add(book2);

        BookDtoWithId bookDtoWithId1 = new BookDtoWithId(1L, "test1", authorString, genreString);
        BookDtoWithId bookDtoWithId2 = new BookDtoWithId(2L, "test2", authorString, genreString);

        List<BookDtoWithId> bookDtoWithIds = new ArrayList<>();
        bookDtoWithIds.add(bookDtoWithId1);
        bookDtoWithIds.add(bookDtoWithId2);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.toDto(book1)).thenReturn(bookDtoWithId1);
        when(bookMapper.toDto(book2)).thenReturn(bookDtoWithId2);

        List<BookDtoWithId> result = bookService.readAll();

        assertEquals(bookDtoWithIds, result);
        verify(bookRepository).findAll();
    }

    @Test
    void readAllBooks_whenBookNotExists_thenReturnEmptyList() {
        List<Book> books = new ArrayList<>();
        List<BookDtoWithId> bookDtoWithIds = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDtoWithId> result = bookService.readAll();

        assertEquals(bookDtoWithIds, result);
        verify(bookRepository).findAll();
    }

    @Test
    void updateBook_whenBookExists_shouldReturnUpdatedBook() {
        Long id = 1L;
        String name = "test";
        String newName = "newName";

        String authorString = "test";
        Author author = new Author();
        author.setId(id);
        author.setName(authorString);

        String genreString = "test";
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(genreString);

        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);

        Book bookForUpdate = new Book();
        bookForUpdate.setId(id);
        bookForUpdate.setName(newName);
        bookForUpdate.setAuthor(author);
        bookForUpdate.setGenre(genre);

        BookDtoWithId bookDtoWithIdForUpdate = new BookDtoWithId(id, newName, authorString, genreString);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toEntity(bookDtoWithIdForUpdate)).thenReturn(bookForUpdate);
        when(bookMapper.toDto(bookForUpdate)).thenReturn(bookDtoWithIdForUpdate);

        BookDtoWithId result = bookService.update(bookDtoWithIdForUpdate);

        assertEquals(bookDtoWithIdForUpdate, result);
        verify(bookRepository).findById(id);
    }

    @Test
    void updateBook_whenBookDoesNotExists_shouldReturnException() {
        Long id = 1L;
        String newName = "newName";
        String authorString = "test";
        String genreString = "test";


        BookDtoWithId bookDtoWithIdForUpdate = new BookDtoWithId(id, newName, authorString, genreString);

        when(bookRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> bookService.update(bookDtoWithIdForUpdate));
        verify(bookRepository).findById(id);
        verify(bookRepository, never()).flush();
    }

    @Test
    void deleteBook_whenBookExists_shouldDeleteBook() {
        Long id = 1L;
        String name = "test";

        String authorString = "test";
        Author author = new Author();
        author.setId(id);
        author.setName(authorString);

        String genreString = "test";
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(genreString);

        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));


        bookService.delete(book.getId());

        verify(bookRepository).findById(id);
        verify(bookRepository).delete(any());
    }

    @Test
    void deleteBook_whenBookDoesNotExists_shouldThrowException() {
        Long id = 1L;
        String name = "test";

        String authorString = "test";
        Author author = new Author();
        author.setId(id);
        author.setName(authorString);

        String genreString = "test";
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(genreString);

        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.delete(book.getId()));
        verify(bookRepository).findById(id);
        verify(bookRepository, never()).deleteById(id);
    }

    @Test
    void findBookByBookName_whenBooksExists_shouldReturnBooks() {
        Long id = 1L;
        Long id2 = 2L;
        String name = "test";

        String authorString = "test";
        Author author = new Author();
        author.setId(id);
        author.setName(authorString);

        String genreString = "test";
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(genreString);

        Book firstBook = new Book();
        firstBook.setId(id);
        firstBook.setName(name);
        firstBook.setAuthor(author);
        firstBook.setGenre(genre);

        Book secondBook = new Book();
        secondBook.setId(id2);
        secondBook.setName(name);
        secondBook.setAuthor(author);
        secondBook.setGenre(genre);

        BookDtoWithId firstBookDtoWithId = new BookDtoWithId(id, name, authorString, genreString);
        BookDtoWithId secondBookDtoWithId = new BookDtoWithId(id2, name, authorString, genreString);

        List<Book> books = new ArrayList<>();
        books.add(firstBook);
        books.add(secondBook);

        List<BookDtoWithId> booksDto = new ArrayList<>();
        booksDto.add(firstBookDtoWithId);
        booksDto.add(secondBookDtoWithId);
        ;

        when(bookRepository.findByName(name)).thenReturn(books);
        when(bookMapper.toDto(firstBook)).thenReturn(firstBookDtoWithId);
        when(bookMapper.toDto(secondBook)).thenReturn(secondBookDtoWithId);

        List<BookDtoWithId> result = bookService.findByName(name);

        assertEquals(booksDto, result);
        verify(bookRepository).findByName(name);
    }

    @Test
    void findBookByBookName_whenBooksDoesNotExists_shouldReturnEmptyList() {
        List<Book> books = new ArrayList<>();
        List<BookDtoWithId> booksDto = new ArrayList<>();

        when(bookRepository.findByName("test")).thenReturn(books);

        List<BookDtoWithId> result = bookService.findByName("test");

        assertEquals(booksDto, result);
        verify(bookRepository).findByName("test");
    }

    @Test
    void findBookByAuthor_whenAuthorExists_shouldReturnBooks() {
        Long id = 1L;
        Long id2 = 2L;
        String name = "test";

        String authorString = "test";
        Author author = new Author();
        author.setId(id);
        author.setName(authorString);

        String genreString = "test";
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(genreString);

        Book firstBook = new Book();
        firstBook.setId(id);
        firstBook.setName(name);
        firstBook.setAuthor(author);
        firstBook.setGenre(genre);

        Book secondBook = new Book();
        secondBook.setId(id2);
        secondBook.setName(name);
        secondBook.setAuthor(author);
        secondBook.setGenre(genre);

        BookDtoWithId firstBookDtoWithId = new BookDtoWithId(id, name, authorString, genreString);
        BookDtoWithId secondBookDtoWithId = new BookDtoWithId(id2, name, authorString, genreString);

        List<Book> books = new ArrayList<>();
        books.add(firstBook);
        books.add(secondBook);

        List<BookDtoWithId> booksDto = new ArrayList<>();
        booksDto.add(firstBookDtoWithId);
        booksDto.add(secondBookDtoWithId);

        when(bookRepository.findByAuthorName(authorString)).thenReturn(books);
        when(bookMapper.toDto(firstBook)).thenReturn(firstBookDtoWithId);
        when(bookMapper.toDto(secondBook)).thenReturn(secondBookDtoWithId);

        List<BookDtoWithId> result = bookService.findByAuthorName(authorString);

        assertEquals(booksDto, result);
        verify(bookRepository).findByAuthorName(authorString);
    }

    @Test
    void findBookByBookAuthor_whenAuthorDoesNotExists_shouldReturnEmptyList() {
        List<Book> books = new ArrayList<>();
        List<BookDtoWithId> booksDto = new ArrayList<>();
        when(bookRepository.findByAuthorName("test")).thenReturn(books);

        List<BookDtoWithId> result = bookService.findByAuthorName("test");

        assertEquals(booksDto, result);
        verify(bookRepository).findByAuthorName("test");
    }

    @Test
    void findBookByGenre_whenGenreExists_shouldReturnBooks() {
        Long id = 1L;
        Long id2 = 2L;
        String name = "test";

        String authorString = "test";
        Author author = new Author();
        author.setId(id);
        author.setName(authorString);

        String genreString = "test";
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(genreString);

        Book firstBook = new Book();
        firstBook.setId(id);
        firstBook.setName(name);
        firstBook.setAuthor(author);
        firstBook.setGenre(genre);

        Book secondBook = new Book();
        secondBook.setId(id2);
        secondBook.setName(name);
        secondBook.setAuthor(author);
        secondBook.setGenre(genre);

        BookDtoWithId firstBookDtoWithId = new BookDtoWithId(id, name, authorString, genreString);
        BookDtoWithId secondBookDtoWithId = new BookDtoWithId(id2, name, authorString, genreString);

        List<Book> books = new ArrayList<>();
        books.add(firstBook);
        books.add(secondBook);

        List<BookDtoWithId> booksDto = new ArrayList<>();
        booksDto.add(firstBookDtoWithId);
        booksDto.add(secondBookDtoWithId);

        when(bookRepository.findByGenreName(genreString)).thenReturn(books);
        when(bookMapper.toDto(firstBook)).thenReturn(firstBookDtoWithId);
        when(bookMapper.toDto(secondBook)).thenReturn(secondBookDtoWithId);


        List<BookDtoWithId> result = bookService.findByGenreName(genreString);

        assertEquals(booksDto, result);
        verify(bookRepository).findByGenreName(genreString);
    }

    @Test
    void findBookByBookGenre_whenGenreDoesNotExists_shouldReturnEmptyList() {
        List<Book> books = new ArrayList<>();
        List<BookDtoWithId> booksDto = new ArrayList<>();
        when(bookRepository.findByGenreName("test")).thenReturn(books);

        List<BookDtoWithId> result = bookService.findByGenreName("test");

        assertEquals(booksDto, result);
        verify(bookRepository).findByGenreName("test");
    }

}