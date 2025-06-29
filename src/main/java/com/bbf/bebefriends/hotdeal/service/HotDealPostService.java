package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.global.utils.file.FireBaseService;
import com.bbf.bebefriends.hotdeal.dto.HotDealCommentDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealLikeDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;
import com.bbf.bebefriends.hotdeal.entity.*;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.repository.*;
import com.bbf.bebefriends.member.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotDealPostService {

    private final HotDealRepository hotDealRepository;
    private final HotDealPostRepository hotDealPostRepository;
    private final HotDealCommentRepository hotDealCommentRepository;
    private final HotDealPostViewRepository hotDealPostViewRepository;
    private final FireBaseService fireBaseService;
    private final HotDealCategoryRepository hotDealCategoryRepository;

    // 핫딜 게시글 생성
    @Transactional
    public HotDealPostDto.CreateHotDealPostResponse createHotDealPost(HotDealPostDto.CreateHotDealPostRequest request,
                                                                      List<MultipartFile> images,
                                                                      User user) {
        // 핫딜 초기화
        HotDeal hotDeal = null;

        // 핫딜 식별자가 있는 경우 핫딜 조회
        if (request.getHotDealId() != null) {
            hotDeal = hotDealRepository.findById(request.getHotDealId())
                    .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_NOT_FOUND));
        }

        List<String> imgPaths = new ArrayList<>();
        for (MultipartFile uploadedFile : images){
            String imgUrl = fireBaseService.uploadFirebaseBucket(uploadedFile);
            imgPaths.add(imgUrl);
        }

        String links = String.join(",", request.getLinks());
        String img = String.join(",", imgPaths);
        String ages = request.getAge().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));
        HotDealCategory category = hotDealCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_NOT_FOUND));
        HotDealPost post = HotDealPost.createHotDealPost(user, hotDeal, request, links, img, ages, category);

        hotDealPostRepository.save(post);

        return new HotDealPostDto.CreateHotDealPostResponse(post);
    }

    // 핫딜 게시글 수정
    @Transactional
    public HotDealPostDto.UpdateHotDealPostResponse updateHotDealPost(HotDealPostDto.UpdateHotDealPostRequest request,
                                                                      List<MultipartFile> images) {
        // 수정할 핫딜 게시글 조회
        HotDealPost hotDealPost = hotDealPostRepository.findById(request.getHotDealPostId())
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_POST_NOT_FOUND));

        // 기존과 다른 핫딜인 경우
        if (!hotDealPost.getHotDeal().getId().equals(request.getHotDealId())) {
            // 수정된 핫딜로 세팅
            HotDeal hotDeal = hotDealRepository.findById(request.getHotDealId())
                    .orElseThrow();
            hotDealPost.updateHotDeal(hotDeal);
        }

        List<String> newImgPaths = new ArrayList<>();
        for (MultipartFile uploadedFile : images){
            String imgUrl = fireBaseService.uploadFirebaseBucket(uploadedFile);
            newImgPaths.add(imgUrl);
        }

        String links = String.join(",", request.getLinks());
        String img = String.join(",", request.getImgPaths()) + String.join(",", newImgPaths);
        String ages = request.getAge().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        HotDealCategory category = hotDealCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_NOT_FOUND));

        hotDealPost.updatePost(request, links, img, ages, category);

        return new HotDealPostDto.UpdateHotDealPostResponse(hotDealPost);
    }

    // 핫딜 게시물 삭제
    @Transactional
    public String deleteHotDealPost(HotDealPostDto.DeleteHotDealPostRequest request) {
        // 삭제할 핫딜 게시글 조회
        HotDealPost hotDealPost = hotDealPostRepository.findById(request.getHotDealPostId())
                .orElseThrow();

        // 핫딜 게시글 삭제 처리
        hotDealPost.setDeletedAt();

        return "핫딜 게시글이 삭제되었습니다.";
    }

    // 핫딜 게시물 상세조회
    public HotDealPostDto.HotDealPostDetailsResponse searchHotDealPostDetail(Long hotDealPostId, User user) {
        HotDealPost hotDealPost = hotDealPostRepository.findById(hotDealPostId)
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_POST_NOT_FOUND));
        hotDealPost.increaseViewCount();

        Long hotDealId = null;
        if (hotDealPost.getHotDeal() != null) {
            hotDealId = hotDealPost.getHotDeal().getId();
        }

        List<String> links = Arrays.stream(hotDealPost.getLink().split(",")).toList();
        List<String> imgPaths = Arrays.stream(hotDealPost.getImgPath().split(",")).toList();
        List<String> ages = Arrays.stream(hotDealPost.getAge().split(",")).toList();

        return HotDealPostDto.HotDealPostDetailsResponse.builder()
                .postId(hotDealPost.getId())
                .userId(hotDealPost.getUser().getUid())
                .title(hotDealPost.getTitle())
                .content(hotDealPost.getContent())
                .hotDealId(hotDealId)
                .link(links)
                .imgPath(imgPaths)
                .status(hotDealPost.getStatus())
                .age(ages)
                .viewCount(hotDealPost.getViewCount())
                .likeCount(hotDealPost.getLikeCount())
                .build();

//        Optional<HotDealPostView> hotDealPostView = hotDealPostViewRepository.findByUserAndHotDealPost(user, hotDealPost);
//
//        // 조회한 게시글이 아닌 경우 (아마 중복 확인으로 인한 조회수 증가 방지 목적인 듯)
//        if (hotDealPostView.isEmpty()) {
//            // 핫딜 게시글 조회 저장
//            HotDealPostView newhotDealPostView = HotDealPostView.builder()
//                    .user(user)
//                    .hotDealPost(hotDealPost)
//                    .build();
//            hotDealPostViewRepository.save(newhotDealPostView);
//
//            // 조회수 증가
//            hotDealPost.increaseViewCount();
//        }
    }
}
