package com.meshakin.dto.without.id;

import jakarta.validation.constraints.NotNull;

public record GenreDtoWithoutId (
        @NotNull
        String name
){
}
