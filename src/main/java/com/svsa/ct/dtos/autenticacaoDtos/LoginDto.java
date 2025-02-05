package com.svsa.ct.dtos.autenticacaoDtos;

import jakarta.validation.constraints.NotNull;

public record LoginDto(@NotNull String email, @NotNull String senha) {
}
