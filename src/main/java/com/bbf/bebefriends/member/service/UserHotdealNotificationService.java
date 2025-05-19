package com.bbf.bebefriends.member.service;


import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.UserDTO;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserHotdealNotification;
import com.bbf.bebefriends.member.exception.UserControllerAdvice;
import com.bbf.bebefriends.member.repository.UserHotdealNotificationRepository;
import com.bbf.bebefriends.member.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHotdealNotificationService {

    private final UserHotdealNotificationRepository hotdealNotificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void updateHotdealNotificationSettings(UserDTO.UserHotdealNotificationRequest request, User user) {

        UserHotdealNotification notification = hotdealNotificationRepository.findUserHotdealNotificationByUser(user)
                .orElse(UserHotdealNotification.of(user));

        // 나이 그룹 알림 설정 업데이트
        notification.setAge_0(request.age_0());
        notification.setAge_1(request.age_1());
        notification.setAge_2(request.age_2());
        notification.setAge_3(request.age_3());
        notification.setAge_4(request.age_4());
        notification.setAge_5(request.age_5());
        notification.setAge_6(request.age_6());

        hotdealNotificationRepository.save(notification);
    }

    public UserDTO.UserHotdealNotificationResponse getHotdealNotificationSettings(User user) {
        UserHotdealNotification notification = hotdealNotificationRepository.findUserHotdealNotificationByUser(user)
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode._INTERNAL_SERVER_ERROR));

        return new UserDTO.UserHotdealNotificationResponse(
                notification.isAge_0(),
                notification.isAge_1(),
                notification.isAge_2(),
                notification.isAge_3(),
                notification.isAge_4(),
                notification.isAge_5(),
                notification.isAge_6()
        );
    }


}
