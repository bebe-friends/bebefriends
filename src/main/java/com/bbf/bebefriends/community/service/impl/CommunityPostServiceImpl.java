package com.bbf.bebefriends.community.service.impl;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.community.entity.CommunityCategory;
import com.bbf.bebefriends.community.entity.CommunityImage;
import com.bbf.bebefriends.community.entity.CommunityLink;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.community.repository.CommunityImageRepository;
import com.bbf.bebefriends.community.repository.CommunityLinkRepository;
import com.bbf.bebefriends.community.repository.CommunityPostRepository;
import com.bbf.bebefriends.community.service.CommunityCategoryService;
import com.bbf.bebefriends.community.service.CommunityPostService;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.global.utils.file.FireBaseService;
import com.bbf.bebefriends.member.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostServiceImpl implements CommunityPostService {
    private final CommunityPostRepository communityPostRepository;
    private final CommunityCategoryService communityCategoryService;
    private final FireBaseService fireBaseService;
    private final CommunityCommentServiceImpl communityCommentServiceImpl;

    // 게시물 생성 (게시물, 이미지, 링크)
    @Override
    @Transactional
    public CommunityPostDTO.CreatePostResponse createPost(CommunityPostDTO.CreatePostRequest request, List<MultipartFile> images, User user) {
        CommunityCategory category = communityCategoryService.getCategoryByName(request.getCategory());

        CommunityPost communityPost = CommunityPost.createPost(user, category, request);
        communityPostRepository.save(communityPost);

        if (images != null) {
            for (MultipartFile uploadedFile : images){
                String imgUrl = fireBaseService.uploadFirebaseBucket(uploadedFile);
                CommunityImage image = CommunityImage.createImageFile(communityPost, imgUrl);
                communityPost.addImage(image);
            }
        }

        if (request.getLink() != null) {
            for (String uploadedLink : request.getLink()){
                CommunityLink link = CommunityLink.createLink(communityPost, uploadedLink);
                communityPost.addLink(link);
            }
        }

        return new CommunityPostDTO.CreatePostResponse(communityPost);
    }

    // 게시물 수정
    @Override
    @Transactional
    public CommunityPostDTO.UpdatePostResponse updatePost(CommunityPostDTO.UpdatePostRequest request, List<MultipartFile> newImages, User user) {
        CommunityPost post = communityPostRepository.findById(request.getPostId())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));
        // FIXME: transactional 호출 시점에 새로운 영속성 컨텍스트가 시작되기 때문에 동일 영속성 컨텍스트가 아니라 제대로 작동하지 않음
        // 인스턴스 동일성 문제를 해결하기 위해 값 비교를 하는 long 값을 equals로 비교하기
        if (!(post.getUser().getUid().equals(user.getUid()))) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        CommunityCategory category = communityCategoryService.getCategoryByName(request.getCategory());
        post.updatePost(category, request);

        List<CommunityImage> currentImages = post.getImages();
        List<String> keepUrls = request.getExistingImageUrls() != null
                ? request.getExistingImageUrls()
                : List.of();

        // 삭제할 이미지: 기존 중에 요청에 없는 URL
        List<CommunityImage> toRemove = currentImages.stream()
                .filter(img -> !keepUrls.contains(img.getImgUrl()))
                .toList();
//        toRemove.forEach(img -> post.getImages().remove(img));
        toRemove.forEach(img -> {
            fireBaseService.deleteFirebaseFile(img.getImgUrl()); // 실제 Firebase에서 삭제
            post.getImages().remove(img); // 엔티티 관계에서도 제거
        });

//        if (request.getNewImages() != null) {
//            for (MultipartFile uploadedImage : request.getNewImages()) {
//                String imgUrl = fireBaseService.uploadFirebaseBucket(uploadedImage);
//                CommunityImage newImg = CommunityImage.createImageFile(post, imgUrl);
//                post.addImage(newImg);
//            }
//        }

        // 2) 새로 올라온 이미지 처리
        if (newImages != null && !newImages.isEmpty()) {
            for (MultipartFile uploaded : newImages) {
                String imgUrl = fireBaseService.uploadFirebaseBucket(uploaded);
                CommunityImage newImg = CommunityImage.createImageFile(post, imgUrl);
                post.addImage(newImg);
            }
        }

        post.getLinks().clear();
        if (request.getNewLinks() != null) {
            for (String uploadedLink : request.getNewLinks()) {
                post.addLink(CommunityLink.createLink(post, uploadedLink));
            }
        }

        return new CommunityPostDTO.UpdatePostResponse(post);
    }

    // 게시물 삭제
    @Override
    @Transactional
    public String deletePost(CommunityPostDTO.DeletePostRequest request, User user) {
        CommunityPost post = communityPostRepository.findById(request.getPostId())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));
        if (!(post.getUser().getUid().equals(user.getUid()))) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        post.setDeletedAt();

        return "게시물이 삭제되었습니다.";
    }

    // 게시물 상세 페이지
    @Override
    @Transactional
    public CommunityPostDTO.PostDetailsResponse getPostDetail(Long postId) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));
        post.increaseViewCount();

//        List<CommunityCommentDTO.CommentDetails> comments =
//                communityCommentServiceImpl.getCommentsByPost(post);

        return CommunityPostDTO.PostDetailsResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .author(post.getUser().getNickname())
                .createdAt(post.getCreatedDate())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .content(post.getContent())
                .imageUrls(post.getImages().stream().map(CommunityImage::getImgUrl).toList())
                .links(post.getLinks().stream().map(CommunityLink::getLink).toList())
                .build();
    }
}
