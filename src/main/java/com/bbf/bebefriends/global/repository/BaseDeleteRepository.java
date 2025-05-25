package com.bbf.bebefriends.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.time.LocalDateTime;

@NoRepositoryBean
public interface BaseDeleteRepository<T, ID> extends JpaRepository<T, ID> {

    void deleteByDeletedAtBefore(LocalDateTime cutoffDate);

}
