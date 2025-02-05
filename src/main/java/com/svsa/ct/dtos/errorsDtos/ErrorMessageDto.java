package com.svsa.ct.dtos.errorsDtos;

import java.util.List;

public record ErrorMessageDto(int status, String error, List<String> message) {
}
