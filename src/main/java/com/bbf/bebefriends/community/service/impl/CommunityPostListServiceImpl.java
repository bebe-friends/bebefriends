package com.bbf.bebefriends.community.service.impl;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.community.entity.CommunityCategory;
import com.bbf.bebefriends.community.entity.CommunityImage;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.repository.CommunityCommentRepository;
import com.bbf.bebefriends.community.repository.CommunityPostForAnonymousRepository;
import com.bbf.bebefriends.community.repository.CommunityPostLikeRepository;
import com.bbf.bebefriends.community.repository.CommunityPostRepository;
import com.bbf.bebefriends.community.service.CommunityCategoryService;
import com.bbf.bebefriends.community.service.CommunityPostListService;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostListServiceImpl implements CommunityPostListService {
    private final CommunityPostRepository communityPostRepository;
    private final CommunityPostForAnonymousRepository communityPostForAnonymousRepository;
    private final CommunityCategoryService communityCategoryService;

    // DTO 변환 (첫 번째 이미지 URL)
    private CommunityPostDTO.PostListResponse toDto(CommunityPost post) {
        String firstImg = post.getImages().stream()
                .findFirst()
                .map(CommunityImage::getImgUrl)
                .orElse(null);

//        int commentCount = communityCommentRepository.countByPostAndDeletedAtIsNull(post);
        return CommunityPostDTO.PostListResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .author(post.getUser().getNickname())
                .content(post.getContent())
                .firstImageUrl(firstImg)
                .createdAt(post.getCreatedDate())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .build();
    }

    // 모든 게시물
    @Override
    public List<CommunityPostDTO.PostListResponse> getAllPosts(User user) {
        if (user == null) {
            return communityPostForAnonymousRepository.findAllActivePostsForAnonymous()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }
        List<CommunityPost> communityPosts = communityPostRepository.findAllActivePosts(user);
        return communityPosts
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 카테고리 별 조회 -> id로 조회하는게 더 낫나?
    @Override
    public List<CommunityPostDTO.PostListResponse> getPostsByCategory(String request, User user) {
        CommunityCategory category = communityCategoryService.getCategoryByName(request);
        if (user.getRole() == UserRole.GUEST) {
            return communityPostForAnonymousRepository.findByCategoryActiveForAnonymous(category)
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }
        return communityPostRepository.findByCategoryActive(category, user)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 제목 혹은 글쓴이 검색
    @Override
    public List<CommunityPostDTO.PostListResponse> getPostsBySearch(String query, User user) {
        if (user.getRole() == UserRole.GUEST) {
            return communityPostForAnonymousRepository.searchPostsByKeywordForAnonymous(query)
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }
        return communityPostRepository.searchPostsByKeyword(query, user)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 제목으로 조회
//    @Override
//    public List<CommunityPostDTO.PostListResponse> getPostsByTitle(String keyword) {
//        return communityPostRepository.findByTitleLikeActive(keyword)
//                .stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }

    // 작성자로 조회
//    @Override
//    public List<CommunityPostDTO.PostListResponse> getPostsByAuthor(String keyword) {
//        return communityPostRepository.findByAuthorLikeActive(keyword)
//                .stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }

    // 내가 쓴 게시물
    @Override
    public List<CommunityPostDTO.PostListResponse> getMyPosts(User user) {
        return communityPostRepository.findByUserAndDeletedAtIsNull(user)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 댓글 단 게시물
    @Override
    public List<CommunityPostDTO.PostListResponse> getCommentedPosts(User user) {
        return communityPostRepository.findCommentedByUserActive(user)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 좋아요한 게시물
    @Override
    public List<CommunityPostDTO.PostListResponse> getLikedPosts(User user) {
        return communityPostRepository.findLikedByUserActive(user)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
