package com.bbf.bebefriends.community.service.impl;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.community.entity.CommunityCategory;
import com.bbf.bebefriends.community.entity.CommunityImage;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.community.repository.CommunityCategoryRepository;
import com.bbf.bebefriends.community.repository.CommunityPostBlockRepository;
import com.bbf.bebefriends.community.repository.CommunityPostForAnonymousRepository;
import com.bbf.bebefriends.community.repository.CommunityPostRepository;
import com.bbf.bebefriends.community.service.CommunityCategoryService;
import com.bbf.bebefriends.community.service.CommunityPostListService;
import com.bbf.bebefriends.global.entity.BasePageResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostListServiceImpl implements CommunityPostListService {
    private final CommunityPostRepository communityPostRepository;
    private final CommunityPostForAnonymousRepository communityPostForAnonymousRepository;
    private final CommunityCategoryService communityCategoryService;
    private final CommunityPostBlockRepository communityPostBlockRepository;
    private final CommunityCategoryRepository communityCategoryRepository;

    // DTO 변환 (첫 번째 이미지 URL)
    private CommunityPostDTO.PostListResponse toDto(CommunityPost post, User user) {
        String firstImg = post.getImages().stream()
                .findFirst()
                .map(CommunityImage::getImgUrl)
                .orElse(null);
        Boolean isBlocked = communityPostBlockRepository.existsByPostAndUser(post, user);

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
                .isBlocked(isBlocked)
                .build();
    }

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
                .isBlocked(null)
                .build();
    }

    private BasePageResponse<CommunityPostDTO.PostListResponse> toDtoWithCursor(List<CommunityPostDTO.PostListResponse> posts) {
        Long newCursor = posts.isEmpty()
                ? null
                : posts.get(posts.size() - 1).getPostId();

        return BasePageResponse.of(posts, newCursor);
    }

    public BasePageResponse<CommunityPostDTO.PostListResponse> getAllPosts(User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize);

        if (user.getRole() == UserRole.GUEST) {
            List<CommunityPostDTO.PostListResponse> posts = communityPostForAnonymousRepository.findAllActivePostsForAnonymous(cursorId, limit)
                    .stream()
                    .map(this::toDto)
                    .toList();

            return toDtoWithCursor(posts);
        }
        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.findAllActivePostsWithCursor(user, cursorId, limit)
                .stream()
                .map(post -> toDto(post, user))
                .toList();

        return toDtoWithCursor(posts);
    }

    @Override
    public BasePageResponse<CommunityPostDTO.PostListResponse> getPostsByCategory(Long request, User user, Long cursorId, int pageSize) {
        CommunityCategory category = communityCategoryRepository.findById(request)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_CATEGORY_NOT_FOUND));
        Pageable limit = PageRequest.of(0, pageSize);

        if (user.getRole() == UserRole.GUEST) {
            List<CommunityPostDTO.PostListResponse> posts = communityPostForAnonymousRepository.findByCategoryActiveForAnonymous(category, cursorId, limit)
                    .stream()
                    .map(this::toDto)
                    .toList();

            return toDtoWithCursor(posts);
        }
        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.findByCategoryActive(category, user, cursorId, limit)
                .stream()
                .map(post -> toDto(post, user))
                .toList();

        return toDtoWithCursor(posts);
    }

    // 제목 혹은 글쓴이 검색
    @Override
    public BasePageResponse<CommunityPostDTO.PostListResponse> getPostsBySearch(String query, User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize);

        if (user.getRole() == UserRole.GUEST) {
            List<CommunityPostDTO.PostListResponse> posts = communityPostForAnonymousRepository.searchPostsByKeywordForAnonymous(query, cursorId, limit)
                    .stream()
                    .map(this::toDto)
                    .toList();

            return toDtoWithCursor(posts);
        }
        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.searchPostsByKeyword(query, user, cursorId, limit)
                .stream()
                .map(post -> toDto(post, user))
                .toList();

        return toDtoWithCursor(posts);
    }

    // 내가 쓴 게시물
    @Override
    public BasePageResponse<CommunityPostDTO.PostListResponse> getMyPosts(User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize);

        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.findMyPostsWithCursor(user, cursorId, limit)
                .stream()
                .map(post -> toDto(post, user))
                .toList();

        return toDtoWithCursor(posts);
    }

    // 댓글 단 게시물
    @Override
    public BasePageResponse<CommunityPostDTO.PostListResponse> getCommentedPosts(User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize);

        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.findCommentedByUserActive(user, cursorId, limit)
                .stream()
                .map(post -> toDto(post, user))
                .toList();

        return toDtoWithCursor(posts);
    }

    // 좋아요한 게시물
    @Override
    public BasePageResponse<CommunityPostDTO.PostListResponse> getLikedPosts(User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize);

        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.findLikedByUserActive(user, cursorId, limit)
                .stream()
                .map(post -> toDto(post, user))
                .toList();

        return toDtoWithCursor(posts);
    }
}
