package com.meshakin.dto.without.id;

import jakarta.validation.constraints.NotNull;

public record BookDtoWithoutId (
        @NotNull
        String name,
        @NotNull
        String authorName,
        @NotNull
        String genreName
){
}
