package com.meshakin.unit.service;

import com.meshakin.dto.BookDto;
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
import static org.mockito.Mockito.when;

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

        BookDto bookDtoForSave = new BookDto(null, name, authorString, genreString);
        BookDto savedBookDto = new BookDto(id, name, authorString, genreString);

        when(bookRepository.save(bookForSave)).thenReturn(bookForSave);
        when(bookMapper.toEntity(bookDtoForSave)).thenReturn(bookForSave);
        when(bookMapper.toDto(bookForSave)).thenReturn(savedBookDto);

        BookDto result = bookService.create(bookDtoForSave);

        assertEquals(savedBookDto, result);

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


        BookDto bookDto = new BookDto(id, name, authorString, genreString);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.read(id);

        assertEquals(bookDto, result);
    }

    @Test
    void getBookById_whenBookNotExists_thenReturnException() {
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.read(id));

    }

    @Test
    void readAllBooks_whenBookExists_thenReturnBooks() {
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

        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setId(1L);
        book1.setName("test1");


        Book book2 = new Book();
        book2.setId(2L);
        book2.setName("test2");

        books.add(book1);
        books.add(book2);

        BookDto bookDto1 = new BookDto(1L, "test1", authorString, genreString);
        BookDto bookDto2 = new BookDto(2L, "test2", authorString, genreString);

        List<BookDto> bookDtos = new ArrayList<>();
        bookDtos.add(bookDto1);
        bookDtos.add(bookDto2);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.toDto(book1)).thenReturn(bookDto1);
        when(bookMapper.toDto(book2)).thenReturn(bookDto2);

        List<BookDto> result = bookService.readAll();
        assertEquals(bookDtos, result);
    }

    @Test
    void readAllBooks_whenBookNotExists_thenReturnEmptyList() {
        List<Book> books = new ArrayList<>();
        List<BookDto> bookDtos = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDto> result = bookService.readAll();

        assertEquals(bookDtos, result);
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

        BookDto bookDtoForUpdate = new BookDto(id, newName, authorString, genreString);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toEntity(bookDtoForUpdate)).thenReturn(bookForUpdate);
        when(bookMapper.toDto(bookForUpdate)).thenReturn(bookDtoForUpdate);

        BookDto result = bookService.update(bookDtoForUpdate);
        assertEquals(bookDtoForUpdate, result);
    }

    @Test
    void updateBook_whenBookDoesNotExists_shouldReturnException() {
        Long id = 1L;
        String newName = "newName";
        String authorString = "test";
        String genreString = "test";


        BookDto bookDtoForUpdate = new BookDto(id, newName, authorString, genreString);

        when(bookRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> bookService.update(bookDtoForUpdate));
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

        BookDto firstBookDto = new BookDto(id, name, authorString, genreString);
        BookDto secondBookDto = new BookDto(id2, name, authorString, genreString);

        List<Book> books = new ArrayList<>();
        books.add(firstBook);
        books.add(secondBook);

        List<BookDto> booksDto = new ArrayList<>();
        booksDto.add(firstBookDto);
        booksDto.add(secondBookDto);
        ;

        when(bookRepository.findByName(name)).thenReturn(books);
        when(bookMapper.toDto(firstBook)).thenReturn(firstBookDto);
        when(bookMapper.toDto(secondBook)).thenReturn(secondBookDto);

        List<BookDto> result = bookService.findByName(name);
        assertEquals(booksDto, result);
    }

    @Test
    void findBookByBookName_whenBooksDoesNotExists_shouldReturnEmptyList() {
        List<Book> books = new ArrayList<>();
        List<BookDto> booksDto = new ArrayList<>();
        when(bookRepository.findByName("test")).thenReturn(books);

        List<BookDto> result = bookService.findByName("test");

        assertEquals(booksDto, result);
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

        BookDto firstBookDto = new BookDto(id, name, authorString, genreString);
        BookDto secondBookDto = new BookDto(id2, name, authorString, genreString);

        List<Book> books = new ArrayList<>();
        books.add(firstBook);
        books.add(secondBook);

        List<BookDto> booksDto = new ArrayList<>();
        booksDto.add(firstBookDto);
        booksDto.add(secondBookDto);

        when(bookRepository.findByAuthorName(authorString)).thenReturn(books);
        when(bookMapper.toDto(firstBook)).thenReturn(firstBookDto);
        when(bookMapper.toDto(secondBook)).thenReturn(secondBookDto);


        List<BookDto> result = bookService.findByAuthorName(authorString);
        assertEquals(booksDto, result);
    }

    @Test
    void findBookByBookAuthor_whenAuthorDoesNotExists_shouldReturnEmptyList() {
        List<Book> books = new ArrayList<>();
        List<BookDto> booksDto = new ArrayList<>();
        when(bookRepository.findByAuthorName("test")).thenReturn(books);

        List<BookDto> result = bookService.findByAuthorName("test");

        assertEquals(booksDto, result);
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

        BookDto firstBookDto = new BookDto(id, name, authorString, genreString);
        BookDto secondBookDto = new BookDto(id2, name, authorString, genreString);

        List<Book> books = new ArrayList<>();
        books.add(firstBook);
        books.add(secondBook);

        List<BookDto> booksDto = new ArrayList<>();
        booksDto.add(firstBookDto);
        booksDto.add(secondBookDto);

        when(bookRepository.findByGenreName(genreString)).thenReturn(books);
        when(bookMapper.toDto(firstBook)).thenReturn(firstBookDto);
        when(bookMapper.toDto(secondBook)).thenReturn(secondBookDto);


        List<BookDto> result = bookService.findByGenreName(genreString);
        assertEquals(booksDto, result);
    }

    @Test
    void findBookByBookGenre_whenGenreDoesNotExists_shouldReturnEmptyList() {
        List<Book> books = new ArrayList<>();
        List<BookDto> booksDto = new ArrayList<>();
        when(bookRepository.findByGenreName("test")).thenReturn(books);

        List<BookDto> result = bookService.findByGenreName("test");

        assertEquals(booksDto, result);
    }

}