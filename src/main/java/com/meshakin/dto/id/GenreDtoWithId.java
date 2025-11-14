package com.meshakin.dto.id;

import jakarta.validation.constraints.NotNull;

public record GenreDtoWithId(
        Long id,
        @NotNull
        String name
){
}
