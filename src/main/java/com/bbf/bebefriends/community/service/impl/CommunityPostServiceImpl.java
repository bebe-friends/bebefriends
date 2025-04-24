package com.bbf.bebefriends.community.service.impl;

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

    // 게시물 생성 (게시물, 이미지, 링크)
    @Override
    @Transactional
    public CommunityPostDTO.CreatePostResponse createPost(CommunityPostDTO.CreatePostRequest request, User user) {
        CommunityCategory category = communityCategoryService.getCategoryByName(request.getCategory());

        CommunityPost communityPost = CommunityPost.createPost(user, category, request);
        communityPostRepository.save(communityPost);

        if (request.getImg() != null) {
            for (MultipartFile uploadedFile : request.getImg()){
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
    public CommunityPostDTO.UpdatePostResponse updatePost(CommunityPostDTO.UpdatePostRequest request, User user) {
        CommunityPost post = communityPostRepository.findById(request.getPostId())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));
        if (!(post.getUser().equals(user))) {
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
        toRemove.forEach(img -> post.getImages().remove(img));

        if (request.getNewImages() != null) {
            for (MultipartFile uploadedImage : request.getNewImages()) {
                String imgUrl = fireBaseService.uploadFirebaseBucket(uploadedImage);
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
        if (!(post.getUser().equals(user))) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        post.setDeletedAt();

        return "게시물이 삭제되었습니다.";
    }
}
