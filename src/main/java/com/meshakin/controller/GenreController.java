package com.meshakin.controller;

import com.meshakin.dto.GenreDto;
import com.meshakin.service.GenreService;
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
    public ResponseEntity<GenreDto> saveGenre(@RequestBody GenreDto genreDtoWithoutId) {
        GenreDto genreDtoWithId = genreService.create(genreDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(genreDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(genreDtoWithId);
    }

    @PutMapping("/{id}")
    public GenreDto updateGenre(@RequestBody GenreDto genreDtoWithId) {
            GenreDto updatedDto = genreService.update(genreDtoWithId);
            return updatedDto;

    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable("id") Long id){
            genreService.delete(id);
    }
}