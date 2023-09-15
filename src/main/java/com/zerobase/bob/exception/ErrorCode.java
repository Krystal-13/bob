package com.zerobase.bob.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* user */
    USER_NOT_FOUND("사용자가 없습니다.", NOT_FOUND),
    ALREADY_REGISTERED_USER("이미 존재하는 사용자입니다.", BAD_REQUEST),
    UNMATCHED_INFORMATION("아이디 또는 비밀번호가 일치하지 않습니다.", BAD_REQUEST),

    /* recipe */
    RECIPE_NOT_FOUNT("일치하는 레시피가 없습니다.", NOT_FOUND),
    ALREADY_REGISTERED_RECIPE("이미 북마크되어 있는 레시피입니다.", BAD_REQUEST),
    UNMATCHED_USER_RECIPE("본인의 레시피만 수정/삭제할 수 있습니다.", BAD_REQUEST),

    /* else */
    INVALID_REQUEST("잘못된 요청입니다.", BAD_REQUEST),
    EXPIRED_TOKEN("인증 토큰이 만료되었습니다.", UNAUTHORIZED);

    private final String description;
    private final HttpStatus status;
}
