package api.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserName(String userName);
}
