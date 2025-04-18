package pl.pwr.SkillSwap.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Lazy;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import pl.pwr.SkillSwap.enums.SkillLevel;

import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "SkillUser")
@Table(name = "skills_user")
@NoArgsConstructor
public class SkillUser {

    @EmbeddedId
    private SkillUserId id;

    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "level")
    private SkillLevel skillLevel;

    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @MapsId("skillId")
    private Skill skill;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @MapsId("userId")
    private User user;

    public SkillUser(SkillLevel skillLevel,
                     Skill skill,
                     User user) {
        this.skillLevel = skillLevel;
        this.skill = skill;
        this.user = user;
        this.id = new SkillUserId(skill.getId(), user.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        SkillUser that = (SkillUser) o;
        return Objects.equals(skill, that.skill) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, skill);
    }
    @PreUpdate
    public void update() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
    @PrePersist
    public void create() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
