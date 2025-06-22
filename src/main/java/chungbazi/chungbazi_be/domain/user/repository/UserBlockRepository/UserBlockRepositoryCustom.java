package chungbazi.chungbazi_be.domain.user.repository.UserBlockRepository;

public interface UserBlockRepositoryCustom {
    boolean existsBlockBetweenUsers(Long user1Id, Long user2Id);
}
