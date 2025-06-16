package com.bbf.bebefriends.community.service.impl;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.community.entity.CommunityComment;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.community.repository.CommunityCommentBlockRepository;
import com.bbf.bebefriends.community.repository.CommunityCommentRepository;
import com.bbf.bebefriends.community.repository.CommunityPostRepository;
import com.bbf.bebefriends.community.repository.CommunityUserBlockRepository;
import com.bbf.bebefriends.community.service.CommunityCommentService;
import com.bbf.bebefriends.global.entity.BasePageResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommentServiceImpl implements CommunityCommentService {
    private final CommunityCommentRepository communityCommentRepository;
    private final CommunityPostRepository communityPostRepository;
    private final CommunityUserBlockRepository communityUserBlockRepository;
    private final CommunityCommentBlockRepository communityCommentBlockRepository;

    private Boolean checkBlocked(User currentUser, CommunityComment comment) {
        if (currentUser.getRole() == UserRole.GUEST) {
            return false;
        }

        // 유저가 작성자를 차단했는지
        boolean userBlocked = communityUserBlockRepository
                .existsByUserAndBlockedUser(currentUser, comment.getUser());
        // 이 댓글을 직접 차단했는지
        boolean commentBlocked = communityCommentBlockRepository
                .existsByUserAndComment(currentUser, comment);

        return userBlocked || commentBlocked;
    }

    // 부모 댓글과 각 댓글마다 최대 3개 대댓글 조회
    @Override
    public BasePageResponse<CommunityCommentDTO.ParentCommentResponse> getParentComments(User user, Long postId, Long cursorId, int pageSize) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));

        Pageable parentLimit = PageRequest.of(0, pageSize);
        List<CommunityComment> parents = communityCommentRepository
                .findParentCommentsWithCursor(post, cursorId, parentLimit);

        List<CommunityCommentDTO.ParentCommentResponse> dtos = parents.stream()
                .map(parent -> {
                    Pageable childLimit = PageRequest.of(0, 3, Sort.by("id").ascending());

                    List<CommunityComment> children = communityCommentRepository
                            .findChildCommentsWithCursor(parent, null, childLimit);

                    List<CommunityCommentDTO.ChildCommentDTO> childDtos = children.stream()
                            .map(child -> CommunityCommentDTO.ChildCommentDTO.builder()
                                    .commentId(child.getId())
                                    .parentId(parent.getId())
                                    .authorName(child.getUser().getNickname())
                                    .content(child.getContent())
                                    .createdAt(child.getCreatedDate())
                                    .isBlocked(checkBlocked(user, child))
                                    .isDeleted(communityCommentRepository.existsByIdAndDeletedAtIsNotNull(child.getId()))
                                    .build())
                            .toList();

                    return CommunityCommentDTO.ParentCommentResponse.builder()
                            .commentId(parent.getId())
                            .authorId(parent.getUser().getUid())
                            .authorName(parent.getUser().getNickname())
                            .content(parent.getContent())
                            .createdAt(parent.getCreatedDate())
                            .replyComments(childDtos)
                            .totalReplyCount(communityCommentRepository.countByParentAndDeletedAtIsNull(parent))
                            .hasMoreComment(children.size() > 3)
                            .isBlocked(checkBlocked(user, parent))
                            .isDeleted(communityCommentRepository.existsByIdAndDeletedAtIsNotNull(parent.getId()))
                            .build();
                })
                .toList();

        Long lastCursor = dtos.isEmpty() ? null : dtos.get(dtos.size() - 1).getCommentId();

        return BasePageResponse.of(dtos, lastCursor);
    }

    // 대댓글 페이지네이션
    @Override
    public BasePageResponse<CommunityCommentDTO.ChildCommentDTO> getChildComments(User user, Long parentId, Long cursorId, int pageSize) {
        CommunityComment parent = communityCommentRepository.findById(parentId)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMENT_NOT_FOUND));

        Pageable Limit = PageRequest.of(0, pageSize);

        List<CommunityComment> children = communityCommentRepository
                .findChildCommentsWithCursor(parent, cursorId, Limit);

        List<CommunityCommentDTO.ChildCommentDTO> childDtos = children.stream()
                .map(child -> CommunityCommentDTO.ChildCommentDTO.builder()
                        .commentId(child.getId())
                        .parentId(parentId)
                        .authorName(child.getUser().getNickname())
                        .content(child.getContent())
                        .createdAt(child.getCreatedDate())
                        .isBlocked(checkBlocked(user, child))
                        .isDeleted(communityCommentRepository.existsByIdAndDeletedAtIsNotNull(child.getId()))
                        .build())
                .toList();

        Long lastCursor = childDtos.isEmpty() ? null : childDtos.get(childDtos.size() - 1).getCommentId();

        return BasePageResponse.of(childDtos, lastCursor);
    }

//    @Override
//    public List<CommunityCommentDTO.ParentCommentResponse> getCommentsByPost(
//            Long   postId,
//            Long   primaryOffset,
//            Long   subOffset,
//            int    limit
//    ) {
//        communityPostRepository.findById(postId)
//                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));
//
//        List<CommunityCommentDTO.CommentCursorProjection> rows = communityCommentRepository.findCommentByCursor(
//                postId,
//                primaryOffset,
//                subOffset,
//                limit
//        );
//
//        Set<Long> visitedParent = new HashSet<>();
//        List<CommunityCommentDTO.ParentCommentResponse> returnDTOs = new ArrayList<>();
//
//        for (CommunityCommentDTO.CommentCursorProjection row : rows) {
//            Long pId   = row.getParentId();
//            Long cId   = row.getChildId();
//
//            // 부모 댓글이 아직 추가되지 않았다면
//            if (!visitedParent.contains(pId)) {
//                CommunityCommentDTO.ParentCommentResponse parentDto = CommunityCommentDTO.ParentCommentResponse.builder()
//                        .commentId(pId)
//                        .authorId(row.getParentUserId())
//                        .authorName(row.getParentAuthorName())
//                        .content(row.getParentContent())
//                        .createdAt(row.getParentCreatedDate())
//                        .parentCommentId(null)
//                        .build();
//                returnDTOs.add(parentDto);
//                visitedParent.add(pId);
//            }
//
//            // childId가 null이 아니면
//            if (cId != null) {
//                CommunityCommentDTO.ParentCommentResponse childDto = CommunityCommentDTO.ParentCommentResponse.builder()
//                        .commentId(cId)
//                        .authorId(row.getChildUserId())
//                        .authorName(row.getChildAuthorName())
//                        .content(row.getChildContent())
//                        .createdAt(row.getChildCreatedDate())
//                        .parentCommentId(pId)
//                        .build();
//                returnDTOs.add(childDto);
//            }
//
//            if (returnDTOs.size() >= limit) {
//                break;
//            }
//        }
//
//        return returnDTOs;
//    }

//    public Optional<CommunityCommentDTO.CommentCursor> getNextCursor(CommunityCommentDTO.CommentCursorProjection lastRow) {
//        if (lastRow == null) {
//            return Optional.empty();
//        }
//        Long nextPrimary = lastRow.getParentId();
//        Long nextSub = lastRow.getChildId();  // 자식이 null이면 그냥 null 넘어감
//
//        return Optional.of(new CommunityCommentDTO.CommentCursor(nextPrimary, nextSub));
//    }

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

//    private CommunityCommentDTO.ParentCommentResponse toDto(CommunityComment comment) {
//        List<CommunityCommentDTO.ParentCommentResponse> replies = comment.getSubComments().stream()
//                .filter(c -> c.getDeletedAt() == null)
//                .map(this::toDto)
//                .toList();
//
//        return CommunityCommentDTO.ParentCommentResponse.builder()
//                .commentId(comment.getId())
//                .commenter(comment.getUser().getNickname())
//                .content(comment.getContent())
//                .createdAt(comment.getCreatedDate())
//                .replies(replies)
//                .build();
//    }
}
