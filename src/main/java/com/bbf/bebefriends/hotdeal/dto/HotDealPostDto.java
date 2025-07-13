package com.bbf.bebefriends.hotdeal.dto;

import com.bbf.bebefriends.community.entity.CommunityImage;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.global.entity.AgeRange;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import com.bbf.bebefriends.member.entity.User;
import com.google.firebase.database.annotations.Nullable;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class HotDealPostDto {

    @Data
    public static class ChangeStatusRequest {
        private Long hotDealPostId;
    }

    // 게시글 등록 (필요한거: 카테고리(대분류), 핫딜 기록 체크)
    @Data
    public static class CreateHotDealPostRequest {
        private Long hotDealId;             // 핫딜 식별자

        @NonNull
        private String title;               // 핫딜 게시글 제목

        @NonNull
        private String content;             // 핫딜 게시글 내용

        private List<String> links;                // 링크

        @NonNull
        private Boolean status;              // 상태

        private List<Integer> age;                // 나이

        @NonNull
        private Long categoryId;        // 대분류

        private Boolean isConnected;
    }

    @Data
    public static class CreateHotDealPostResponse {
        private Long hotDealPostId;

        public CreateHotDealPostResponse(HotDealPost hotDealPost) {
            this.hotDealPostId = hotDealPost.getId();
        }
    }

    // 게시글 수정
    @Data
    public static class UpdateHotDealPostRequest {
        @NonNull
        private Long hotDealPostId;

        private Long hotDealId;

        private String content;

        private List<String> Links;

        private List<String> imgPaths;

        private String status;

        private Long categoryId;

        private List<Integer> age;
    }

    @Data
    public static class UpdateHotDealPostResponse {
        private Long postId;

        public UpdateHotDealPostResponse(HotDealPost post) {
            this.postId = post.getId();
        }
    }

    // 게시글 삭제
    @Data
    public static class DeleteHotDealPostRequest {
        @NonNull
        private Long hotDealPostId;
    }

    // 게시글 상세
    @Data
    @Builder
    public static class HotDealPostDetailsResponse {
        private Long postId;                    // 핫딜 게시글 식별자

        private Long userId;                // 회원 식별자

        private Long hotDealId;             // 핫딜 식별자

        private String title;               // 핫딜 게시글 제목

        private String content;             // 핫딜 게시글 내용

        private List<String> link;                // 링크

        private List<String> imgPath;             // 이미지 경로

        private Boolean status;              // 상태

        private Set<AgeRange> age;                // 나이

        private int viewCount;              // 조회수

        private int likeCount;              // 좋아요 수
    }
    // Entity -> dto
//    public static HotDealPostDto fromEntity(HotDealPost hotDealPost) {
//        return HotDealPostDto.builder()
//                .id(hotDealPost.getId())
//                .userId(hotDealPost.getUser().getUid())
//                .hotDealId(hotDealPost.getHotDeal().getId())
//                .title(hotDealPost.getTitle())
//                .content(hotDealPost.getContent())
//                .link(hotDealPost.getLink())
//                .imgPath(hotDealPost.getImgPath())
//                .status(hotDealPost.getStatus())
//                .age(hotDealPost.getAge())
//                .viewCount(hotDealPost.getViewCount())
//                .likeCount(hotDealPost.getLikeCount())
//                .build();
//    }

    @Data
    @NoArgsConstructor
    public static class HotDealListResponse {
        private Long postId;
        private String title;
        private String authorName;
        private String firstImageUrl;
        private LocalDateTime createdAt;
        private int viewCount;
        private int likeCount;
        private int commentCount;
        private String category;
        private Set<AgeRange> age;

        public static HotDealListResponse of(HotDealPost hotDealPost, HotDealCategory hotDealCategory) {
            HotDealListResponse response = new HotDealListResponse();

            String[] paths = hotDealPost.getImgPath().split(",");
            String firstImg = paths.length > 0 ? paths[0] : null;

            response.postId = hotDealPost.getId();
            response.title = hotDealPost.getTitle();
            response.authorName = hotDealPost.getUser().getNickname();
            response.firstImageUrl = firstImg;
            response.createdAt = hotDealPost.getCreatedDate();
            response.viewCount = hotDealPost.getViewCount();
            response.likeCount = hotDealPost.getLikeCount();
            response.commentCount = hotDealPost.getCommentCount();
            if (hotDealCategory != null) {
                response.category = hotDealCategory.getName();
            } else {
                response.category = "";
            }

            response.age = new LinkedHashSet<>(hotDealPost.getAgeRange());

            return response;
        }
    }
}
