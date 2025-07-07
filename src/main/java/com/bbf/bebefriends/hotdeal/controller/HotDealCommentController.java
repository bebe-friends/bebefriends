package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.global.entity.BasePageResponse;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.global.swagger.postComment.CommunityChildCommentsPageResponseSpec;
import com.bbf.bebefriends.global.swagger.postComment.CommunityParentCommentsPageResponseSpec;
import com.bbf.bebefriends.hotdeal.dto.HotDealCommentDto;
import com.bbf.bebefriends.hotdeal.service.HotDealCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "핫딜 게시글 댓글", description = "핫딜 게시글 댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal")
public class HotDealCommentController {

    private final HotDealCommentService hotDealCommentService;

//    @Operation(summary = "핫딜 댓글 조회", description = "핫딜 게시글의 댓글을 조회합니다.")
//    @GetMapping("/comment")
//    public BaseResponse<Page<HotDealCommentDto>> searchHotDealComment(@RequestParam Long hotDealPostId, Pageable pageable) {
//        return BaseResponse.onSuccess(hotDealPostService.searchHotDealComment(hotDealPostId,pageable), ResponseCode.OK);
//    }
//
    @Operation(summary = "핫딜 댓글 등록", description = "핫딜 게시글의 댓글을 등록합니다.")
    @PostMapping("/comment")
    public BaseResponse<HotDealCommentDto.CreateCommentResponse> createHotDealComment(
            @RequestBody HotDealCommentDto.CreateCommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(hotDealCommentService.createHotDealComment(request, user.getUser()), ResponseCode.OK);
    }

    @Operation(summary = "핫딜 댓글 수정", description = "핫딜 게시글의 댓글을 수정합니다.")
    @PatchMapping("/comment")
    public BaseResponse<HotDealCommentDto.UpdateCommentResponse> updateHotDealComment(
            @RequestBody HotDealCommentDto.UpdateCommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl user
    ) {
        return BaseResponse.onSuccess(hotDealCommentService.updateHotDealComment(request, user.getUser()), ResponseCode.OK);
    }

    @Operation(summary = "핫딜 댓글 삭제", description = "핫딜 게시글의 댓글을 삭제합니다.")
    @DeleteMapping("/comment")
    public BaseResponse<HotDealCommentDto.DeleteCommentResponse> deleteHotDealComment(
            @RequestBody HotDealCommentDto.DeleteCommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl user
    ) {
        return BaseResponse.onSuccess(hotDealCommentService.deleteHotDealComment(request.getCommentId(), user.getUser()), ResponseCode.OK);
    }

    @GetMapping("/post/{postId}/parent-comments")
    @Operation(
            summary = "게시글의 댓글 목록 페이지네이션, 대댓글은 기본 3개 출력",
            description = """
            postId       PathVariable – 대상 게시글 ID\n
            cursorId     QueryParam – “이전 요청에서 마지막으로 본 부모 댓글 id”\n
            pageSize     QueryParam – 페이지 크기(항목 개수, 기본 10)
            """,
            responses = @ApiResponse(
                    content = @Content(
                            schema = @Schema(
                                    implementation = CommunityParentCommentsPageResponseSpec.class
                            )
                    )
            )
    )
    public BaseResponse<BasePageResponse<HotDealCommentDto.ParentCommentResponse>> getDefaultComments(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long postId,
            @RequestParam(value = "cursorId", required = false) Long cursorId,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        return BaseResponse.onSuccess(hotDealCommentService.getParentComments(user.getUser(), postId, cursorId, pageSize), ResponseCode.OK);
    }

    @GetMapping("/{parentId}/replies")
    @Operation(
            summary = "대댓글 목록 페이지네이션",
            description = """
            첫 페이지는 cursorId 생략\n

            parentId     PathVariable – 대상 댓글 ID\n
            cursorId     QueryParam – “이전 요청에서 마지막으로 본 대댓글 id”\n
            pageSize     QueryParam – 페이지 크기(항목 개수, 기본 20)
            """,
            responses = @ApiResponse(
                    content = @Content(
                            schema = @Schema(
                                    implementation = CommunityChildCommentsPageResponseSpec.class
                            )
                    )
            )
    )
    public BaseResponse<BasePageResponse<HotDealCommentDto.ChildCommentDTO>> getReplies(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long parentId,
            @RequestParam(value = "cursorId", required = false) Long cursorId,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        return BaseResponse.onSuccess(hotDealCommentService.getChildComments(user.getUser(), parentId, cursorId, pageSize), ResponseCode.OK);
    }

    @Operation(summary = "커뮤니티 부모 댓글 조회", description = "대댓글의 부모 댓글을 조회합니다.")
    @GetMapping("/comment/{commentId}/parent")
    public BaseResponse<HotDealCommentDto.ParentOnlyResponse> getParentComment(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long commentId
    ) {
        return BaseResponse.onSuccess(hotDealCommentService.getParentOnly(user.getUser(), commentId), ResponseCode.OK);
    }
}
