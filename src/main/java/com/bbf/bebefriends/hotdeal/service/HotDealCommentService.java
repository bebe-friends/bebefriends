package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.community.entity.CommunityComment;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.community.repository.CommunityUserBlockRepository;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealCommentDto;
import com.bbf.bebefriends.hotdeal.entity.HotDealComment;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.repository.HotDealCommentRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealPostRepository;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HotDealCommentService {
    private final HotDealCommentRepository commentRepository;
    private final HotDealPostRepository hotDealPostRepository;
    private final CommunityUserBlockRepository communityUserBlockRepository;
    private final HotDealCommentRepository hotDealCommentRepository;

//    private Boolean checkBlocked(User currentUser, CommunityComment comment) {
//        if (currentUser.getRole() == UserRole.GUEST) {
//            return false;
//        }
//
//        // 유저가 작성자를 차단했는지
//        boolean userBlocked = communityUserBlockRepository
//                .existsByUserAndBlockedUser(currentUser, comment.getUser());
//        // 이 댓글을 직접 차단했는지
//        boolean commentBlocked = communityCommentBlockRepository
//                .existsByUserAndComment(currentUser, comment);
//
//        return userBlocked || commentBlocked;
//    }

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
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
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
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        // 댓글 삭제 처리
        hotDealComment.setDeletedAt();

        return new HotDealCommentDto.DeleteCommentResponse(hotDealComment.getId());
    }
}
