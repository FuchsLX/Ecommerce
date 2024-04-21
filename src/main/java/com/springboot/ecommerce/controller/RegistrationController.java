package com.springboot.ecommerce.controller;


import com.springboot.ecommerce.controller.dto.RegistrationDto;
import com.springboot.ecommerce.security.registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping
    public String getSignUpPage(Model model){
        RegistrationDto request = new RegistrationDto();
        model.addAttribute("request", request);
//        List<UserRole> roleList = Arrays.asList(USER,VENDOR, ADMIN);
//        model.addAttribute("roleList", roleList);
        return  "registration";
    }

    @PostMapping("register")
    public String register(@ModelAttribute("request") RegistrationDto request){
        registrationService.register(request);
        return "redirect:/home";
    }

    @GetMapping("confirm")
    public String confirm(@RequestParam("token") String token){
        registrationService.confirmedToken(token);
        return "redirect:/login";
    }


}
