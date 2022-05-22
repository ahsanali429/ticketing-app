package com.task.callsign.repository;

import com.task.callsign.models.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {
    Optional<UserInfo> findByUserName(String userName);
}
