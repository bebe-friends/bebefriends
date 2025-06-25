package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.global.entity.BasePageResponse;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.global.swagger.postList.CommunityPostListPageResponseSpec;
import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.service.HotDealPostListService;
import com.bbf.bebefriends.member.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "핫딜 게시글 목록", description = "핫딜 게시글 목록 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal/list")
public class HotDealPostListController {
    private final HotDealPostListService hotDealPostListService;

    @GetMapping
    @Operation(
            summary = "핫딜 게시물 조회",
            description = """
                          category, keyword 조합에 따라 전체/맞춤 조회\n
                          첫 조회 시 cursorId 는 제외\n
                          기본 pageSize 는 20
                          """,
            responses = @ApiResponse(
                    content = @Content(
                            schema = @Schema(
                                    implementation = CommunityPostListPageResponseSpec.class
                            )
                    )
            )
    )
    public BaseResponse<BasePageResponse<HotDealPostDto.HotDealListResponse>> getHotDeals(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam(value = "cursorId", required = false) Long cursorId,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "type", required = false) PostType type
    ) {
        User currentUser = user.getUser();

        BasePageResponse<HotDealPostDto.HotDealListResponse> response;

//        if (keyword != null && !keyword.isBlank()) {
//            // 검색어가 있으면 검색 서비스 호출
//            response = hotDealPostListService.getHotDealsBySearch(keyword, currentUser, cursorId, pageSize);
//        }

        switch (type) {
            case RECOMMENDED -> response = hotDealPostListService.getHotDealsByRecommended(currentUser, cursorId, pageSize);
            case ALL         -> response = hotDealPostListService.getAllHotDeals(currentUser, cursorId, pageSize);
            default          -> throw new HotDealControllerAdvice(ResponseCode._BAD_REQUEST);
        }

        return BaseResponse.onSuccess(response, ResponseCode.OK);
    }

    public enum PostType {
        RECOMMENDED,
        ALL
    }
}
