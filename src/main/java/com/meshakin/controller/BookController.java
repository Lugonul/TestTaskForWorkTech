package com.meshakin.controller;

import com.meshakin.dto.BookDto;
import com.meshakin.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.readAll();
    }

    @GetMapping("/author")
    public List<BookDto> getBooksByAuthorName(@RequestParam String authorName) {
        return bookService.findByAuthorName(authorName);
    }

    @GetMapping("/genre")
    public List<BookDto> getBooksByGenreName(@RequestParam String genreName) {
        return bookService.findByGenreName(genreName);
    }

    @GetMapping("/name")
    public List<BookDto> getBooksByName(@RequestParam String name) {
        return bookService.findByName(name);
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable("id") Long id) {
        return bookService.read(id).orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto bookDtoWithoutId) {
        BookDto bookDtoWithId = bookService.create(bookDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(bookDtoWithId);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@RequestBody BookDto bookDtoWithId) {
        BookDto updatedDto = bookService.update(bookDtoWithId);
        return updatedDto;

    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") Long id){
        bookService.delete(id);
    }
}