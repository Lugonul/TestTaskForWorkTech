package com.meshakin.dto.id;

import jakarta.validation.constraints.NotNull;

public record AuthorDtoWithId(
        Long id,
        @NotNull
        String name
){
}
