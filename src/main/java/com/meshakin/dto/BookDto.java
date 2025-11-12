package com.meshakin.dto;

public record BookDto (
        Long id,
        String name,
        String authorName,
        String genreName
){
}
