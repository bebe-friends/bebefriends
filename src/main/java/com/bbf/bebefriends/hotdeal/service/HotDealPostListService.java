package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.hotdeal.repository.HotDealPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotDealPostListService {
    private final HotDealPostRepository postRepository;

    //    public Page<HotDealPostDto> searchAllHotDealPost(Pageable pageable) {
//        return hotDealPostRepository.findAllByDeletedAtIsNull(pageable).map(HotDealPostDto::fromEntity);
//    }
//
//    public Page<HotDealPostDto> searchCategoryHotDealPost(Long hotDealCategoryId,Pageable pageable) {
//        // 핫딜 카테고리 조회
//        HotDealCategory hotDealCategory = hotDealCategoryRepository.findById(hotDealCategoryId)
//                .orElseThrow();
//
//        // 조회한 카테고리를 통해 핫딜 게시글 조회
//        return hotDealPostRepository.findByHotDeal_HotDealCategoryAndDeletedAtIsNull(hotDealCategory,pageable).map(HotDealPostDto::fromEntity);
//    }
}
