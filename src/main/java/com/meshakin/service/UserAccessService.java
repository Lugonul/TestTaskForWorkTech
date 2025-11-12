package com.meshakin.service;

import com.meshakin.dto.UserAccessDto;
import com.meshakin.entity.UserAccess;
import com.meshakin.repository.UserAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAccessService implements UserDetailsService {

    private final UserAccessRepository userAccessRepository;
    private final PasswordEncoder encoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        return userAccessRepository.findByUserLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found: " + username
                ));
    }

    @Transactional
    public UserDetails createUser(UserAccessDto userDto){

        UserAccess user = new UserAccess();
        user.setUserLogin(userDto.username());
        user.setUserPassword(encoder.encode(userDto.password()));
        user.setUserRole("user");
       return userAccessRepository.save(user);
    }
}
