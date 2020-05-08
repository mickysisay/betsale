package com.webapps.betsale.models.data;

import com.webapps.betsale.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findByUsername(String username);

    @Override
    List<User> findAll();
}
