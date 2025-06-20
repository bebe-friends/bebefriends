package com.bbf.bebefriends.hotdeal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "핫딜 게시글 댓글", description = "핫딜 게시글 댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal/comment")
public class HotDealCommentController {

//    private final HotDealPostService hotDealPostService;
//
//    @Operation(summary = "핫딜 댓글 조회", description = "핫딜 게시글의 댓글을 조회합니다.")
//    @GetMapping("/comment")
//    public BaseResponse<Page<HotDealCommentDto>> searchHotDealComment(@RequestParam Long hotDealPostId, Pageable pageable) {
//        return BaseResponse.onSuccess(hotDealPostService.searchHotDealComment(hotDealPostId,pageable), ResponseCode.OK);
//    }
//
//    @Operation(summary = "핫딜 댓글 등록", description = "핫딜 게시글의 댓글을 등록합니다.")
//    @PostMapping("/comment")
//    public BaseResponse<HotDealCommentDto> createHotDealComment(@RequestBody HotDealCommentDto hotDealCommentDto, @AuthenticationPrincipal UserDetailsImpl user) {
//        return BaseResponse.onSuccess(hotDealPostService.createHotDealComment(hotDealCommentDto, user.getUser()), ResponseCode.OK);
//    }
//
//    @Operation(summary = "핫딜 댓글 수정", description = "핫딜 게시글의 댓글을 수정합니다.")
//    @PutMapping("/comment")
//    public BaseResponse<HotDealCommentDto> updateHotDealComment(@RequestBody HotDealCommentDto hotDealCommentDto, @AuthenticationPrincipal UserDetailsImpl user) {
//        return BaseResponse.onSuccess(hotDealPostService.updateHotDealComment(hotDealCommentDto, user.getUser()), ResponseCode.OK);
//    }
//
//    @Operation(summary = "핫딜 댓글 삭제", description = "핫딜 게시글의 댓글을 삭제합니다.")
//    @DeleteMapping("/comment")
//    public BaseResponse<Long> deleteHotDealComment(@RequestParam Long hotDealCommentId, @AuthenticationPrincipal UserDetailsImpl user) {
//        return BaseResponse.onSuccess(hotDealPostService.deleteHotDealComment(hotDealCommentId, user.getUser()), ResponseCode.OK);
//    }
}
