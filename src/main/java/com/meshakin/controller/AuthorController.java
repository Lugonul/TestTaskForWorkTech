package com.meshakin.controller;

import com.meshakin.dto.AuthorDto;
import com.meshakin.service.AuthorService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorDto> getAllAuthors() {
        return authorService.readAll();
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorService.read(id).orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> saveAuthor(@RequestBody AuthorDto authorDtoWithoutId) {
        AuthorDto authorDtoWithId = authorService.create(authorDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(authorDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(authorDtoWithId);
    }

    @PutMapping("/{id}")
    public AuthorDto updateAuthor(@RequestBody AuthorDto authorDtoWithId) {
        AuthorDto updatedDto = authorService.update(authorDtoWithId);
        return updatedDto;

    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable("id") Long id){
        authorService.delete(id);
    }
}