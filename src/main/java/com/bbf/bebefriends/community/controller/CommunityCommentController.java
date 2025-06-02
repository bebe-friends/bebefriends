package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.community.service.CommunityCommentService;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
@Tag(name = "커뮤니티 댓글 관련 api", description = "댓글 생성·수정·삭제 api")
public class CommunityCommentController {
    private final CommunityCommentService communityCommentService;

    @PostMapping("/comment")
    @Operation(summary = "커뮤니티 댓글 생성", description = "게시물에 댓글을 게시합니다.")
    public BaseResponse<String> createComment(@AuthenticationPrincipal UserDetailsImpl user,
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

    /**
     * 예시 요청:
     *   GET /api/v1/posts/123/comments/flat?parentOffset=2&childOffset=21&limit=5
     *
     * 첫 페이지는 parentOffset, childOffset 둘 다 생략:
     *   GET /api/v1/posts/123/comments/flat?limit=5
     *
     * @param postId        PathVariable – 대상 게시글 ID
     * @param parentOffset  QueryParam – “이전 요청에서 마지막으로 본 parent.comment_id” (optional)
     * @param childOffset   QueryParam – “이전 요청에서 마지막으로 본 child.comment_id” (optional)
     * @param pageSize      QueryParam – 페이지 크기(항목 개수, 필수)
     */
    @GetMapping("/post/{postId}/comment-details")
    @Operation(summary = "게시글의 댓글 목록", description = "선택된 게시물의 댓글 목록을 가져옵니다.")
    public BaseResponse<CommunityCommentDTO.CommentDetailsResponse> getCommentDetails(
            @PathVariable Long postId,
            @RequestParam(value = "parentOffset", required = false) Long parentOffset,
            @RequestParam(value = "childOffset",  required = false) Long childOffset,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        List<CommunityCommentDTO.CommentCursorProjection> rows = communityCommentService.getCommentsByPost(
                postId,
                parentOffset,
                childOffset,
                pageSize
        );

        CommunityCommentDTO.CommentCursorProjection lastRow = rows.isEmpty() ? null : rows.get(rows.size() - 1);
        Optional<CommunityCommentDTO.CommentCursor> nextCursor = communityCommentService.getNextCursor(lastRow);

        CommunityCommentDTO.CommentDetailsResponse response = new CommunityCommentDTO.CommentDetailsResponse(rows, nextCursor.orElse(null));
        return BaseResponse.onSuccess(response, ResponseCode.OK);
    }
}
