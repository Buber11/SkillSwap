package pl.pwr.SkillSwap.enums;

public enum SkillLevel {
    BEGINNER("BEGINNER"),
    INTERMEDIATE("INTERMEDIATE"),
    ADVANCED("ADVANCED"),
    NATIVE("NATIVE"),;
    private final String level;
    SkillLevel(String level) {
        this.level = level;
    }
    public String getLevel() {
        return level;
    }
}
