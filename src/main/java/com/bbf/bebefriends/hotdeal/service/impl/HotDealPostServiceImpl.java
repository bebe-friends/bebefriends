package com.bbf.bebefriends.hotdeal.service.impl;

import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealCommentDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealLikeDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;
import com.bbf.bebefriends.hotdeal.entity.*;
import com.bbf.bebefriends.hotdeal.repository.*;
import com.bbf.bebefriends.hotdeal.service.HotDealPostService;
import com.bbf.bebefriends.member.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class HotDealPostServiceImpl implements HotDealPostService {

    private final HotDealRepository hotDealRepository;
    private final HotDealPostRepository hotDealPostRepository;
    private final HotDealCategoryRepository hotDealCategoryRepository;
    private final HotDealCommentRepository hotDealCommentRepository;
    private final HotDealLikeRepository hotDealLikeRepository;
    private final HotDealPostViewRepository hotDealPostViewRepository;


    @Override
    public Page<HotDealPostDto> searchAllHotDealPost(Pageable pageable) {
        return hotDealPostRepository.findAllByDeletedAtIsNull(pageable).map(HotDealPostDto::fromEntity);
    }

    @Override
    public Page<HotDealPostDto> searchCategoryHotDealPost(Long hotDealCategoryId,Pageable pageable) {
        // 핫딜 카테고리 조회
        HotDealCategory hotDealCategory = hotDealCategoryRepository.findById(hotDealCategoryId)
                .orElseThrow();

        // 조회한 카테고리를 통해 핫딜 게시글 조회
        return hotDealPostRepository.findByHotDeal_HotDealCategoryAndDeletedAtIsNull(hotDealCategory,pageable).map(HotDealPostDto::fromEntity);
    }

    public HotDealPostDto createHotDealPost(HotDealPostDto hotDealPostDto, User user) {
        // 핫딜 초기화
        HotDeal hotDeal = null;

        // 핫딜 식별자가 있는 경우 핫딜 조회
        if (hotDealPostDto.getHotDealId() != null) {
            hotDeal = hotDealRepository.findById(hotDealPostDto.getHotDealId())
                    .orElseThrow();
        }

        // 핫딜 게시글 생성
        HotDealPost hotDealPost = HotDealPost.builder()
                .user(user)
                .hotDeal(hotDeal)
                .title(hotDealPostDto.getTitle())
                .content(hotDealPostDto.getContent())
                .link(hotDealPostDto.getLink())
                .imgPath(hotDealPostDto.getImgPath())
                .status(hotDealPostDto.getStatus())
                .age(hotDealPostDto.getAge())
                .build();
        hotDealPostRepository.save(hotDealPost);

        return hotDealPostDto;
    }

    @Override
    public HotDealPostDto updateHotDealPost(HotDealPostDto hotDealPostDto, User user) {
        // 수정할 핫딜 게시글 조회
        HotDealPost hotDealPost = hotDealPostRepository.findById(hotDealPostDto.getId())
                .orElseThrow();

        // 게시글 작성자가 아닌 경우
        if (!(hotDealPost.getUser().equals(user))) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        // 기존과 다른 핫딜인 경우
        if (!hotDealPost.getHotDeal().getId().equals(hotDealPostDto.getHotDealId())) {
            // 수정된 핫딜로 세팅
            HotDeal hotDeal = hotDealRepository.findById(hotDealPostDto.getHotDealId())
                    .orElseThrow();
            hotDealPost.updateHotDeal(hotDeal);
        }

        // 핫딜 게시글 업데이트
        hotDealPost.update(hotDealPostDto);

        return hotDealPostDto;
    }

    @Override
    public Long deleteHotDealPost(Long hotDealPostId, User user) {
        // 삭제할 핫딜 게시글 조회
        HotDealPost hotDealPost = hotDealPostRepository.findById(hotDealPostId)
                .orElseThrow();

        // 게시글 작성자가 아닌 경우
        if (!(hotDealPost.getUser().equals(user))) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        // 핫딜 게시글 삭제 처리
        hotDealPost.delete();

        return hotDealPostId;
    }

    @Override
    public HotDealPostDto searchHotDealPostDetail(Long hotDealPostId, User user) {
        HotDealPost hotDealPost = hotDealPostRepository.findById(hotDealPostId)
                .orElseThrow();

        Optional<HotDealPostView> hotDealPostView = hotDealPostViewRepository.findByUserAndHotDealPost(user, hotDealPost);

        // 조회한 게시글이 아닌 경우
        if (hotDealPostView.isEmpty()) {
            // 핫딜 게시글 조회 저장
            HotDealPostView newhotDealPostView = HotDealPostView.builder()
                    .user(user)
                    .hotDealPost(hotDealPost)
                    .build();
            hotDealPostViewRepository.save(newhotDealPostView);

            // 조회수 증가
            hotDealPost.increaseViewCount();
        }

        return HotDealPostDto.fromEntity(hotDealPost);
    }

    @Override
    public HotDealCommentDto createHotDealComment(HotDealCommentDto hotDealCommentDto, User user) {
        // 핫딜 댓글 초기화
        HotDealComment repliedComment = null;

        // 핫딜 게시글 조회
        HotDealPost hotDealPost = hotDealPostRepository.findById(hotDealCommentDto.getHotDealPostId())
                .orElseThrow();


        // 핫딜 댓글 식별자가 있는 경우 핫딜 댓글 조회
        if (hotDealCommentDto.getRepliedCommentId() != null) {
            repliedComment = hotDealCommentRepository.findById(hotDealCommentDto.getRepliedCommentId())
                    .orElseThrow();
        }

        // 핫딜 댓글 생성
        HotDealComment hotDealComment = HotDealComment.builder()
                .user(user)
                .repliedComment(repliedComment)
                .hotDealPost(hotDealPost)
                .content(hotDealCommentDto.getContent())
                .build();
        hotDealCommentRepository.save(hotDealComment);

        return hotDealCommentDto;
    }

    @Override
    public HotDealCommentDto updateHotDealComment(HotDealCommentDto hotDealCommentDto, User user) {
        // 수정할 댓글 조회
        HotDealComment hotDealComment = hotDealCommentRepository.findById(hotDealCommentDto.getId())
                .orElseThrow();

        // 댓글 작성자가 아닌 경우
        if (!(hotDealComment.getUser().equals(user))) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        // 댓글 업데이트
        hotDealComment.update(hotDealCommentDto);

        return hotDealCommentDto;
    }

    @Override
    public Long deleteHotDealComment(Long hotDealCommentId, User user) {
        // 삭제 할 댓글 조회
        HotDealComment hotDealComment = hotDealCommentRepository.findById(hotDealCommentId)
                .orElseThrow();

        // 댓글 작성자가 아닌 경우
        if (!(hotDealComment.getUser().equals(user))) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        // 댓글 삭제 처리
        hotDealComment.delete();

        return hotDealCommentId;
    }

    @Override
    public Page<HotDealCommentDto> searchHotDealComment(Long hotDealPostId, Pageable pageable) {
        // 핫딜 게시글 조회
        HotDealPost hotDealPost = hotDealPostRepository.findById(hotDealPostId)
                .orElseThrow();

        return hotDealCommentRepository.findByHotDealPostAndDeletedAtIsNull(hotDealPost, pageable).map(HotDealCommentDto::fromEntity);
    }

    @Override
    public HotDealLikeDto likeHotDealPost(Long hotDealPostId, User user) {
        // 핫딜 게시글 조회
        HotDealPost hotDealPost = hotDealPostRepository.findById(hotDealPostId)
                .orElseThrow();

        // 좋아요가 되어있는지 조회
        Optional<HotDealLike> hotDealLike = hotDealLikeRepository.findByHotDealPostAndUser(hotDealPost, user);

        // 좋아요 여부 포함된 리턴용 dto 생성
        HotDealLikeDto hotDealLikeDto = HotDealLikeDto.builder()
                .hotDealPostId(hotDealPostId)
                .likeChk(true)
                .build();

        // 있으면 좋아요 취소
        if (hotDealLike.isPresent()) {
            hotDealLikeRepository.delete(hotDealLike.get());

            // 좋아요 수 감소
            hotDealPost.decreaseLikeCount();

            // 좋아요 여부 설정
            hotDealLikeDto.setLikeChk(false);

            return hotDealLikeDto;

        }

        // 좋아요 저장
        HotDealLike newHotDealLike = HotDealLike.builder()
                .hotDealPost(hotDealPost)
                .user(user)
                .build();
        hotDealLikeRepository.save(newHotDealLike);

        // 좋아요 수 증가
        hotDealPost.increaseLikeCount();

        return hotDealLikeDto;

    }

    @Override
    public HotDealLikeDto likeHotDealPostChk(Long hotDealPostId, User user) {
        // 핫딜 게시글 조회
        HotDealPost hotDealPost = hotDealPostRepository.findById(hotDealPostId)
                .orElseThrow();

        // 좋아요가 되어있는지 조회
        Optional<HotDealLike> hotDealLike = hotDealLikeRepository.findByHotDealPostAndUser(hotDealPost, user);

        // 좋아요 여부 포함된 리턴용 dto 생성
        HotDealLikeDto hotDealLikeDto = HotDealLikeDto.builder()
                .hotDealPostId(hotDealPostId)
                .likeChk(true)
                .build();

        // 좋아요가 안되어있는 경우
        if (hotDealLike.isEmpty()) {
            hotDealLikeDto.setLikeChk(false);
        }

        return hotDealLikeDto;
    }

}
