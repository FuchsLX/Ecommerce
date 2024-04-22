package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.controller.dto.RoleDTO;
import com.springboot.ecommerce.services.PermissionService;
import com.springboot.ecommerce.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/role-management")
public class RoleController {

    private final RoleService roleService;
    private final PermissionService permissionService;

    @GetMapping("")
    public String getViewPage(Model model) {
        model.addAttribute("managerRoles", roleService.getAllStaffRoles());
        return "management-role";
    }

    @GetMapping("/add-new-role")
    public String addNewRolePage(Model model) {
        model.addAttribute("role", new RoleDTO());
        model.addAttribute("permissions", permissionService.getAllPermissions());
        return "update-role-form";
    }

    @PostMapping("/save-role")
    public String saveRole(@ModelAttribute("role") RoleDTO roleDTO) {
        roleService.save(roleDTO);
        return "redirect:/role-management";
    }

    @GetMapping("/delete-role/{id}")
    public String deleteRole(@PathVariable("id") String id) {
        roleService.deleteById(id);
        return "redirect:/role-management";
    }

    @GetMapping("/update-role/{id}")
    public String roleDetail(@PathVariable("id") String id, Model model) {
        model.addAttribute("role", roleService.getById(id));
        model.addAttribute("permissions", permissionService.getAllPermissions());
        return "update-role-form";
    }
}
