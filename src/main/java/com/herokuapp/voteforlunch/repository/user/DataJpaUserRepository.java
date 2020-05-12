package com.herokuapp.voteforlunch.repository.user;

import com.herokuapp.voteforlunch.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class DataJpaUserRepository implements UserRepository {
    private final CrudUserRepository crudRepository;

    public DataJpaUserRepository(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }
}
