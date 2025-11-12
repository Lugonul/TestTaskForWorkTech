package com.meshakin.service;

import com.meshakin.dto.BookDto;
import com.meshakin.entity.Author;
import com.meshakin.entity.Book;
import com.meshakin.entity.Genre;
import com.meshakin.mapper.AuthorMapper;
import com.meshakin.mapper.BookMapper;
import com.meshakin.mapper.GenreMapper;
import com.meshakin.repository.AuthorRepository;
import com.meshakin.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {


    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @Transactional
    public BookDto create(BookDto BookDto) {
        Book book = bookMapper.toEntity(BookDto);
        Book saved = bookRepository.save(book);
        return bookMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public Optional<BookDto> read(Long id) {

        Optional<BookDto> maybeBook = bookRepository.findById(id)
                .map(bookMapper::toDto);

        return maybeBook;
    }

    @Transactional(readOnly = true)
    public List<BookDto> readAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDto update(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.id()).orElseThrow(EntityNotFoundException::new);

        book = bookMapper.toEntity(bookDto);
        bookRepository.flush();

        return bookMapper.toDto(book);
    }

    @Transactional
    public void delete(Long id) {
        Book Book = bookRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        bookRepository.delete(Book);
    }

    @Transactional
    public List<BookDto> findByAuthorName(String authorName) {
        return bookRepository.findByAuthorName(authorName).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BookDto> findByGenreName(String genreName) {
        return bookRepository.findByGenreName(genreName).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BookDto> findByName(String name) {
        return bookRepository.findByName(name).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

}