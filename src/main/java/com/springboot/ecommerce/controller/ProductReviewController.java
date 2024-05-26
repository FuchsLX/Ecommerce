package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.controller.dto.ProductReviewDTO;
import com.springboot.ecommerce.services.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    @PostMapping("/save")
    public String saveProductReview(@ModelAttribute("review") ProductReviewDTO pr,
                                    RedirectAttributes redirectAttributes) {
        pr = productReviewService.save(pr);
        redirectAttributes.addAttribute("productSlug", pr.getProductSlug());
        return "redirect:/product/{productSlug}";
    }

    @GetMapping("/detail/{productId}")
    public String getReviewDetail(@PathVariable("productId") String productId,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {
        String email = userDetails.getUsername();
        model.addAttribute("email", email);
        model.addAttribute("review", productReviewService.getReviewOfCurrentUserIfFound(productId, email));
        return "review-detail";
    }

    @GetMapping("/pd/{productId}/{pageNo}")
    @ResponseBody
    public ResponseEntity<List<ProductReviewDTO>> getAllReviewsWithPagination(
            @PathVariable("productId") String productId,
            @PathVariable("pageNo") int pageNo) {
        return ResponseEntity.ok(productReviewService.getAllReviews(productId, pageNo));
    }
}
