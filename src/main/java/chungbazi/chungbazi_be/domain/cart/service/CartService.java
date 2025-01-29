package chungbazi.chungbazi_be.domain.cart.service;

import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.cart.entity.Cart;
import chungbazi.chungbazi_be.domain.cart.repository.CartRepository;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import chungbazi.chungbazi_be.domain.policy.service.PolicyService;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final PolicyService policyService;
    private final UserService userService;

    // 장바구니에 담기
    @Transactional
    public void addPolicyToCart(Long policyId) {

        Policy policy = policyService.findByPolicyId(policyId);

        Long userId = SecurityUtils.getUserId();
        User user = userService.findByUserId(userId);

        Cart cart = new Cart(policy, user);
        cartRepository.save(cart);
    }

    @Transactional
    public void deletePolicyFromCart(Long policyId) {

        Policy policy = policyService.findByPolicyId(policyId);

        Long userId = SecurityUtils.getUserId();
        User user = userService.findByUserId(userId);

        cartRepository.deleteByPolicy_IdAndUser_Id(policyId, userId);
    }
}
