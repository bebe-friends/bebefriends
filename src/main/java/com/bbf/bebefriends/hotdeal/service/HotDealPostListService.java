package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.community.entity.CommunityImage;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.global.entity.BasePageResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.repository.HotDealCategoryRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealLikeRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealPostRepository;
import com.bbf.bebefriends.member.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotDealPostListService {
    private final HotDealPostRepository postRepository;
    private final HotDealLikeRepository likeRepository;

    private BasePageResponse<HotDealPostDto.HotDealListResponse> toDtoWithCursor(List<HotDealPostDto.HotDealListResponse> posts) {
        Long newCursor = posts.isEmpty()
                ? null
                : posts.get(posts.size() - 1).getPostId();

        return BasePageResponse.of(posts, newCursor);
    }

    // 전체 핫딜 조회
    public BasePageResponse<HotDealPostDto.HotDealListResponse> getAllHotDeals(User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize);

        List<HotDealPost> hotDeals = postRepository.findAllHotDealsWithCursor(cursorId, limit);

        List<HotDealPostDto.HotDealListResponse> dtos = hotDeals.stream()
                .map(post -> Optional.ofNullable(post.getHotDeal())
                        .map(hot -> HotDealPostDto.HotDealListResponse.of(
                                post, hot.getHotDealCategory()))
                        .orElseGet(() -> HotDealPostDto.HotDealListResponse.of(post, post.getHotDealCategory()))
                )
                .toList();

        return toDtoWithCursor(dtos);
    }

    // 추천 핫딜 조회
    public BasePageResponse<HotDealPostDto.HotDealListResponse> getHotDealsByRecommended(User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize);

        List<HotDealPostDto.HotDealListResponse> posts = postRepository.findCustomHotDealsWithCursor(user, cursorId, limit)
                .stream()
                .map(post -> HotDealPostDto.HotDealListResponse.of(post, post.getHotDealCategory()))
                .toList();

        return toDtoWithCursor(posts);
    }
}
