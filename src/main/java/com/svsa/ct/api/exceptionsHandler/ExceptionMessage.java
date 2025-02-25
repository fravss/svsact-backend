package com.svsa.ct.api.exceptionsHandler;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionMessage {
    private int httpCode;
    private String errorClass;
    private String message;
}
