package com.bbf.bebefriends.community.dto;

import com.bbf.bebefriends.community.entity.CommunityPost;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class CommunityPostDTO {
    // 생성
    @Data
    public static class CreatePostRequest {
        @NonNull
        private String category;
        @NonNull
        private String title;
        @NonNull
        private String content;

        private List<MultipartFile> img;
        private List<String> link;
    }

    @Data
    public static class CreatePostResponse {
        private Long postId;

        public CreatePostResponse(CommunityPost post) {
            this.postId = post.getId();
        }
    }

    // 수정
    @Data
    public static class UpdatePostRequest {
        @NonNull
        private Long postId;
        @NonNull
        private String category;
        @NonNull
        private String title;
        @NonNull
        private String content;

        private List<MultipartFile> newImages;
        private List<String> existingImageUrls;

        private List<String> newLinks;
    }

    @Data
    public static class UpdatePostResponse {
        private Long postId;

        public UpdatePostResponse(CommunityPost post) {
            this.postId = post.getId();
        }
    }

    // 삭제
    @Data
    public static class DeletePostRequest {
        @NonNull
        private Long postId;
    }

    // 게시물 목록
    @Data
    @Builder
    public static class PostListResponse {
        private Long postId;
        private String title;
        private String author;
        private String content;
        private String firstImageUrl;
        private LocalDateTime createdAt;
        private int viewCount;
        private int likeCount;
        private int commentCount;
    }

    // 게시물 상세 페이지
    @Data
    @AllArgsConstructor
    @Builder
    public static class PostDetailsResponse {
        private Long postId;
        private String title;
        private String author;
        private LocalDateTime createdAt;
        private int viewCount;
        private int likeCount;
        private int commentCount;
        private String content;
        private List<String> imageUrls;
        private List<String> links;
        private List<CommunityCommentDTO.CommentDetails> comments; // 댓글(대댓글 포함)
    }
}
