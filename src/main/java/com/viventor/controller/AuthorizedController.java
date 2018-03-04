package com.viventor.controller;

import com.viventor.dto.ApplicationUserDTO;
import com.viventor.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthorizedController {
    @Resource
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/createAppUser")
    public ApplicationUserDTO createAppUser(@RequestBody @Valid @NotNull ApplicationUserDTO applicationUserDTO) {
        return userService.createApplicationUser(applicationUserDTO);
    }

}
