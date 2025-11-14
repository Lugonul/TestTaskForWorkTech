package com.meshakin.unit.controller;

import com.meshakin.controller.GenreController;
import com.meshakin.dto.id.GenreDtoWithId;
import com.meshakin.dto.without.id.GenreDtoWithoutId;
import com.meshakin.service.GenreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenreControllerTest {
    @Mock
    private GenreService genreService;
    @InjectMocks
    private GenreController genreController;

    @Test
    void createGenre_shouldReturnCreatedGenre() {
        GenreDtoWithoutId genreDtoWithoutId = new GenreDtoWithoutId("test");
        GenreDtoWithId genreDtoWithId = new GenreDtoWithId(1L, "test");

        when(genreService.create(genreDtoWithoutId)).thenReturn(genreDtoWithId);

        GenreDtoWithId result = genreController.saveGenre(genreDtoWithoutId).getBody();

        Assertions.assertEquals(genreDtoWithId, result);
        verify(genreService).create(genreDtoWithoutId);
    }

    @Test
    void getGenre_shouldReturnGenre() {
        Long genreId = 1L;
        GenreDtoWithId genreDtoWithId = new GenreDtoWithId(genreId, "test");

        when(genreService.read(genreId)).thenReturn(genreDtoWithId);

        GenreDtoWithId result = genreController.getGenreById(genreId);

        Assertions.assertEquals(genreDtoWithId, result);
        verify(genreService).read(genreId);
    }

    @Test
    void getAllGenres_shouldReturnGenres() {
        GenreDtoWithId genreDtoWithId = new GenreDtoWithId(1L, "test");
        GenreDtoWithId genreDtoWithId2 = new GenreDtoWithId(2L, "test2");
        List<GenreDtoWithId> genres = new ArrayList<>();
        genres.add(genreDtoWithId);
        genres.add(genreDtoWithId2);

        when(genreService.readAll()).thenReturn(genres);

        List<GenreDtoWithId> result = genreController.getAllGenres();


        Assertions.assertEquals(genres, result);
        verify(genreService).readAll();
    }

    @Test
    void updateGenre_shouldReturnUpdatedGenre() {
        GenreDtoWithId genreDtoWithId = new GenreDtoWithId(1L, "test");

        when(genreService.update(genreDtoWithId)).thenReturn(genreDtoWithId);

        GenreDtoWithId result = genreController.updateGenre(genreDtoWithId);

        Assertions.assertEquals(genreDtoWithId, result);
        verify(genreService).update(genreDtoWithId);
    }

    @Test
    void deleteGenre_shouldDeleteGenre() {
        Long genreId = 1L;

        genreController.deleteGenre(genreId);

        verify(genreService).delete(genreId);
    }

}

