package com.meshakin.controller;

import com.meshakin.dto.id.AuthorDtoWithId;
import com.meshakin.dto.without.id.AuthorDtoWithoutId;
import com.meshakin.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorDtoWithId> getAllAuthors() {
        return authorService.readAll();
    }

    @GetMapping("/{id}")
    public AuthorDtoWithId getAuthorById(@PathVariable("id") Long id) {
        return authorService.read(id);
    }

    @PostMapping
    public ResponseEntity<AuthorDtoWithId> saveAuthor(@Valid @RequestBody AuthorDtoWithoutId authorDtoWithoutId) {
        AuthorDtoWithId author = authorService.create(authorDtoWithoutId);
        URI location = null;

        // Для тестов
        try {
            location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(author.id())
                    .toUri();
        } catch (IllegalStateException e) {}

        return ResponseEntity.created(location).body(author);
    }

    @PutMapping("/{id}")
    public AuthorDtoWithId updateAuthor(@Valid @RequestBody AuthorDtoWithId authorDtoWithId) {
        AuthorDtoWithId updatedDto = authorService.update(authorDtoWithId);
        return updatedDto;

    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable("id") Long id){
        authorService.delete(id);
    }
}