package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealCommentDto;
import com.bbf.bebefriends.hotdeal.entity.HotDealComment;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import com.bbf.bebefriends.member.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotDealCommentService {

    // 핫딜 게시물 댓글 생성
//    public HotDealCommentDto createHotDealComment(HotDealCommentDto hotDealCommentDto, User user) {
//        // 핫딜 댓글 초기화
//        HotDealComment repliedComment = null;
//
//        // 핫딜 게시글 조회
//        HotDealPost hotDealPost = hotDealPostRepository.findById(hotDealCommentDto.getHotDealPostId())
//                .orElseThrow();
//
//
//        // 핫딜 댓글 식별자가 있는 경우 핫딜 댓글 조회
//        if (hotDealCommentDto.getRepliedCommentId() != null) {
//            repliedComment = hotDealCommentRepository.findById(hotDealCommentDto.getRepliedCommentId())
//                    .orElseThrow();
//        }
//
//        // 핫딜 댓글 생성
//        HotDealComment hotDealComment = HotDealComment.builder()
//                .user(user)
//                .repliedComment(repliedComment)
//                .hotDealPost(hotDealPost)
//                .content(hotDealCommentDto.getContent())
//                .build();
//        hotDealCommentRepository.save(hotDealComment);
//
//        return hotDealCommentDto;
//    }

    // 핫딜 게시글 댓글 수정
//    @Transactional
//    public HotDealCommentDto updateHotDealComment(HotDealCommentDto hotDealCommentDto, User user) {
//        // 수정할 댓글 조회
//        HotDealComment hotDealComment = hotDealCommentRepository.findById(hotDealCommentDto.getId())
//                .orElseThrow();
//
//        // 댓글 작성자가 아닌 경우
//        if (!(hotDealComment.getUser().equals(user))) {
//            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
//        }
//
//        // 댓글 업데이트
//        hotDealComment.update(hotDealCommentDto);
//
//        return hotDealCommentDto;
//    }

    // 핫딜 게시글 댓글 삭제
//    @Transactional
//    public Long deleteHotDealComment(Long hotDealCommentId, User user) {
//        // 삭제 할 댓글 조회
//        HotDealComment hotDealComment = hotDealCommentRepository.findById(hotDealCommentId)
//                .orElseThrow();
//
//        // 댓글 작성자가 아닌 경우
//        if (!(hotDealComment.getUser().equals(user))) {
//            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
//        }
//
//        // 댓글 삭제 처리
//        hotDealComment.delete();
//
//        return hotDealCommentId;
//    }
}
