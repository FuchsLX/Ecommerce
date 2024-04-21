package com.springboot.ecommerce.controller;



import com.springboot.ecommerce.entities.category.Category;
import com.springboot.ecommerce.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category-management")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("add-new-category.html")
    public String getAddCategory(Model model){
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "update-category-form";
    }

    @PostMapping("save-category")
    public String saveNewCategory(@ModelAttribute("category") Category category){
        categoryService.saveCategory(category);
        return "redirect:/category-management/categories-list";
    }


    @GetMapping("categories-list")
    public String viewCategoryList(Model model){
        model.addAttribute("categoriesList", categoryService.getAllCategories());
        model.addAttribute("categoriesParentList", categoryService.getAllCategoryParent());
        return "management-category";
    }

    @GetMapping("delete-category/{id}")
    public String deleteCategory(@PathVariable("id") String id){
        categoryService.deleteCategory(id);
        return "redirect:/category-management/categories-list";
    }

    @GetMapping("update-category-form/{id}")
    public String updateCategory(@PathVariable("id") String id, Model model){
        model.addAttribute("category", categoryService.getCategoryById(id));
        model.addAttribute("categories", categoryService.getAllCategoriesExcept(id));
        return "update-category-form";
    }
}
