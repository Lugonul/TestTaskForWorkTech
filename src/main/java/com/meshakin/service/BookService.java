package com.meshakin.service;

import com.meshakin.dto.id.BookDtoWithId;
import com.meshakin.dto.without.id.BookDtoWithoutId;
import com.meshakin.entity.Book;
import com.meshakin.mapper.BookMapper;
import com.meshakin.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {


    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Transactional
    public BookDtoWithId create(BookDtoWithoutId bookDtoWithoutId) {
        Book book = bookMapper.toEntityWithoutId(bookDtoWithoutId);
        Book saved = bookRepository.save(book);
        return bookMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public BookDtoWithId read(Long id) {

        BookDtoWithId book = bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);

        return book;
    }

    @Transactional(readOnly = true)
    public List<BookDtoWithId> readAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDtoWithId update(BookDtoWithId bookDtoWithId) {
        Book book = bookRepository.findById(bookDtoWithId.id()).orElseThrow(EntityNotFoundException::new);

        book = bookMapper.toEntity(bookDtoWithId);
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
    public List<BookDtoWithId> findByAuthorName(String authorName) {
        return bookRepository.findByAuthorName(authorName).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BookDtoWithId> findByGenreName(String genreName) {
        return bookRepository.findByGenreName(genreName).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BookDtoWithId> findByName(String name) {
        return bookRepository.findByName(name).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

}