package com.bbf.bebefriends.member.repository;

import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserHotdealNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserHotdealNotificationRepository extends JpaRepository<UserHotdealNotification, Long> {

    Optional<UserHotdealNotification> findUserHotdealNotificationByUser(User user);
}
