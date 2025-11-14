package com.meshakin.dto.id;

import jakarta.validation.constraints.NotNull;

public record BookDtoWithId(
        Long id,
        @NotNull
        String name,
        @NotNull
        String authorName,
        @NotNull
        String genreName
){
}
