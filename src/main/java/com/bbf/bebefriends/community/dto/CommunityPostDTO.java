package com.bbf.bebefriends.community.dto;

import com.bbf.bebefriends.community.entity.CommunityPost;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

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

    // 게시물 조회
    @Data
    public static class PostListRequest {

    }

    @Data
    public static class PostListResponse {

    }

    // 게시물 상세 페이지
    @Data
    public static class PostDetailsRequest {

    }

    @Data
    public static class PostDetailsResponse {

    }
}
