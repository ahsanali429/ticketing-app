package com.task.callsign.services;

import com.task.callsign.models.entity.UserInfo;
import com.task.callsign.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CallSignUserDetailsService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public CallSignUserDetailsService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserInfo userInfo = userInfoRepository.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException(username + " is not found"));
        return new User(userInfo.getUserName(), userInfo.getPassword(), Collections.emptyList());
//        return new User("admin", "admin", Collections.emptyList());
    }
}
