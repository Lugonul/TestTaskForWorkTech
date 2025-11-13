package com.meshakin.dto;

import jakarta.validation.constraints.NotNull;

public record BookDto (
        Long id,
        @NotNull
        String name,
        @NotNull
        String authorName,
        @NotNull
        String genreName
){
}
