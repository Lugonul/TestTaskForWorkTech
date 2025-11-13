package com.meshakin.controller;

import com.meshakin.dto.GenreDto;
import com.meshakin.service.GenreService;
import jakarta.persistence.EntityNotFoundException;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("genres")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<GenreDto> getAllGenres() {
        return genreService.readAll();
    }

    @GetMapping("/{id}")
    public GenreDto getGenreById(@PathVariable("id") Long id) {
        return genreService.read(id).orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    public ResponseEntity<GenreDto> saveGenre(@Valid @RequestBody GenreDto genreDto) {
        GenreDto genre = genreService.create(genreDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(genre.id())
                .toUri();
        return ResponseEntity.created(location).body(genre);
    }

    @PutMapping("/{id}")
    public GenreDto updateGenre(@Valid @RequestBody GenreDto genreDto) {
            GenreDto updatedDto = genreService.update(genreDto);
            return updatedDto;

    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable("id") Long id){
            genreService.delete(id);
    }
}