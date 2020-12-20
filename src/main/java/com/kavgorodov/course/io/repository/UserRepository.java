package com.kavgorodov.course.io.repository;

import com.kavgorodov.course.io.entity.UserEntity;
import com.kavgorodov.course.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

/**
 * @author Vladimir Vishnevskiy
 */


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);

}
