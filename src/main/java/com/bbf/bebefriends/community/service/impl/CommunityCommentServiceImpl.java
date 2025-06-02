package com.bbf.bebefriends.community.service.impl;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.community.entity.CommunityComment;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.community.repository.CommunityCommentRepository;
import com.bbf.bebefriends.community.repository.CommunityPostRepository;
import com.bbf.bebefriends.community.service.CommunityCommentService;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommentServiceImpl implements CommunityCommentService {
    private final CommunityCommentRepository communityCommentRepository;
    private final CommunityPostRepository communityPostRepository;

    /**
     * 커서 기반(flatten) 댓글 페이지네이션
     *
     * @param postId         대상 게시글 ID
     * @param primaryOffset  마지막으로 본 부모ID (null 이면 첫 페이지)
     * @param subOffset      마지막으로 본 자식ID (해당 부모에서)
     *                       → “부모만”인 경우 subOffset을 0으로 넘기면 곧바로 다음 부모부터 시작
     * @param limit          페이지 크기(항목 개수)
     * @return List<CommentCursorProjection> – 보려는 댓글 리스트
     */
    @Override
    public List<CommunityCommentDTO.CommentCursorProjection> getCommentsByPost(
            Long   postId,
            Long   primaryOffset,
            Long   subOffset,
            int    limit
    ) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));

        return communityCommentRepository.findCommentByCursor(
                postId,
                primaryOffset,
                subOffset,
                limit
        );
    }

    /**
     * “다음 페이지 커서” 계산 헬퍼
     *
     * @param lastRow 마지막으로 반환된 CommentCursorProjection
     * @return (parentId, childId) 쌍; 다음 페이지 요청 시 넘겨 줄 커서
     */
    public Optional<CommunityCommentDTO.CommentCursor> getNextCursor(CommunityCommentDTO.CommentCursorProjection lastRow) {
        if (lastRow == null) {
            return Optional.empty();
        }
        Long nextPrimary = lastRow.getParentId();
        Long nextSub = lastRow.getChildId();  // 자식이 null이면 그냥 null 넘어감

        return Optional.of(new CommunityCommentDTO.CommentCursor(nextPrimary, nextSub));
    }

    // TODO: 대댓글의 댓글은 불가능. 깊이는 최대 2까지만 설정하도록
    @Override
    @Transactional
    public String createComment(User user, CommunityCommentDTO.CreateCommentRequest request) {
        CommunityPost post = communityPostRepository.findById(request.getPostId())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));
        CommunityComment parent = null;
        if (request.getParentId() != null) {
            parent = communityCommentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMENT_NOT_FOUND));
        }

        CommunityComment comment = CommunityComment.createComment(post, user, parent, request);
        communityCommentRepository.save(comment);

        post.increaseCommentCount();
        return "댓글을 작성하였습니다.";
    }

    @Override
    @Transactional
    public String updateComment(User user, CommunityCommentDTO.UpdateCommentRequest request) {
        CommunityComment comment = communityCommentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMENT_NOT_FOUND));
        if (!(comment.getUser().getUid().equals(user.getUid()))) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        comment.updateComment(request.getContent());
        return "댓글을 수정하였습니다.";
    }

    @Override
    @Transactional
    public String deleteComment(User user, CommunityCommentDTO.DeleteCommentRequest request) {
        CommunityComment comment = communityCommentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMENT_NOT_FOUND));
        if (!(comment.getUser().getUid().equals(user.getUid()))) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        comment.setDeletedAt();
        CommunityPost post = comment.getPost();
        post.decreaseCommentCount();

        return "댓글을 삭제하였습니다.";
    }

//    private CommunityCommentDTO.CommentDetails toDto(CommunityComment comment) {
//        List<CommunityCommentDTO.CommentDetails> replies = comment.getSubComments().stream()
//                .filter(c -> c.getDeletedAt() == null)
//                .map(this::toDto)
//                .toList();
//
//        return CommunityCommentDTO.CommentDetails.builder()
//                .commentId(comment.getId())
//                .commenter(comment.getUser().getNickname())
//                .content(comment.getContent())
//                .createdAt(comment.getCreatedDate())
//                .replies(replies)
//                .build();
//    }
}
