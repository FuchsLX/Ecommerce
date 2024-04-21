package com.springboot.ecommerce.controller;


import com.springboot.ecommerce.entities.user.User;
import com.springboot.ecommerce.entities.user.UserMeta;
import com.springboot.ecommerce.entities.user.UserMetaGender;
import com.springboot.ecommerce.services.UserMetaService;
import com.springboot.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.springboot.ecommerce.entities.user.UserMetaGender.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class UserMetaController {
    private final UserService userService;

    private final UserMetaService userMetaService;

    @GetMapping("edit")
    public String addNewAccountInformation(Model model,
                                           @AuthenticationPrincipal UserDetails user){
        User currentUser = userService.findByEmail(user.getUsername());
        UserMeta userMeta = userMetaService.getUserMetaByCurrentUser(currentUser.getId());
        List<UserMetaGender> genderList = new ArrayList<>(Arrays.asList(MALE,FEMALE));
        model.addAttribute("userMeta", Objects.requireNonNullElseGet(userMeta, UserMeta::new));
        model.addAttribute("genderList", genderList);
        return "account-information";
    }

    @PostMapping("save-user-meta")
    public String saveUserMeta(@ModelAttribute("userMeta") UserMeta userMeta,
                               @AuthenticationPrincipal UserDetails user){
        User currentUser = userService.findByEmail(user.getUsername());
        if (userMeta.getUser() == null){
            userMeta.setUser(currentUser);
        }
        userMetaService.saveUserMeta(userMeta);
        return "redirect:/account/edit";
    }
}
