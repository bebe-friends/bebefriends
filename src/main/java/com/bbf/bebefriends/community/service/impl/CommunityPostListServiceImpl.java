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

    public CommunityPostDTO.PostListWithCursorResponse toDtoWithCursor(List<CommunityPostDTO.PostListResponse> posts) {
        Long newCursor = posts.isEmpty()
                ? null
                : posts.get(posts.size() - 1).getPostId();
        return new CommunityPostDTO.PostListWithCursorResponse(posts, newCursor);
    }

    public CommunityPostDTO.PostListWithCursorResponse getAllPosts(User user, Long cursorId, int pageSize) {
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
                .map(this::toDto)
                .toList();

        return toDtoWithCursor(posts);
    }

    // 카테고리 별 조회 -> id로 조회하는게 더 낫나?
    @Override
    public CommunityPostDTO.PostListWithCursorResponse getPostsByCategory(String request, User user, Long cursorId, int pageSize) {
        CommunityCategory category = communityCategoryService.getCategoryByName(request);
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
                .map(this::toDto)
                .toList();

        return toDtoWithCursor(posts);
    }

    // 제목 혹은 글쓴이 검색
    @Override
    public CommunityPostDTO.PostListWithCursorResponse getPostsBySearch(String query, User user, Long cursorId, int pageSize) {
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
                .map(this::toDto)
                .toList();

        return toDtoWithCursor(posts);
    }

    // 내가 쓴 게시물
    @Override
    public CommunityPostDTO.PostListWithCursorResponse getMyPosts(User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize);

        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.findMyPostsWithCursor(user, cursorId, limit)
                .stream()
                .map(this::toDto)
                .toList();

        return toDtoWithCursor(posts);
    }

    // 댓글 단 게시물
    @Override
    public CommunityPostDTO.PostListWithCursorResponse getCommentedPosts(User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize);

        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.findCommentedByUserActive(user, cursorId, limit)
                .stream()
                .map(this::toDto)
                .toList();

        return toDtoWithCursor(posts);
    }

    // 좋아요한 게시물
    @Override
    public CommunityPostDTO.PostListWithCursorResponse getLikedPosts(User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize);

        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.findLikedByUserActive(user, cursorId, limit)
                .stream()
                .map(this::toDto)
                .toList();

        return toDtoWithCursor(posts);
    }
}
