package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityCategoryDTO;
import com.bbf.bebefriends.community.entity.CommunityCategory;
import com.bbf.bebefriends.community.service.CommunityCategoryService;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommunityCategoryController {
    private final CommunityCategoryService communityCategoryService;

    // 전체 카테고리 조회
    @GetMapping("/community-category")
    public BaseResponse<List<CommunityCategory>> getAllCategory() {
        return BaseResponse.onSuccess(communityCategoryService.getAllCategory(), ResponseCode.OK);
    }

    // 카테고리 만들기
    @PostMapping("/community-category")
    public BaseResponse<CommunityCategoryDTO.CreateCategoryResponse> createCategory(CommunityCategoryDTO.CreateCategoryRequest request,
                                                                                      @AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityCategoryService.saveCategory(request, user.getUser()), ResponseCode.OK);
    }

    // 카테고리 수정
    @PatchMapping("/community-category")
    public BaseResponse<CommunityCategoryDTO.UpdateCategoryResponse> updateCategory(CommunityCategoryDTO.UpdateCategoryRequest request,
                                                                                    @AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityCategoryService.updateCategory(request, user.getUser()), ResponseCode.OK);
    }
}
