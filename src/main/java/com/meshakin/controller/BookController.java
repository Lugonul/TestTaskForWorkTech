package com.meshakin.controller;

import com.meshakin.dto.id.BookDtoWithId;
import com.meshakin.dto.without.id.BookDtoWithoutId;
import com.meshakin.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("books")
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDtoWithId> getAllBooks() {
        String username = getCurrentUsername();
        log.info("User '{}' is trying to get all books", username);
        List<BookDtoWithId> books = bookService.readAll();
        log.info("User '{}' got all books", username);
        return books;
    }

    @GetMapping("/author")
    public List<BookDtoWithId> getBooksByAuthorName(@RequestParam String authorName) {
        String username = getCurrentUsername();
        log.info("User '{}' is trying to get books with author: {}", username, authorName);
        List<BookDtoWithId> books = bookService.findByAuthorName(authorName);
        log.info("User '{}' got books with author: {}", username, authorName);
        return books;
    }

    @GetMapping("/genre")
    public List<BookDtoWithId> getBooksByGenreName(@RequestParam String genreName) {
        String username = getCurrentUsername();
        log.info("User '{}' is trying to get books with genre: {}", username, genreName);
        List<BookDtoWithId> books = bookService.findByGenreName(genreName);
        log.info("User '{}' got books with genre: {}", username, genreName);
        return books;
    }

    @GetMapping("/name")
    public List<BookDtoWithId> getBooksByName(@RequestParam String name) {
        String username = getCurrentUsername();
        log.info("User '{}' is trying to get books with title: {}", username, name);
        List<BookDtoWithId> books = bookService.findByName(name);
        log.info("User '{}' got books with title: {}", username, name);
        return books;
    }

    @GetMapping("/{id}")
    public BookDtoWithId getBookById(@PathVariable("id") Long id) {
        String username = getCurrentUsername();
        log.info("User '{}' is trying to get book with id: {}", username, id);
        BookDtoWithId bookDtoWithId = bookService.read(id);
        log.info("User '{}' got book with id: {}", username, id);
        return bookDtoWithId;
    }

    @PostMapping
    public ResponseEntity<BookDtoWithId> saveBook(@Valid @RequestBody BookDtoWithoutId bookDtoWithoutId) {
        String username = getCurrentUsername();
        log.info("User '{}' is trying to create book : {}", username, bookDtoWithoutId.name());
        BookDtoWithId book = bookService.create(bookDtoWithoutId);
        URI location = null;

        // Для тестов
        try {
            location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(book.id())
                    .toUri();
        } catch (IllegalStateException e) {}

        log.info("User '{}' created book with id: {}", username, book.id());
        return ResponseEntity.created(location).body(book);
    }

    @PutMapping("/{id}")
    public BookDtoWithId updateBook(@Valid @RequestBody BookDtoWithId bookDtoWithId) {
        String username = getCurrentUsername();
        log.info("User '{}' is trying to update book : {}", username, bookDtoWithId.name());
        BookDtoWithId updatedDto = bookService.update(bookDtoWithId);
        log.info("User '{}' updated book with id: {}", username, bookDtoWithId.id());
        return updatedDto;

    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") Long id){
        String username = getCurrentUsername();
        log.info("User '{}' is trying to delete with id: {}", username, id);
        bookService.delete(id);
        log.info("User '{}' deleted book with id: {}", username, id);
    }

    // public для тестов
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}