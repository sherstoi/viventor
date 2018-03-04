package com.viventor.service;

import com.viventor.dto.ApplicationUserDTO;
import com.viventor.dto.ApplicationUserMapper;
import com.viventor.entity.ApplicationUser;
import com.viventor.exceptions.UserAlreadyExistsException;
import com.viventor.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ApplicationUserDTO createApplicationUser(ApplicationUserDTO applicationUserDTO) {
        ApplicationUser applicationUser = userRepository.findUserByUserName(applicationUserDTO.getUserName());
        if (applicationUser != null) {
            throw new UserAlreadyExistsException("User " + applicationUserDTO.getUserName() + " already exists!");
        }
        applicationUser = new ApplicationUser();
        applicationUser.setUserName(applicationUserDTO.getUserName());
        applicationUser.setPassword(passwordEncoder.encode(applicationUserDTO.getPassword()));
        applicationUser = userRepository.save(applicationUser);

        return ApplicationUserMapper.INSTANCE.toApplicationUserResponse(applicationUser);
    }

    public ApplicationUserDTO findAppUserByLogin(String userLogin) {
        ApplicationUser applicationUser = userRepository.findUserByUserName(userLogin);
        return ApplicationUserMapper.INSTANCE.toApplicationUserResponse(applicationUser);
    }

}