package chungbazi.chungbazi_be.domain.cart.controller;

import chungbazi.chungbazi_be.domain.cart.dto.CartRequestDTO;
import chungbazi.chungbazi_be.domain.cart.dto.CartResponseDTO;
import chungbazi.chungbazi_be.domain.cart.service.CartService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart/policies")
public class CartController {

    private final CartService cartService;

    // 장바구니에 담기
    @PostMapping("/{policyId}")
    public ApiResponse<String> addPolicyToCart(@PathVariable("policyId") Long policyId) {

        cartService.addPolicyToCart(policyId);
        return ApiResponse.onSuccess("장바구니에 성공적으로 추가되었습니다.");
    }

    // 장바구니 삭제
    @DeleteMapping("/delete")
    public ApiResponse<String> deletePolicyFromCart(@RequestBody CartRequestDTO.CartDeleteList deleteList) {

        cartService.deletePolicyFromCart(deleteList);
        return ApiResponse.onSuccess("장바구니에서 성공적으로 삭제되었습니다.");
    }

    // 장바구니 정책 전체 조회
    @GetMapping
    public ApiResponse<List<CartResponseDTO.CartPolicyList>> getPoliciesFromCart() {

        List<CartResponseDTO.CartPolicyList> policies = cartService.getPoliciesFromCart();
        return ApiResponse.onSuccess(policies);
    }

}
