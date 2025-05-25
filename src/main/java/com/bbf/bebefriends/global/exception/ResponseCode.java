package com.bbf.bebefriends.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    // 정상 code
    OK(HttpStatus.OK,"200", "Ok"),
    CREATED(HttpStatus.CREATED, "201", "Created"),
    NO_CONTENT(HttpStatus.NO_CONTENT, "204", "No Content"),

    // Common Error
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON001","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON002","권한이 잘못되었습니다"),
    _METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON003", "지원하지 않는 Http Method 입니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON004", "금지된 요청입니다."),

    // Member Error
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),
    NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4003", "이미 등록된 닉네임 입니다."),
    NICKNAME_INVALID(HttpStatus.BAD_REQUEST, "MEMBER4004", "닉네임이 잘못되었습니다."),
    MEMBER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4005", "이미 가입된 회원입니다."),

    // Community Error
    COMMUNITY_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMUNITY_POST4001", "게시물을 찾을 수 없습니다."),
    COMMUNITY_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMUNITY_CATEGORY4002", "카테고리를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMUNITY_COMMENT4003", "댓글을 찾을 수 없습니다."),

    // PostLike Error
    COMMUNITY_POST_LIKE_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST_LIKE4001", "해당 게시물에 좋아요를 누르지 않았습니다."),

    // File Error
    FILE_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "FILE4001", "파일 업로드에 실패했습니다."),

    // Token Error
    ACCESS_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "TOKEN4001", "헤더에 토큰 값이 없습니다"),
    TOKEN_EXPIRED_EXCEPTION(HttpStatus.BAD_REQUEST, "TOKEN4002", "토큰의 유효 기간이 만료되었습니다"),
    TOKEN_INVALID_EXCEPTION(HttpStatus.BAD_REQUEST, "TOKEN4003", "유효하지 않은 토큰입니다"),
    JWT_SIGNATURE_INVALID_EXCEPTION(HttpStatus.BAD_REQUEST, "TOKEN4004", "JWT 토큰이 올바르지 않습니다(header.payload.signature)"),
    FIREBASE_TOKEN_INVALID_EXCEPTION(HttpStatus.BAD_REQUEST, "TOKEN4005", "유효하지 않은 firebase 토큰입니다"),

    // AWS S3 Error
    S3_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "S34001", "파일 업로드에 실패했습니다."),
    S3_PATH_NOT_FOUND(HttpStatus.BAD_REQUEST, "S34002", "파일이 존재하지 않습니다."),

    // File Error
    FILE_MAX_SIZE_OVER(HttpStatus.PAYLOAD_TOO_LARGE, "FILE4001", "100MB 이하 파일만 업로드 할 수 있습니다."),
    FILE_CONTENT_TYPE_NOT_IMAGE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "FILE4002", "이미지 파일만 업로드할 수 있습니다."),
    FILE_SAVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "FILE4003", "파일 저장에 실패했습니다. 서버에 문의하세요.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public static ResponseCode valueOf(HttpStatus httpStatus) {
        if(httpStatus == null) {
            throw new GeneralException("HttpStatus is null.");
        }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if(httpStatus.is4xxClientError()) {
                        return ResponseCode._BAD_REQUEST;
                    } else if (httpStatus.is5xxServerError()) {
                        return ResponseCode._INTERNAL_SERVER_ERROR;
                    } else {
                        return ResponseCode.OK;
                    }
                });
    }
}
