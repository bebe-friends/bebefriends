package com.bbf.bebefriends.hotdeal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "핫딜 게시글 좋아요", description = "핫딜 게시글 좋아요 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal/like")
public class HotDealPostLikeController {

//    private final HotDealPostService hotDealPostService;
//
//    @Operation(summary = "핫딜 좋아요", description = "핫딜 게시글을 좋아요 하거나 좋아요 취소합니다.")
//    @PostMapping("/like")
//    public BaseResponse<HotDealLikeDto> likeHotDealPost(@RequestParam Long hotDealPostId, @AuthenticationPrincipal UserDetailsImpl user) {
//        return BaseResponse.onSuccess(hotDealPostService.likeHotDealPost(hotDealPostId, user.getUser()), ResponseCode.OK);
//    }
//
//    @Operation(summary = "핫딜 좋아요 여부 조회", description = "핫딜 게시글을 좋아요가 되어있는지 여부를 조회합니다.")
//    @GetMapping("/like")
//    public BaseResponse<HotDealLikeDto> likeHotDealPostChk(@RequestParam Long hotDealPostId, @AuthenticationPrincipal UserDetailsImpl user) {
//        return BaseResponse.onSuccess(hotDealPostService.likeHotDealPostChk(hotDealPostId, user.getUser()), ResponseCode.OK);
//    }

}
