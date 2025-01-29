package chungbazi.chungbazi_be.domain.cart.repository;

import chungbazi.chungbazi_be.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
    void deleteByPolicy_IdAndUser_Id(Long policyId, Long userId);
}
