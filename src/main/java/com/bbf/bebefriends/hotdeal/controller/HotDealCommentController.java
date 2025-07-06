package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealCommentDto;
import com.bbf.bebefriends.hotdeal.service.HotDealCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "핫딜 게시글 댓글", description = "핫딜 게시글 댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal/comment")
public class HotDealCommentController {

    private final HotDealCommentService hotDealCommentService;

//    @Operation(summary = "핫딜 댓글 조회", description = "핫딜 게시글의 댓글을 조회합니다.")
//    @GetMapping("/comment")
//    public BaseResponse<Page<HotDealCommentDto>> searchHotDealComment(@RequestParam Long hotDealPostId, Pageable pageable) {
//        return BaseResponse.onSuccess(hotDealPostService.searchHotDealComment(hotDealPostId,pageable), ResponseCode.OK);
//    }
//
    @Operation(summary = "핫딜 댓글 등록", description = "핫딜 게시글의 댓글을 등록합니다.")
    @PostMapping("")
    public BaseResponse<HotDealCommentDto.CreateCommentResponse> createHotDealComment(
            @RequestBody HotDealCommentDto.CreateCommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(hotDealCommentService.createHotDealComment(request, user.getUser()), ResponseCode.OK);
    }

    @Operation(summary = "핫딜 댓글 수정", description = "핫딜 게시글의 댓글을 수정합니다.")
    @PatchMapping("")
    public BaseResponse<HotDealCommentDto.UpdateCommentResponse> updateHotDealComment(
            @RequestBody HotDealCommentDto.UpdateCommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl user
    ) {
        return BaseResponse.onSuccess(hotDealCommentService.updateHotDealComment(request, user.getUser()), ResponseCode.OK);
    }

    @Operation(summary = "핫딜 댓글 삭제", description = "핫딜 게시글의 댓글을 삭제합니다.")
    @DeleteMapping("")
    public BaseResponse<HotDealCommentDto.DeleteCommentResponse> deleteHotDealComment(
            @RequestBody HotDealCommentDto.DeleteCommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl user
    ) {
        return BaseResponse.onSuccess(hotDealCommentService.deleteHotDealComment(request.getCommentId(), user.getUser()), ResponseCode.OK);
    }
}
