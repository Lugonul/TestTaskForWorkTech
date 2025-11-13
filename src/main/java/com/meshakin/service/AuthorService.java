package com.meshakin.service;

import com.meshakin.dto.AuthorDto;
import com.meshakin.entity.Author;
import com.meshakin.mapper.AuthorMapper;
import com.meshakin.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {


    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    @Transactional
    public AuthorDto create(AuthorDto AuthorDto) {
        Author Author = authorMapper.toEntity(AuthorDto);
        Author savedAuthor = authorRepository.save(Author);

        return authorMapper.toDto(savedAuthor);
    }

    @Transactional(readOnly = true)
    public Optional<AuthorDto> read(Long id) {

        Optional<AuthorDto> maybeAuthor = authorRepository.findById(id)
                .map(authorMapper::toDto);

        return maybeAuthor;
    }

    @Transactional(readOnly = true)
    public List<AuthorDto> readAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AuthorDto update(AuthorDto authorDto) {
        Author author = authorRepository.findById(authorDto.id()).orElseThrow(EntityNotFoundException::new);

        author = authorMapper.toEntity(authorDto);
        authorRepository.flush();

        return authorMapper.toDto(author);
    }

    @Transactional
    public void delete(Long id) {
        Author Author = authorRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        authorRepository.delete(Author);
    }

    @Transactional
    public AuthorDto findByName(String name) {
       Author author = authorRepository.findByName(name);
       if (author == null) {
           throw new EntityNotFoundException();
       }
       return authorMapper.toDto(author);
    }

}