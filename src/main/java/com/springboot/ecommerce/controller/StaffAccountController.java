package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.controller.dto.StaffDTO;
import com.springboot.ecommerce.services.PermissionService;
import com.springboot.ecommerce.services.RoleService;
import com.springboot.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("staff-management")
@RequiredArgsConstructor
public class StaffAccountController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("")
    public String getViewPage(Model model){
        model.addAttribute("staffs", userService.getAllStaffAccount());
        return "management-staff";
    }

    @PostMapping("save-staff-account")
    public String saveStaffAcc(@ModelAttribute("staff") StaffDTO staffDTO) {
        userService.saveStaffAccount(staffDTO);
        return "redirect:/staff-management";
    }

    @GetMapping("/add-new-staff")
    public String addNewStaffAccount(Model model){
        model.addAttribute("roles", roleService.getAllStaffRoles());
        model.addAttribute("staff", new StaffDTO());
        return "update-staff-form";
    }

    @GetMapping("/delete-staff/{id}")
    public String deleteStaffAccount(@PathVariable("id") String id) {
        userService.deleteStaffAccountById(id);
        return "redirect:/staff-management";
    }

    @GetMapping("/staff-detail/{id}")
    public String staffDetail(@PathVariable("id") String id, Model model) {
        model.addAttribute("roles", roleService.getAllStaffRoles());
        model.addAttribute("staff", userService.getStaffAccById(id));
        return "update-staff-form";
    }
}
