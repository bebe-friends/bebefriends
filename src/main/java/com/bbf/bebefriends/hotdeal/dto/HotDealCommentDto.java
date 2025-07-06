package com.bbf.bebefriends.hotdeal.dto;

import com.bbf.bebefriends.hotdeal.entity.HotDealComment;
import lombok.*;

import java.time.LocalDateTime;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder(toBuilder = true)
public class HotDealCommentDto {

    @Data
    public static class CreateCommentRequest {
        @NonNull
        private Long hotDealPostId;             // 핫딜 게시글 식별자

        private Long parentId;

        @NonNull
        private String content;                 // 내용
    }

    @Data
    @AllArgsConstructor
    public static class CreateCommentResponse {
        private Long commentId;
    }

    @Data
    public static class UpdateCommentRequest {
        @NonNull
        private Long commentId;

        @NonNull
        private String content;
    }

    @Data
    @AllArgsConstructor
    public static class UpdateCommentResponse {
        private Long commentId;
    }

    @Data
    public static class DeleteCommentRequest {
        private Long commentId;
    }

    @Data
    @AllArgsConstructor
    public static class DeleteCommentResponse {
        private Long commentId;
    }


    // Entity -> dto
//    public static HotDealCommentDto fromEntity(HotDealComment hotDealComment) {
//
//        return HotDealCommentDto.builder()
//                .id(hotDealComment.getId())
//                .userId(hotDealComment.getUser().getUid())
//                .hotDealPostId(hotDealComment.getHotDealPost().getId())
//                .content(hotDealComment.getContent())
//                .deletedAt(hotDealComment.getDeletedAt())
//                .build();
//    }
}
