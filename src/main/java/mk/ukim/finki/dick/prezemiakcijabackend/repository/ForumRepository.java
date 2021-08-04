package mk.ukim.finki.dick.prezemiakcijabackend.repository;

import mk.ukim.finki.dick.prezemiakcijabackend.domain.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {

    Optional<Forum> findByInitiativeId(Long initiativeId);
}
