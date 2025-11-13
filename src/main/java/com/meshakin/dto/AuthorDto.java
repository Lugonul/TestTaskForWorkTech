package com.meshakin.dto;

import jakarta.validation.constraints.NotNull;

public record AuthorDto (
        Long id,
        @NotNull
        String name
){
}
