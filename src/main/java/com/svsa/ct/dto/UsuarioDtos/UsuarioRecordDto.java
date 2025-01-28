package com.svsa.ct.dto.UsuarioDtos;

import com.svsa.ct.model.enums.Role;

public record UsuarioRecordDto(Long id,
                               String name,
                               String email
                               ) {
}
