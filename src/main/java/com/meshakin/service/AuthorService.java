package com.meshakin.service;

import com.meshakin.dto.id.AuthorDtoWithId;
import com.meshakin.dto.without.id.AuthorDtoWithoutId;
import com.meshakin.entity.Author;
import com.meshakin.mapper.AuthorMapper;
import com.meshakin.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {


    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    @Transactional
    public AuthorDtoWithId create(AuthorDtoWithoutId authorDtoWithoutId) {
        Author Author = authorMapper.toEntityWithoutId(authorDtoWithoutId);
        Author savedAuthor = authorRepository.save(Author);

        return authorMapper.toDto(savedAuthor);
    }

    @Transactional(readOnly = true)
    public AuthorDtoWithId read(Long id) {

        AuthorDtoWithId author = authorRepository.findById(id)
                .map(authorMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);

        return author;
    }

    @Transactional(readOnly = true)
    public List<AuthorDtoWithId> readAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AuthorDtoWithId update(AuthorDtoWithId authorDtoWithId) {
        Author author = authorRepository.findById(authorDtoWithId.id()).orElseThrow(EntityNotFoundException::new);

        author = authorMapper.toEntity(authorDtoWithId);
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
    public AuthorDtoWithId findByName(String name) {
       Author author = authorRepository.findByName(name);
       if (author == null) {
           throw new EntityNotFoundException();
       }
       return authorMapper.toDto(author);
    }

}