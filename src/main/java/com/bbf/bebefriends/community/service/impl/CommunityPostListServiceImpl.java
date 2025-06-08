package com.bbf.bebefriends.community.service.impl;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.community.entity.CommunityCategory;
import com.bbf.bebefriends.community.entity.CommunityImage;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.repository.CommunityPostForAnonymousRepository;
import com.bbf.bebefriends.community.repository.CommunityPostRepository;
import com.bbf.bebefriends.community.service.CommunityCategoryService;
import com.bbf.bebefriends.community.service.CommunityPostListService;
import com.bbf.bebefriends.global.entity.BasePageResponse;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    private BasePageResponse<CommunityPostDTO.PostListResponse> toDtoWithCursor(List<CommunityPostDTO.PostListResponse> posts, int pageSize) {
        Long newCursor = posts.isEmpty()
                ? null
                : posts.get(posts.size() - 1).getPostId();

        boolean hasNext = posts.size() > pageSize;

        List<CommunityPostDTO.PostListResponse> content = hasNext
                ? posts.subList(0, pageSize)
                : posts;

        return BasePageResponse.of(content, newCursor, hasNext);
    }

    public BasePageResponse<CommunityPostDTO.PostListResponse> getAllPosts(User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize + 1);

        if (user.getRole() == UserRole.GUEST) {
            List<CommunityPostDTO.PostListResponse> posts = communityPostForAnonymousRepository.findAllActivePostsForAnonymous(cursorId, limit)
                    .stream()
                    .map(this::toDto)
                    .toList();

            return toDtoWithCursor(posts, pageSize);
        }
        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.findAllActivePostsWithCursor(user, cursorId, limit)
                .stream()
                .map(this::toDto)
                .toList();

        return toDtoWithCursor(posts, pageSize);
    }

    // 카테고리 별 조회 -> id로 조회하는게 더 낫나?
    @Override
    public BasePageResponse<CommunityPostDTO.PostListResponse> getPostsByCategory(String request, User user, Long cursorId, int pageSize) {
        CommunityCategory category = communityCategoryService.getCategoryByName(request);
        Pageable limit = PageRequest.of(0, pageSize + 1);

        if (user.getRole() == UserRole.GUEST) {
            List<CommunityPostDTO.PostListResponse> posts = communityPostForAnonymousRepository.findByCategoryActiveForAnonymous(category, cursorId, limit)
                    .stream()
                    .map(this::toDto)
                    .toList();

            return toDtoWithCursor(posts, pageSize);
        }
        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.findByCategoryActive(category, user, cursorId, limit)
                .stream()
                .map(this::toDto)
                .toList();

        return toDtoWithCursor(posts, pageSize);
    }

    // 제목 혹은 글쓴이 검색
    @Override
    public BasePageResponse<CommunityPostDTO.PostListResponse> getPostsBySearch(String query, User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize + 1);

        if (user.getRole() == UserRole.GUEST) {
            List<CommunityPostDTO.PostListResponse> posts = communityPostForAnonymousRepository.searchPostsByKeywordForAnonymous(query, cursorId, limit)
                    .stream()
                    .map(this::toDto)
                    .toList();

            return toDtoWithCursor(posts, pageSize);
        }
        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.searchPostsByKeyword(query, user, cursorId, limit)
                .stream()
                .map(this::toDto)
                .toList();

        return toDtoWithCursor(posts, pageSize);
    }

    // 내가 쓴 게시물
    @Override
    public BasePageResponse<CommunityPostDTO.PostListResponse> getMyPosts(User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize + 1);

        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.findMyPostsWithCursor(user, cursorId, limit)
                .stream()
                .map(this::toDto)
                .toList();

        return toDtoWithCursor(posts, pageSize);
    }

    // 댓글 단 게시물
    @Override
    public BasePageResponse<CommunityPostDTO.PostListResponse> getCommentedPosts(User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize + 1);

        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.findCommentedByUserActive(user, cursorId, limit)
                .stream()
                .map(this::toDto)
                .toList();

        return toDtoWithCursor(posts, pageSize);
    }

    // 좋아요한 게시물
    @Override
    public BasePageResponse<CommunityPostDTO.PostListResponse> getLikedPosts(User user, Long cursorId, int pageSize) {
        Pageable limit = PageRequest.of(0, pageSize + 1);

        List<CommunityPostDTO.PostListResponse> posts = communityPostRepository.findLikedByUserActive(user, cursorId, limit)
                .stream()
                .map(this::toDto)
                .toList();

        return toDtoWithCursor(posts, pageSize);
    }
}
