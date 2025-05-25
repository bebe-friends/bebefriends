package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealCommentDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealLikeDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;
import com.bbf.bebefriends.hotdeal.service.HotDealPostService;
import com.bbf.bebefriends.member.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "핫딜 게시글", description = "핫딜 게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hot-deal-post")
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
    public BaseResponse<HotDealPostDto> createHotDealPost(@RequestBody HotDealPostDto hotDealPostDto, @AuthenticationPrincipal User user) {
        return BaseResponse.onSuccess(hotDealPostService.createHotDealPost(hotDealPostDto, user), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 게시글 수정", description = "핫딜 게시글을 수정합니다.")
    @PutMapping
    public BaseResponse<HotDealPostDto> updateHotDealPost(@RequestBody HotDealPostDto hotDealPostDto, @AuthenticationPrincipal User user) {
        return BaseResponse.onSuccess(hotDealPostService.updateHotDealPost(hotDealPostDto, user), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 게시글 삭제", description = "핫딜 게시글을 삭제합니다.")
    @DeleteMapping
    public BaseResponse<Long> deleteHotDealPost(@RequestParam Long hotDealPostId, @AuthenticationPrincipal User user) {
        return BaseResponse.onSuccess(hotDealPostService.deleteHotDealPost(hotDealPostId, user), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 게시글 상세 조회", description = "핫딜 게시글을 상세 조회합니다.")
    @GetMapping("/detail")
    public BaseResponse<HotDealPostDto> searchHotDealPostDetail(@RequestParam Long hotDealPostId, @AuthenticationPrincipal User user) {
        return BaseResponse.onSuccess(hotDealPostService.searchHotDealPostDetail(hotDealPostId, user), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 댓글 조회", description = "핫딜 게시글의 댓글을 조회합니다.")
    @GetMapping("/comment")
    public BaseResponse<Page<HotDealCommentDto>> searchHotDealComment(@RequestParam Long hotDealPostId, Pageable pageable) {
        return BaseResponse.onSuccess(hotDealPostService.searchHotDealComment(hotDealPostId,pageable), ResponseCode.OK);
    }

    @Operation(summary = "핫딜 댓글 등록", description = "핫딜 게시글의 댓글을 등록합니다.")
    @PostMapping("/comment")
    public BaseResponse<HotDealCommentDto> createHotDealComment(@RequestBody HotDealCommentDto hotDealCommentDto, @AuthenticationPrincipal User user) {
        return BaseResponse.onSuccess(hotDealPostService.createHotDealComment(hotDealCommentDto, user), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 댓글 수정", description = "핫딜 게시글의 댓글을 수정합니다.")
    @PutMapping("/comment")
    public BaseResponse<HotDealCommentDto> updateHotDealComment(@RequestBody HotDealCommentDto hotDealCommentDto, @AuthenticationPrincipal User user) {
        return BaseResponse.onSuccess(hotDealPostService.updateHotDealComment(hotDealCommentDto, user), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 댓글 삭제", description = "핫딜 게시글의 댓글을 삭제합니다.")
    @DeleteMapping("/comment")
    public BaseResponse<Long> deleteHotDealComment(@RequestParam Long hotDealCommentId, @AuthenticationPrincipal User user) {
        return BaseResponse.onSuccess(hotDealPostService.deleteHotDealComment(hotDealCommentId, user), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 좋아요", description = "핫딜 게시글을 좋아요 하거나 좋아요 취소합니다.")
    @PostMapping("/like")
    public BaseResponse<HotDealLikeDto> likeHotDealPost(@RequestParam Long hotDealPostId, @AuthenticationPrincipal User user) {
        return BaseResponse.onSuccess(hotDealPostService.likeHotDealPost(hotDealPostId, user), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 좋아요 여부 조회", description = "핫딜 게시글을 좋아요가 되어있는지 여부를 조회합니다.")
    @GetMapping("/like")
    public BaseResponse<HotDealLikeDto> likeHotDealPostChk(@RequestParam Long hotDealPostId, @AuthenticationPrincipal User user) {
        return BaseResponse.onSuccess(hotDealPostService.likeHotDealPostChk(hotDealPostId, user), ResponseCode.OK);

    }


}
