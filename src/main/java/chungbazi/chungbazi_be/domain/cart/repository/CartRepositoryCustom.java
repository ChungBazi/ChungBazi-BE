package chungbazi.chungbazi_be.domain.cart.repository;

import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import chungbazi.chungbazi_be.domain.user.entity.User;
import java.util.List;


public interface CartRepositoryCustom {

    List<Policy> findByCategoryAndUser(Category category, Long cursor, User user, int size, String order);

}
