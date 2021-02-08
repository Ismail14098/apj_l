package kz.bank.demo.persistance.repository;

import kz.bank.demo.persistance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String passwordHash);
}
