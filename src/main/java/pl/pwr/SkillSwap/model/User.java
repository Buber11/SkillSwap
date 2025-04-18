package pl.pwr.SkillSwap.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import pl.pwr.SkillSwap.enums.UserRole;
import pl.pwr.SkillSwap.enums.UserStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@NamedEntityGraph(
        name = "user-skills",
        attributeNodes = {
                @NamedAttributeNode("skills")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    private UserStatus status;

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SkillUser> skills;

    public void addSkill(SkillUser skillUser) {
        if(skills == null){
            skills = new java.util.ArrayList<>();
        }
        skills.add(skillUser);
    }

    public void removeSkill(Skill skill) {
        if (skill == null || skills == null) {
            return;
        }
        for (Iterator<SkillUser> iterator = skills.iterator();
             iterator.hasNext(); ) {
            SkillUser skillUser = iterator.next();

            if (skillUser.getUser().equals(this) &&
                    skillUser.getSkill().equals(skill)) {
                iterator.remove();
                skillUser.setUser(null);
                skillUser.setSkill(null);
            }
        }
    }

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

