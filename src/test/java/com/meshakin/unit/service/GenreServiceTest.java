package com.meshakin.unit.service;

import com.meshakin.dto.GenreDto;
import com.meshakin.entity.Genre;
import com.meshakin.mapper.GenreMapper;
import com.meshakin.repository.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.meshakin.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {
    @Mock
    GenreRepository genreRepository;

    @Mock
    GenreMapper genreMapper;

    @InjectMocks
    GenreService genreService;

    @Test
    void createGenre_shouldReturnGenre() {
        Long id = 1L;
        String name = "test";

        Genre genreForSave = new Genre();
        genreForSave.setName(name);

        Genre savedGenre = new Genre();
        savedGenre.setId(id);
        savedGenre.setName(name);

        GenreDto genreDtoForSave = new GenreDto(null, name);
        GenreDto savedGenreDto = new GenreDto(id, name);

        when(genreRepository.save(genreForSave)).thenReturn(genreForSave);
        when(genreMapper.toEntity(genreDtoForSave)).thenReturn(genreForSave);
        when(genreMapper.toDto(genreForSave)).thenReturn(savedGenreDto);

        GenreDto result = genreService.create(genreDtoForSave);

        assertEquals(savedGenreDto, result);

    }

    @Test
    void getGenreById_whenGenreExists_thenReturnGenre() {
        Long id = 1L;
        String name = "test";

        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(name);

        GenreDto genreDto = new GenreDto(id, name);

        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        when(genreMapper.toDto(genre)).thenReturn(genreDto);

        GenreDto result = genreService.read(id);

        assertEquals(genreDto, result);
    }

    @Test
    void getGenreById_whenGenreNotExists_thenReturnException() {
        Long id = 1L;
        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> genreService.read(id));

    }

    @Test
    void readAllGenres_whenGenreExists_thenReturnGenres() {
        List<Genre> genres = new ArrayList<>();
        Genre genre1 = new Genre();
        genre1.setId(1L);
        genre1.setName("test1");

        Genre genre2 = new Genre();
        genre2.setId(2L);
        genre2.setName("test2");

        genres.add(genre1);
        genres.add(genre2);

        GenreDto genreDto1 = new GenreDto(1L, "test1");
        GenreDto genreDto2 = new GenreDto(2L, "test2");

        List<GenreDto> genreDtos = new ArrayList<>();
        genreDtos.add(genreDto1);
        genreDtos.add(genreDto2);

        when(genreRepository.findAll()).thenReturn(genres);
        when(genreMapper.toDto(genre1)).thenReturn(genreDto1);
        when(genreMapper.toDto(genre2)).thenReturn(genreDto2);

        List <GenreDto> result = genreService.readAll();
        assertEquals(genreDtos, result);
    }

    @Test
    void readAllGenres_whenGenreNotExists_thenReturnEmptyList() {
        List<Genre> genres = new ArrayList<>();
        List<GenreDto> genreDtos = new ArrayList<>();
        when(genreRepository.findAll()).thenReturn(genres);

        List <GenreDto> result = genreService.readAll();

        assertEquals(genreDtos, result);
    }

    @Test
    void updateGenre_whenGenreExists_shouldReturnUpdatedGenre() {
        Long id = 1L;
        String name = "test";
        String newName = "newName";

        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(name);

        Genre genreForUpdate = new Genre();
        genreForUpdate.setId(id);
        genreForUpdate.setName(newName);

        GenreDto genreDtoForUpdate = new GenreDto(id, newName);

        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        when(genreMapper.toEntity(genreDtoForUpdate)).thenReturn(genreForUpdate);
        when(genreMapper.toDto(genreForUpdate)).thenReturn(genreDtoForUpdate);

        GenreDto result = genreService.update(genreDtoForUpdate);
        assertEquals(genreDtoForUpdate, result);
    }

    @Test
    void updateGenre_whenGenreDoesNotExists_shouldReturnException () {
        Long id = 1L;
        String newName = "newName";

        GenreDto genreDtoForUpdate = new GenreDto(id, newName);

        when(genreRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> genreService.update(genreDtoForUpdate));
    }

    @Test
    void deleteGenre_whenGenreExists_shouldDeleteGenre() {
        Long id = 1L;
        String name = "test";
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(name);

        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        genreService.delete(genre.getId());
    }

    @Test
    void deleteGenre_whenGenreDoesNotExists_shouldThrowException() {
        Long id = 1L;
        String name = "test";
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(name);

        when(genreRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> genreService.delete(genre.getId()));
    }

    @Test
    void findGenreByName_whenGenreExists_shouldReturnGenre() {
        Long id = 1L;
        String name = "test";

        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(name);

        GenreDto foundGenreDto = new GenreDto(id, name);

        when(genreRepository.findByName(name)).thenReturn(genre);
        when(genreMapper.toDto(genre)).thenReturn(foundGenreDto);

        GenreDto result = genreService.findByName(name);
        assertEquals(foundGenreDto, result);
    }

}