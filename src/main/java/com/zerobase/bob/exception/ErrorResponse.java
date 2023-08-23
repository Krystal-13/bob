package com.zerobase.bob.exception;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ErrorResponse {
    private ErrorCode errorCode;
    private String errorMessage;
}
