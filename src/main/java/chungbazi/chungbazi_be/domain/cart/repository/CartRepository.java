package chungbazi.chungbazi_be.domain.cart.repository;

import chungbazi.chungbazi_be.domain.cart.entity.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Modifying  // 데이터 변경 (update, delete)
    @Query("DELETE from Cart c WHERE c.user.id=:userId AND c.policy.id IN :policyIds")
    void deleteByUser_IdAndPolicyIds(@Param("userId") Long userId, @Param("policyIds") List<Long> policyIds);

    List<Cart> findByUser_Id(Long userId);
}
