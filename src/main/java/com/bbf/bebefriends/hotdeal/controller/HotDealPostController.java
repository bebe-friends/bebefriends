package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;
import com.bbf.bebefriends.hotdeal.service.HotDealPostService;
import com.bbf.bebefriends.member.entity.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "핫딜 게시글", description = "핫딜 게시글 생성, 수정, 삭제, 상세 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal/post")
public class HotDealPostController {
    private final HotDealPostService hotDealPostService;

    // 핫딜 게시글 등록
    @Operation(summary = "핫딜 게시글 등록", description = "핫딜 게시글을 등록합니다.")
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public BaseResponse<HotDealPostDto.CreateHotDealPostResponse> createHotDealPost(@RequestPart(value = "data") @Valid HotDealPostDto.CreateHotDealPostRequest request,
                                                                                    @RequestPart(value = "images") List<MultipartFile> images,
                                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (!userDetails.getRole().equals("ADMIN")) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        return BaseResponse.onSuccess(hotDealPostService.createHotDealPost(request, images, userDetails.getUser()), ResponseCode.OK);
    }

    // 핫딜 게시글 수정
    @Operation(summary = "핫딜 게시글 수정", description = "핫딜 게시글을 수정합니다.")
    @PatchMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public BaseResponse<HotDealPostDto.UpdateHotDealPostResponse> createHotDealPost(@RequestPart(value = "data") @Valid HotDealPostDto.UpdateHotDealPostRequest request,
                                                                                    @RequestPart(value = "images") List<MultipartFile> images,
                                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (!userDetails.getRole().equals("ADMIN")) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        return BaseResponse.onSuccess(hotDealPostService.updateHotDealPost(request, images), ResponseCode.OK);
    }

    // 핫딜 게시글 삭제
    @Operation(summary = "핫딜 게시글 삭제", description = "핫딜 게시글을 삭제합니다.")
    @DeleteMapping()
    public BaseResponse<String> createHotDealPost(@Valid @RequestBody HotDealPostDto.DeleteHotDealPostRequest request,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (!userDetails.getRole().equals("ADMIN")) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        return BaseResponse.onSuccess(hotDealPostService.deleteHotDealPost(request), ResponseCode.OK);
    }

    // 핫딜 게시글 상세 조회


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
