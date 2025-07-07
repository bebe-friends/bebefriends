package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.community.repository.CommunityUserBlockRepository;
import com.bbf.bebefriends.global.entity.BasePageResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealCommentDto;
import com.bbf.bebefriends.hotdeal.entity.HotDealComment;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.repository.HotDealCommentBlockRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealCommentRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealPostRepository;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HotDealCommentService {
    private final HotDealCommentRepository commentRepository;
    private final HotDealPostRepository hotDealPostRepository;
    private final CommunityUserBlockRepository communityUserBlockRepository;
    private final HotDealCommentRepository hotDealCommentRepository;
    private final HotDealCommentBlockRepository hotDealCommentBlockRepository;

    private Boolean checkBlocked(User currentUser, HotDealComment comment) {
        if (currentUser.getRole() == UserRole.GUEST) {
            return false;
        }

        // 유저가 작성자를 차단했는지
        boolean userBlocked = communityUserBlockRepository
                .existsByUserAndBlockedUser(currentUser, comment.getUser());
        // 이 댓글을 직접 차단했는지
        boolean commentBlocked = hotDealCommentBlockRepository
                .existsByUserAndComment(currentUser, comment);

        return userBlocked || commentBlocked;
    }

    public HotDealCommentDto.ParentOnlyResponse getParentOnly(User user, Long commentId) {
        HotDealComment reply = commentRepository.findById(commentId)
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_COMMENT_NOT_FOUND));

        if (reply.getParentComment() == null) {
            throw new HotDealControllerAdvice(ResponseCode.HOTDEAL_COMMENT_NOT_FOUND);
        }
        HotDealComment parent = reply.getParentComment();

        return HotDealCommentDto.ParentOnlyResponse.builder()
                .commentId(parent.getId())
                .authorId(parent.getUser().getUid())
                .authorName(parent.getUser().getNickname())
                .content(parent.getContent())
                .createdAt(parent.getCreatedDate())
                .isDeleted(commentRepository.existsByIdAndDeletedAtIsNotNull(parent.getId()))
                .isBlocked(checkBlocked(user, parent))
                .build();
    }

    // 부모 댓글과 각 댓글마다 최대 3개 대댓글 조회
    public BasePageResponse<HotDealCommentDto.ParentCommentResponse> getParentComments(User user, Long postId, Long cursorId, int pageSize) {
        HotDealPost post = hotDealPostRepository.findById(postId)
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_POST_NOT_FOUND));

        Pageable parentLimit = PageRequest.of(0, pageSize);
        List<HotDealComment> parents = commentRepository
                .findParentCommentsWithCursor(post, cursorId, parentLimit);

        List<HotDealCommentDto.ParentCommentResponse> dtos = parents.stream()
                .map(parent -> {
                    Pageable childLimit = PageRequest.of(0, 3, Sort.by("id").ascending());

                    List<HotDealComment> children = commentRepository
                            .findChildCommentsWithCursor(parent, null, childLimit);

                    List<HotDealCommentDto.ChildCommentDTO> childDtos = children.stream()
                            .map(child -> HotDealCommentDto.ChildCommentDTO.builder()
                                    .commentId(child.getId())
                                    .parentId(parent.getId())
                                    .authorName(child.getUser().getNickname())
                                    .content(child.getContent())
                                    .createdAt(child.getCreatedDate())
                                    .isBlocked(checkBlocked(user, child))
                                    .isDeleted(commentRepository.existsByIdAndDeletedAtIsNotNull(child.getId()))
                                    .build())
                            .toList();

                    return HotDealCommentDto.ParentCommentResponse.builder()
                            .commentId(parent.getId())
                            .authorId(parent.getUser().getUid())
                            .authorName(parent.getUser().getNickname())
                            .content(parent.getContent())
                            .createdAt(parent.getCreatedDate())
                            .replyComments(childDtos)
                            .totalReplyCount(commentRepository.countByParentCommentAndDeletedAtIsNull(parent))
                            .hasMoreComment(children.size() > 3)
                            .isBlocked(checkBlocked(user, parent))
                            .isDeleted(commentRepository.existsByIdAndDeletedAtIsNotNull(parent.getId()))
                            .build();
                })
                .toList();

        Long lastCursor = dtos.isEmpty() ? null : dtos.get(dtos.size() - 1).getCommentId();

        return BasePageResponse.of(dtos, lastCursor);
    }

    // 대댓글 페이지네이션
    public BasePageResponse<HotDealCommentDto.ChildCommentDTO> getChildComments(User user, Long parentId, Long cursorId, int pageSize) {
        HotDealComment parent = commentRepository.findById(parentId)
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_COMMENT_NOT_FOUND));

        Pageable Limit = PageRequest.of(0, pageSize);

        List<HotDealComment> children = commentRepository
                .findChildCommentsWithCursor(parent, cursorId, Limit);

        List<HotDealCommentDto.ChildCommentDTO> childDtos = children.stream()
                .map(child -> HotDealCommentDto.ChildCommentDTO.builder()
                        .commentId(child.getId())
                        .parentId(parentId)
                        .authorName(child.getUser().getNickname())
                        .content(child.getContent())
                        .createdAt(child.getCreatedDate())
                        .isBlocked(checkBlocked(user, child))
                        .isDeleted(commentRepository.existsByIdAndDeletedAtIsNotNull(child.getId()))
                        .build())
                .toList();

        Long lastCursor = childDtos.isEmpty() ? null : childDtos.get(childDtos.size() - 1).getCommentId();

        return BasePageResponse.of(childDtos, lastCursor);
    }

    // 핫딜 게시물 댓글 생성
    public HotDealCommentDto.CreateCommentResponse createHotDealComment(HotDealCommentDto.CreateCommentRequest request, User user) {
        // 핫딜 게시글 조회
        HotDealPost hotDealPost = hotDealPostRepository.findById(request.getHotDealPostId())
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_POST_NOT_FOUND));

        HotDealComment parent = null;
        if (request.getParentId() != null) {
            parent = hotDealCommentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_COMMENT_NOT_FOUND));
        }

        HotDealComment comment = HotDealComment.createComment(hotDealPost, user, parent, request);
        hotDealCommentRepository.save(comment);

        hotDealPost.increaseCommentCount();
        return new HotDealCommentDto.CreateCommentResponse(comment.getId());
    }

    // 핫딜 게시글 댓글 수정
    @Transactional
    public HotDealCommentDto.UpdateCommentResponse updateHotDealComment(HotDealCommentDto.UpdateCommentRequest request, User user) {
        // 수정할 댓글 조회
        HotDealComment hotDealComment = hotDealCommentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_COMMENT_NOT_FOUND));

        // 댓글 작성자가 아닌 경우
        if (!(hotDealComment.getUser().getUid().equals(user.getUid()))) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        // 댓글 업데이트
        hotDealComment.update(request);

        return new HotDealCommentDto.UpdateCommentResponse(hotDealComment.getId());
    }

    // 핫딜 게시글 댓글 삭제
    @Transactional
    public HotDealCommentDto.DeleteCommentResponse deleteHotDealComment(Long hotDealCommentId, User user) {
        // 삭제 할 댓글 조회
        HotDealComment hotDealComment = hotDealCommentRepository.findById(hotDealCommentId)
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_COMMENT_NOT_FOUND));

        // 댓글 작성자가 아닌 경우
        if (!(hotDealComment.getUser().getUid().equals(user.getUid()))) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        // 댓글 삭제 처리
        hotDealComment.setDeletedAt();

        return new HotDealCommentDto.DeleteCommentResponse(hotDealComment.getId());
    }
}
