package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.community.service.CommunityCommentService;
import com.bbf.bebefriends.global.entity.BasePageResponse;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.global.swagger.postComment.CommunityChildCommentsPageResponseSpec;
import com.bbf.bebefriends.global.swagger.postComment.CommunityParentCommentsPageResponseSpec;
import com.bbf.bebefriends.global.swagger.postList.CommunityPostListPageResponseSpec;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
@Tag(name = "커뮤니티 댓글 관련 api", description = "댓글 생성·수정·삭제 api")
public class CommunityCommentController {
    private final CommunityCommentService communityCommentService;

    @PostMapping("/comment")
    @Operation(summary = "커뮤니티 댓글 생성", description = "게시물에 댓글을 게시합니다.")
    public BaseResponse<CommunityCommentDTO.CreateCommentResponse> createComment(@AuthenticationPrincipal UserDetailsImpl user,
                                                                                 @RequestBody CommunityCommentDTO.CreateCommentRequest request) {
        return BaseResponse.onSuccess(communityCommentService.createComment(user.getUser(), request), ResponseCode.OK);
    }

    @PatchMapping("/comment")
    @Operation(summary = "커뮤니티 댓글 수정", description = "본인이 생성한 댓글을 수정합니다.")
    public BaseResponse<String> updateComment(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestBody CommunityCommentDTO.UpdateCommentRequest request) {
        return BaseResponse.onSuccess(communityCommentService.updateComment(user.getUser(), request), ResponseCode.OK);
    }

    @DeleteMapping("/comment")
    @Operation(summary = "커뮤니티 댓글 삭제", description = "본인이 생성한 댓글을 삭제합니다.")
    public BaseResponse<String> deleteComment(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestBody CommunityCommentDTO.DeleteCommentRequest request) {
        return BaseResponse.onSuccess(communityCommentService.deleteComment(user.getUser(), request), ResponseCode.OK);
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
    public BaseResponse<BasePageResponse<CommunityCommentDTO.ParentCommentResponse>> getDefaultComments(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long postId,
            @RequestParam(value = "cursorId", required = false) Long cursorId,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        return BaseResponse.onSuccess(communityCommentService.getParentComments(user.getUser(), postId, cursorId, pageSize), ResponseCode.OK);
    }

    @GetMapping("/{parentId}/replies")
    @Operation(
            summary = "대댓글 목록 페이지네이션",
            description = """
            첫 페이지는 parentOffset, childOffset 둘 다 생략\n

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
    public BaseResponse<BasePageResponse<CommunityCommentDTO.ChildCommentDTO>> getReplies(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long parentId,
            @RequestParam(value = "cursorId", required = false) Long cursorId,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        return BaseResponse.onSuccess(communityCommentService.getChildComments(user.getUser(), parentId, cursorId, pageSize), ResponseCode.OK);
    }

//    @GetMapping("/post/{postId}/comment-details")
//    @Operation(summary = "게시글의 댓글 목록", description = """
//            첫 페이지는 parentOffset, childOffset 둘 다 생략\n
//
//            postId       PathVariable – 대상 게시글 ID\n
//            parentOffset QueryParam – “이전 요청에서 마지막으로 본 부모 댓글 id”\n
//            childOffset  QueryParam – “이전 요청에서 마지막으로 본 자식 댓글 id”\n
//            pageSize     QueryParam – 페이지 크기(항목 개수, 기본 20)
//            """)
//    public BaseResponse<List<CommunityCommentDTO.ParentCommentResponse>> getCommentDetails(
//            @PathVariable Long postId,
//            @RequestParam(value = "parentOffset", required = false) Long parentOffset,
//            @RequestParam(value = "childOffset",  required = false) Long childOffset,
//            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
//    ) {
//        List<CommunityCommentDTO.ParentCommentResponse> response = communityCommentService.getCommentsByPost(
//                postId,
//                parentOffset,
//                childOffset,
//                pageSize
//        );
//
//        return BaseResponse.onSuccess(response, ResponseCode.OK);
//    }
}
