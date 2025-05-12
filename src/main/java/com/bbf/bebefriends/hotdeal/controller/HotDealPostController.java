package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealCommentDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;
import com.bbf.bebefriends.hotdeal.service.HotDealPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "핫딜 게시글", description = "핫딜 게시글 관련 API @유석균")
@RestController
@RequiredArgsConstructor
@RequestMapping("/hot-deal-post")
public class HotDealPostController {

    private final HotDealPostService hotDealPostService;

    @Operation(summary = "핫딜 게시글 전체 조회", description = "전체 핫딜 게시글을 조회합니다.")
    @GetMapping
    public BaseResponse<Page<HotDealPostDto>> searchAllHotDealPost(Pageable pageable) {
        return BaseResponse.onSuccess(hotDealPostService.searchAllHotDealPost(pageable), ResponseCode.OK);
    }

    @Operation(summary = "핫딜 게시글 카테고리별 조회", description = "카테고리별로 핫딜 게시글을 조회합니다.")
    @GetMapping("/category")
    public BaseResponse<Page<HotDealPostDto>> searchCategoryHotDealPost(@RequestParam Long hotDealCategoryId,Pageable pageable) {
        return BaseResponse.onSuccess(hotDealPostService.searchCategoryHotDealPost(hotDealCategoryId,pageable), ResponseCode.OK);
    }

    @Operation(summary = "핫딜 게시글 등록", description = "핫딜 게시글을 등록합니다.")
    @PostMapping
    public BaseResponse<HotDealPostDto> createHotDealPost(@RequestBody HotDealPostDto hotDealPostDto) {
        return BaseResponse.onSuccess(hotDealPostService.createHotDealPost(hotDealPostDto), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 댓글 조회", description = "핫딜 게시글의 댓글을 조회합니다.")
    @GetMapping("/comment")
    public BaseResponse<Page<HotDealCommentDto>> searchHotDealComment(@RequestParam Long hotDealPostId, Pageable pageable) {
        return BaseResponse.onSuccess(hotDealPostService.searchHotDealComment(hotDealPostId,pageable), ResponseCode.OK);
    }

    @Operation(summary = "핫딜 댓글 등록", description = "핫딜 게시글의 댓글을 등록합니다.")
    @PostMapping("/comment")
    public BaseResponse<HotDealCommentDto> createHotDealComment(@RequestBody HotDealCommentDto hotDealCommentDto) {
        return BaseResponse.onSuccess(hotDealPostService.createHotDealComment(hotDealCommentDto), ResponseCode.OK);

    }

}
