package com.kavgorodov.course.io.repository;

import com.kavgorodov.course.io.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vladimir Vishnevskiy
 */


@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);

}
