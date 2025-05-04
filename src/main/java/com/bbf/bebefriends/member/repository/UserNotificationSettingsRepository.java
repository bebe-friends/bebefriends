package com.bbf.bebefriends.member.repository;

import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserNotificationSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserNotificationSettingsRepository extends JpaRepository<UserNotificationSettings, String> {

    Optional<UserNotificationSettings> findUserNotificationSettingsByUser(User user);
    Optional<UserNotificationSettings> findUserNotificationSettingsByUid(String uid);
}