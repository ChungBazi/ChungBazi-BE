package chungbazi.chungbazi_be.domain.cart.controller;

import chungbazi.chungbazi_be.domain.cart.service.CartService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart/policies")
public class CartController {

    private final CartService cartService;

    // 장바구니에 담기
    @PostMapping("/{policyId}")
    public ApiResponse<String> addPolicyToCart(@PathVariable("policyId") String policyId) {
        
    }
}
