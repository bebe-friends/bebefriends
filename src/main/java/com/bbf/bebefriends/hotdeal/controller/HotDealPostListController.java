package com.bbf.bebefriends.hotdeal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotDealPostListController {
    //    @Operation(summary = "핫딜 게시글 전체 조회", description = "전체 핫딜 게시글을 조회합니다.")
//    @GetMapping
//    public BaseResponse<Page<HotDealPostDto>> searchAllHotDealPost(Pageable pageable) {
//        return BaseResponse.onSuccess(hotDealPostService.searchAllHotDealPost(pageable), ResponseCode.OK);
//    }
//
//    @Operation(summary = "핫딜 게시글 카테고리별 조회", description = "카테고리별로 핫딜 게시글을 조회합니다.")
//    @GetMapping("/category")
//    public BaseResponse<Page<HotDealPostDto>> searchCategoryHotDealPost(@RequestParam Long hotDealCategoryId,Pageable pageable) {
//        return BaseResponse.onSuccess(hotDealPostService.searchCategoryHotDealPost(hotDealCategoryId,pageable), ResponseCode.OK);
//    }
}
