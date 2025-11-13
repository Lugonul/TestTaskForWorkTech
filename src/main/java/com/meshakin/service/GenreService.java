package com.meshakin.service;

import com.meshakin.dto.GenreDto;
import com.meshakin.entity.Genre;
import com.meshakin.mapper.GenreMapper;
import com.meshakin.repository.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {


    private final GenreMapper genreMapper;
    private final GenreRepository genreRepository;

    @Transactional
    public GenreDto create(GenreDto GenreDto) {
        Genre Genre = genreMapper.toEntity(GenreDto);
        Genre savedGenre = genreRepository.save(Genre);

        return genreMapper.toDto(savedGenre);
    }

    @Transactional(readOnly = true)
    public GenreDto read(Long id) {

        GenreDto genre = genreRepository.findById(id)
                .map(genreMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);

        return genre;
    }

    @Transactional(readOnly = true)
    public List<GenreDto> readAll() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public GenreDto update(GenreDto genreDto) {
        Genre genre = genreRepository.findById(genreDto.id()).orElseThrow(EntityNotFoundException::new);

        genre = genreMapper.toEntity(genreDto);
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
    public GenreDto findByName(String name) {
        Genre genre = genreRepository.findByName(name);
        return genreMapper.toDto(genre);
    }

}