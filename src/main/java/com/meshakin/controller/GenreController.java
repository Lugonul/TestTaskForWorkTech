package com.meshakin.controller;

import com.meshakin.dto.id.GenreDtoWithId;
import com.meshakin.dto.without.id.GenreDtoWithoutId;
import com.meshakin.service.GenreService;
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
    public List<GenreDtoWithId> getAllGenres() {
        return genreService.readAll();
    }

    @GetMapping("/{id}")
    public GenreDtoWithId getGenreById(@PathVariable("id") Long id) {
        return genreService.read(id);
    }

    @PostMapping
    public ResponseEntity<GenreDtoWithId> saveGenre(@Valid @RequestBody GenreDtoWithoutId genreDtoWithoutIdId) {
        GenreDtoWithId genre = genreService.create(genreDtoWithoutIdId);
        URI location = null;

        // Для тестов
        try {
            location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(genre.id())
                    .toUri();
        } catch (IllegalStateException e) {}

        return ResponseEntity.created(location).body(genre);
    }

    @PutMapping("/{id}")
    public GenreDtoWithId updateGenre(@Valid @RequestBody GenreDtoWithId genreDtoWithId) {
            GenreDtoWithId updatedDto = genreService.update(genreDtoWithId);
            return updatedDto;

    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable("id") Long id){
            genreService.delete(id);
    }
}