package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.hotdeal.dto.HotDealLikeDto;
import com.bbf.bebefriends.hotdeal.entity.HotDealLike;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import com.bbf.bebefriends.hotdeal.repository.HotDealLikeRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealPostRepository;
import com.bbf.bebefriends.member.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotDealPostLikeService {
    private final HotDealPostRepository hotDealPostRepository;
    private final HotDealLikeRepository hotDealLikeRepository;

    @Transactional
    public String likeHotDealPost(Long hotDealPostId, User user) {
        // 핫딜 게시글 조회
        HotDealPost hotDealPost = hotDealPostRepository.findById(hotDealPostId)
                .orElseThrow();

        // 좋아요가 되어있는지 조회
        Optional<HotDealLike> hotDealLike = hotDealLikeRepository.findByHotDealPostAndUser(hotDealPost, user);

        // 있으면 좋아요 취소
        if (hotDealLike.isPresent()) {
            hotDealLikeRepository.delete(hotDealLike.get());

            // 좋아요 수 감소
            hotDealPost.decreaseLikeCount();

            return "좋아요를 취소했습니다.";

        }

        // 좋아요 저장
        HotDealLike newHotDealLike = HotDealLike.builder()
                .hotDealPost(hotDealPost)
                .user(user)
                .build();
        hotDealLikeRepository.save(newHotDealLike);

        // 좋아요 수 증가
        hotDealPost.increaseLikeCount();

        return "게시물에 좋아요를 눌렀습니다.";
    }
}
