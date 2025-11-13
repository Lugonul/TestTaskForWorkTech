package com.meshakin.dto;

import jakarta.validation.constraints.NotNull;

public record GenreDto (
        Long id,
        @NotNull
        String name
){
}
