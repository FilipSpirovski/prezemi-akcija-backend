package mk.ukim.finki.dick.prezemiakcijabackend.repository;

import mk.ukim.finki.dick.prezemiakcijabackend.domain.Initiative;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.Category;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InitiativeRepository extends JpaRepository<Initiative, Long> {

    List<Initiative> findAllByInitiatorEmail(String initiatorEmail);

    List<Initiative> findAllByCategory(Category category);

    List<Initiative> findAllByEventType(EventType eventType);
}
