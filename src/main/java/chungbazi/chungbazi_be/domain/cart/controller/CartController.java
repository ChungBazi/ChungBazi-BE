package chungbazi.chungbazi_be.domain.cart.controller;

import chungbazi.chungbazi_be.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse
}
