
package pl.pwr.SkillSwap.repository;

import org.apache.el.parser.BooleanNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.pwr.SkillSwap.dto.UserRatingDTO;
import pl.pwr.SkillSwap.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            SELECT 
                u.id AS userId,
                u.username,
                u.email,
                ud.name,
                ud.surname,
                ud.description,
                ROUND(AVG(r.value), 2) AS averageRate
            FROM users u
            JOIN user_details ud ON u.id = ud.user_id
            LEFT JOIN rate r ON u.id = r.owner_id
            GROUP BY u.id, ud.name, ud.surname, ud.description
            ORDER BY averageRate DESC
            """,
            countQuery = """
            SELECT COUNT(DISTINCT u.id)
            FROM users u
            """,
            nativeQuery = true)
    Page<UserRatingDTO> findAllWithAverageRating(Pageable pageable);

    Optional<User> findByUsername(String username);

    @EntityGraph(value = "user-skills", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findUserWithSkillsById(Long id);

}