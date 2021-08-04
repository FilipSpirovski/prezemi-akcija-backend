package mk.ukim.finki.dick.prezemiakcijabackend.repository;

import mk.ukim.finki.dick.prezemiakcijabackend.domain.User;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

//    List<User> findAllByRole(Role role);

    Optional<User> findByEmail(String userEmail);
}
