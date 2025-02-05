package com.svsa.ct.dtos.autenticacaoDtos;

import jakarta.validation.constraints.NotNull;

public record RequestLoginDto(@NotNull String email, @NotNull String senha) {
}
