package com.bbf.bebefriends.hotdeal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "핫딜 게시글", description = "핫딜 게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal/post")
public class HotDealPostController {

//    private final HotDealPostService hotDealPostService;
//
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
//
//    @Operation(summary = "핫딜 게시글 등록", description = "핫딜 게시글을 등록합니다.")
//    @PostMapping
//    public BaseResponse<HotDealPostDto> createHotDealPost(@RequestBody HotDealPostDto hotDealPostDto, @AuthenticationPrincipal UserDetailsImpl user) {
//        return BaseResponse.onSuccess(hotDealPostService.createHotDealPost(hotDealPostDto, user.getUser()), ResponseCode.OK);
//    }
//
//    @Operation(summary = "핫딜 게시글 수정", description = "핫딜 게시글을 수정합니다.")
//    @PutMapping
//    public BaseResponse<HotDealPostDto> updateHotDealPost(@RequestBody HotDealPostDto hotDealPostDto, @AuthenticationPrincipal UserDetailsImpl user) {
//        return BaseResponse.onSuccess(hotDealPostService.updateHotDealPost(hotDealPostDto, user.getUser()), ResponseCode.OK);
//    }
//
//    @Operation(summary = "핫딜 게시글 삭제", description = "핫딜 게시글을 삭제합니다.")
//    @DeleteMapping
//    public BaseResponse<Long> deleteHotDealPost(@RequestParam Long hotDealPostId, @AuthenticationPrincipal UserDetailsImpl user) {
//        return BaseResponse.onSuccess(hotDealPostService.deleteHotDealPost(hotDealPostId, user.getUser()), ResponseCode.OK);
//    }
//
//    @Operation(summary = "핫딜 게시글 상세 조회", description = "핫딜 게시글을 상세 조회합니다.")
//    @GetMapping("/detail")
//    public BaseResponse<HotDealPostDto> searchHotDealPostDetail(@RequestParam Long hotDealPostId, @AuthenticationPrincipal UserDetailsImpl user) {
//        return BaseResponse.onSuccess(hotDealPostService.searchHotDealPostDetail(hotDealPostId, user.getUser()), ResponseCode.OK);
//    }

}
