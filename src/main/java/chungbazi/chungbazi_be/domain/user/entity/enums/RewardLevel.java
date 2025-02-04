package chungbazi.chungbazi_be.domain.user.entity.enums;

public enum RewardLevel {
    LEVEL_1(1,0),
    LEVEL_2(2,3),
    LEVEL_3(3,5),
    LEVEL_4(4,7),
    LEVEL_5(5,15),
    LEVEL_6(6,20),
    LEVEL_7(7,25),
    LEVEL_8(8,30),
    LEVEL_9(9,35),
    LEVEL_10(10,40);

    private final int level;
    private final int threashold;

    RewardLevel(int level, int threashold) {this.level = level;this.threashold = threashold;}

    public int getLevel() {
        return level;
    }

    public int getThreashold() {
        return threashold;
    }

    public static RewardLevel getNextRewardLevel(int currentLevel) {
        for (RewardLevel rewardLevel : RewardLevel.values()) {
            if (rewardLevel.getLevel() == currentLevel + 1) {
                return rewardLevel;
            }
        }
        return null;
    }
}
