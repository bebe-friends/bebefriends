package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealLikeDto;
import com.bbf.bebefriends.hotdeal.service.HotDealPostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Tag(name = "핫딜 게시글 좋아요", description = "핫딜 게시글 좋아요 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal/like")
public class HotDealPostLikeController {

    private final HotDealPostLikeService hotDealPostLikeService;

    @Operation(summary = "핫딜 좋아요", description = "핫딜 게시글을 좋아요 하거나 좋아요 취소합니다.")
    @PostMapping("/like")
    public BaseResponse<String> likeHotDealPost(@RequestParam Long hotDealPostId, @AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(hotDealPostLikeService.likeHotDealPost(hotDealPostId, user.getUser()), ResponseCode.OK);
    }
}
