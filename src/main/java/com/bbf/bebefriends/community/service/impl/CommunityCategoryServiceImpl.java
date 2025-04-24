package com.bbf.bebefriends.community.service.impl;

import com.bbf.bebefriends.community.dto.CommunityCategoryDTO;
import com.bbf.bebefriends.community.entity.CommunityCategory;
import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.community.repository.CommunityCategoryRepository;
import com.bbf.bebefriends.community.service.CommunityCategoryService;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bbf.bebefriends.member.entity.UserRole.ADMIN;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCategoryServiceImpl implements CommunityCategoryService {
    private final CommunityCategoryRepository communityCategoryRepository;

    // 전체 카테고리 조회
    @Override
    public List<CommunityCategory> getAllCategory() {
        return communityCategoryRepository.findAll();
    }

    // 새 카테고리 만들기(운영자만 가능)
    @Override
    @Transactional
    public CommunityCategoryDTO.CreateCategoryResponse saveCategory(CommunityCategoryDTO.CreateCategoryRequest request, User user) {
        if (user.getRole() != ADMIN) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        CommunityCategory communityCategory = CommunityCategory.createCategory(request.getName());

        return new CommunityCategoryDTO.CreateCategoryResponse(communityCategoryRepository.save(communityCategory));
    }

    // 카테고리 이름 수정(운영진만 가능)
    @Override
    @Transactional
    public CommunityCategoryDTO.UpdateCategoryResponse updateCategory(CommunityCategoryDTO.UpdateCategoryRequest request, User user) {
        if (user.getRole() != ADMIN) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        CommunityCategory communityCategory = communityCategoryRepository.findById(request.getId())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED));
        communityCategory.updateCategory(request.getNewName());

        return new CommunityCategoryDTO.UpdateCategoryResponse(communityCategory);
    }

    // 카테고리 이름으로 조회
    @Override
    public CommunityCategory getCategoryByName(String name) {

        return communityCategoryRepository.findByName(name)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_CATEGORY_NOT_FOUND));
    }
}
