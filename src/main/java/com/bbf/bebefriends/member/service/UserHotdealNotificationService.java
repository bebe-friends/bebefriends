package com.bbf.bebefriends.member.service;


import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.repository.HotDealCategoryRepository;
import com.bbf.bebefriends.member.dto.UserDTO;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserHotdealNotification;
import com.bbf.bebefriends.member.exception.UserControllerAdvice;
import com.bbf.bebefriends.member.repository.UserHotdealNotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserHotdealNotificationService {

    private final UserHotdealNotificationRepository hotdealNotificationRepository;
    private final HotDealCategoryRepository hotDealCategoryRepository;

    @Transactional
    public void updateHotdealNotificationSettings(UserDTO.UserHotdealNotificationRequest request, User user) {

        UserHotdealNotification notification = hotdealNotificationRepository.findUserHotdealNotificationByUser(user)
                .orElse(UserHotdealNotification.of(user));

        notification.setAge_0(request.age_0());
        notification.setAge_1(request.age_1());
        notification.setAge_2(request.age_2());
        notification.setAge_3(request.age_3());
        notification.setAge_4(request.age_4());
        notification.setAge_5(request.age_5());
        notification.setAge_6(request.age_6());
        notification.setAge_7(request.age_7());

        notification.getPreferredCategories().clear();

        List<HotDealCategory> categories = request.categorys().stream()
                .map(categoryId -> hotDealCategoryRepository.findById(categoryId)
                        .orElseThrow(() -> new UserControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_NOT_FOUND)))
                .collect(Collectors.toList());
        notification.getPreferredCategories().addAll(categories);

        hotdealNotificationRepository.save(notification);
    }

    public UserDTO.UserHotdealNotificationResponse getHotdealNotificationSettings(User user) {
        UserHotdealNotification notification = hotdealNotificationRepository.findByUserWithCategories(user)
                .orElse(UserHotdealNotification.of(user));

        List<String> categoryNames = notification.getPreferredCategories() != null ?
                notification.getPreferredCategories().stream()
                        .map(HotDealCategory::getName)
                        .collect(Collectors.toList()) :
                List.of();

        return new UserDTO.UserHotdealNotificationResponse(
                notification.isAge_0(),
                notification.isAge_1(),
                notification.isAge_2(),
                notification.isAge_3(),
                notification.isAge_4(),
                notification.isAge_5(),
                notification.isAge_6(),
                notification.isAge_7(),
                categoryNames
        );
    }


}
