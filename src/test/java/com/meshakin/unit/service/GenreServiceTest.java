package com.meshakin.unit.service;

import com.meshakin.dto.id.GenreDtoWithId;
import com.meshakin.dto.without.id.GenreDtoWithoutId;
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
import static org.mockito.Mockito.*;

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

        GenreDtoWithoutId genreDtoWithoutId = new GenreDtoWithoutId(name);
        GenreDtoWithId savedGenreDtoWithId = new GenreDtoWithId(id, name);

        when(genreRepository.save(genreForSave)).thenReturn(genreForSave);
        when(genreMapper.toEntityWithoutId(genreDtoWithoutId)).thenReturn(genreForSave);
        when(genreMapper.toDto(genreForSave)).thenReturn(savedGenreDtoWithId);

        GenreDtoWithId result = genreService.create(genreDtoWithoutId);

        assertEquals(savedGenreDtoWithId, result);
        verify(genreRepository).save(genreForSave);

    }

    @Test
    void getGenreById_whenGenreExists_thenReturnGenre() {
        Long id = 1L;
        String name = "test";

        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(name);

        GenreDtoWithId genreDtoWithId = new GenreDtoWithId(id, name);

        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        when(genreMapper.toDto(genre)).thenReturn(genreDtoWithId);

        GenreDtoWithId result = genreService.read(id);

        assertEquals(genreDtoWithId, result);
        verify(genreRepository).findById(id);
    }

    @Test
    void getGenreById_whenGenreNotExists_thenReturnException() {
        Long id = 1L;
        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> genreService.read(id));
        verify(genreRepository).findById(id);
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

        GenreDtoWithId genreDtoWithId1 = new GenreDtoWithId(1L, "test1");
        GenreDtoWithId genreDtoWithId2 = new GenreDtoWithId(2L, "test2");

        List<GenreDtoWithId> genreDtoWithIds = new ArrayList<>();
        genreDtoWithIds.add(genreDtoWithId1);
        genreDtoWithIds.add(genreDtoWithId2);

        when(genreRepository.findAll()).thenReturn(genres);
        when(genreMapper.toDto(genre1)).thenReturn(genreDtoWithId1);
        when(genreMapper.toDto(genre2)).thenReturn(genreDtoWithId2);

        List <GenreDtoWithId> result = genreService.readAll();

        assertEquals(genreDtoWithIds, result);
        verify(genreRepository).findAll();
    }

    @Test
    void readAllGenres_whenGenreNotExists_thenReturnEmptyList() {
        List<Genre> genres = new ArrayList<>();
        List<GenreDtoWithId> genreDtoWithIds = new ArrayList<>();
        when(genreRepository.findAll()).thenReturn(genres);

        List <GenreDtoWithId> result = genreService.readAll();

        assertEquals(genreDtoWithIds, result);
        verify(genreRepository).findAll();
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

        GenreDtoWithId genreDtoWithIdForUpdate = new GenreDtoWithId(id, newName);

        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        when(genreMapper.toEntity(genreDtoWithIdForUpdate)).thenReturn(genreForUpdate);
        when(genreMapper.toDto(genreForUpdate)).thenReturn(genreDtoWithIdForUpdate);

        GenreDtoWithId result = genreService.update(genreDtoWithIdForUpdate);

        assertEquals(genreDtoWithIdForUpdate, result);
        verify(genreRepository).findById(id);
        verify(genreRepository).flush();
    }

    @Test
    void updateGenre_whenGenreDoesNotExists_shouldReturnException () {
        Long id = 1L;
        String newName = "newName";

        GenreDtoWithId genreDtoWithIdForUpdate = new GenreDtoWithId(id, newName);

        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> genreService.update(genreDtoWithIdForUpdate));
        verify(genreRepository).findById(id);
        verify(genreRepository, never()).flush();
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
        verify(genreRepository).findById(id);
        verify(genreRepository).delete(genre);
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
        verify(genreRepository).findById(id);
        verify(genreRepository, never()).delete(genre);
    }

    @Test
    void findGenreByName_whenGenreExists_shouldReturnGenre() {
        Long id = 1L;
        String name = "test";

        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(name);

        GenreDtoWithId foundGenreDtoWithId = new GenreDtoWithId(id, name);

        when(genreRepository.findByName(name)).thenReturn(genre);
        when(genreMapper.toDto(genre)).thenReturn(foundGenreDtoWithId);

        GenreDtoWithId result = genreService.findByName(name);

        assertEquals(foundGenreDtoWithId, result);
        verify(genreRepository).findByName(name);
    }

}