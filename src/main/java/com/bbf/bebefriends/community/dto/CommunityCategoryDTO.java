package com.bbf.bebefriends.community.dto;

import com.bbf.bebefriends.community.entity.CommunityCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

public class CommunityCategoryDTO {
    // 카테고리
    // 생성
    @Data
    @AllArgsConstructor
    public static class CreateCategoryRequest {
        @NonNull
        private String name;
    }

    @Data
    public static class CreateCategoryResponse {
        private Long id;
        private String name;

        public CreateCategoryResponse(CommunityCategory category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }

    // 수정
    @Data
    @AllArgsConstructor
    public static class UpdateCategoryRequest {
        @NonNull
        private Long id;

        @NonNull
        private String newName;
    }

    @Data
    public static class UpdateCategoryResponse {
        private Long id;
        private String name;

        public UpdateCategoryResponse(CommunityCategory category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }
}
