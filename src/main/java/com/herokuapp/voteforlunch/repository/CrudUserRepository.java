package com.herokuapp.voteforlunch.repository;

import com.herokuapp.voteforlunch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {
    User getByEmail(String email);
}
