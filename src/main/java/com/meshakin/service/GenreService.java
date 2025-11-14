package com.meshakin.service;

import com.meshakin.dto.id.GenreDtoWithId;
import com.meshakin.dto.without.id.GenreDtoWithoutId;
import com.meshakin.entity.Genre;
import com.meshakin.mapper.GenreMapper;
import com.meshakin.repository.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {


    private final GenreMapper genreMapper;
    private final GenreRepository genreRepository;

    @Transactional
    public GenreDtoWithId create(GenreDtoWithoutId genreDtoWithoutId) {
        Genre Genre = genreMapper.toEntityWithoutId(genreDtoWithoutId);
        Genre savedGenre = genreRepository.save(Genre);

        return genreMapper.toDto(savedGenre);
    }

    @Transactional(readOnly = true)
    public GenreDtoWithId read(Long id) {

        GenreDtoWithId genre = genreRepository.findById(id)
                .map(genreMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);

        return genre;
    }

    @Transactional(readOnly = true)
    public List<GenreDtoWithId> readAll() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public GenreDtoWithId update(GenreDtoWithId genreDtoWithId) {
        Genre genre = genreRepository.findById(genreDtoWithId.id()).orElseThrow(EntityNotFoundException::new);

        genre = genreMapper.toEntity(genreDtoWithId);
        genreRepository.flush();

        return genreMapper.toDto(genre);
    }

    @Transactional
    public void delete(Long id) {
        Genre Genre = genreRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        genreRepository.delete(Genre);
    }

    @Transactional
    public GenreDtoWithId findByName(String name) {
        Genre genre = genreRepository.findByName(name);
        return genreMapper.toDto(genre);
    }

}