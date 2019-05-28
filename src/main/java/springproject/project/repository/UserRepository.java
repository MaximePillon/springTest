package springproject.project.repository;

import springproject.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findById(int i);

    List<User> findByActive(int i);

    List<User> findByBundle(String name);

    List<User> findAllByEmailContaining(String email);
}
