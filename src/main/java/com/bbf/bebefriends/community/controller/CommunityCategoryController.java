package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityCategoryDTO;
import com.bbf.bebefriends.community.entity.CommunityCategory;
import com.bbf.bebefriends.community.service.CommunityCategoryService;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
@Tag(name = "커뮤니티 게시물 카테고리 api", description = "전체 카테고리 조회·카테고리 생성·수정 api")
public class CommunityCategoryController {
    private final CommunityCategoryService communityCategoryService;

    // 전체 카테고리 조회
    @GetMapping("/category")
    @Operation(summary = "전체 카테고리 조회", description = "전체 카테고리를 조회합니다.")
    public BaseResponse<List<CommunityCategory>> getAllCategory() {
        return BaseResponse.onSuccess(communityCategoryService.getAllCategory(), ResponseCode.OK);
    }

    // 카테고리 만들기
    @PostMapping("/category")
    @Operation(summary = "카테고리 생성", description = "카테고리를 생성합니다.(관리자만 가능)")
    public BaseResponse<CommunityCategoryDTO.CreateCategoryResponse> createCategory(CommunityCategoryDTO.CreateCategoryRequest request,
                                                                                      @AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityCategoryService.saveCategory(request, user.getUser()), ResponseCode.OK);
    }

    // 카테고리 수정
    @PatchMapping("/category")
    @Operation(summary = "카테고리 이름 수정", description = "카테고리 이름을 수정합니다.(관리자만 가능)")
    public BaseResponse<CommunityCategoryDTO.UpdateCategoryResponse> updateCategory(CommunityCategoryDTO.UpdateCategoryRequest request,
                                                                                    @AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityCategoryService.updateCategory(request, user.getUser()), ResponseCode.OK);
    }
}
