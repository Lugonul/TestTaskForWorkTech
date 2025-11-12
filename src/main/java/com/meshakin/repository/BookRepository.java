package com.meshakin.repository;

import com.meshakin.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByName(String name);

    List<Book> findByAuthorName(String authorName);

    List<Book> findByGenreName(String genreName);

}
