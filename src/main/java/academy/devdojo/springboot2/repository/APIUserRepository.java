package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.APIUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APIUserRepository extends JpaRepository<APIUser, Integer> {

    APIUser findByUsername(String username);

}
