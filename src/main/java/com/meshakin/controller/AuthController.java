package com.meshakin.controller;

import com.meshakin.dto.UserAccessDto;
import com.meshakin.service.UserAccessService;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
   private final  UserAccessService userAccessService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserAccessDto userDto){
        try {
        String username = userAccessService.createUser(userDto).getUsername();
        return "registration: ".concat(username);}
        catch (DataIntegrityViolationException e) {
            return "Username is already in use";
        }
    }
}
