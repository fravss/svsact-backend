package com.svsa.ct.dto.AutenticacaoDtos;

import jakarta.validation.constraints.NotNull;

public record LoginDto(@NotNull String email, @NotNull String senha) {
}
