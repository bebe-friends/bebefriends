package com.bbf.bebefriends.hotdeal.service.impl;

import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;
import com.bbf.bebefriends.hotdeal.entity.HotDeal;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import com.bbf.bebefriends.hotdeal.repository.HotDealCategoryRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealPostRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealRepository;
import com.bbf.bebefriends.hotdeal.service.HotDealPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class HotDealPostServiceImpl implements HotDealPostService {

    private final HotDealRepository hotDealRepository;
    private final HotDealPostRepository hotDealPostRepository;
    private final HotDealCategoryRepository hotDealCategoryRepository;


    @Override
    public Page<HotDealPostDto> searchAllHotDealPost(Pageable pageable) {
        return hotDealPostRepository.findAll(pageable).map(HotDealPostDto::fromEntity);
    }

    @Override
    public Page<HotDealPostDto> searchCategoryHotDealPost(Long hotDealCategoryId,Pageable pageable) {
        // 핫딜 카테고리 조회
        HotDealCategory hotDealCategory = hotDealCategoryRepository.findById(hotDealCategoryId)
                .orElseThrow();

        // 조회한 카테고리를 통해 핫딜 게시글 조회
        return hotDealPostRepository.findByHotDeal_HotDealCategory(hotDealCategory,pageable).map(HotDealPostDto::fromEntity);
    }

    public HotDealPostDto createHotDealPost(HotDealPostDto hotDealPostDto) {
        // 핫딜 초기화
        HotDeal hotDeal = null;

        // 핫딜 식별자가 있는 경우 핫딜 조회
        if (hotDealPostDto.getHotDealId() != null) {
            hotDeal = hotDealRepository.findById(hotDealPostDto.getHotDealId())
                    .orElseThrow();
        }

        // 핫딜 게시글 생성
        HotDealPost hotDealPost = HotDealPost.builder()
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

}
