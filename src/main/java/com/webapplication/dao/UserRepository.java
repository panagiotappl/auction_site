package com.webapplication.dao;

import com.webapplication.entity.Message;
import com.webapplication.entity.User;
import org.springframework.cglib.core.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends MongoRepository<User, String>  {

    User findUserByUsernameOrEmail(String username, String email);

    User findUserByUsername(String username);

    User findUserByUserId(String userId);

    User findUserByUserIdAndUsername(String userId, String username);

    Long countByEmail(String email);

    @Query("{ 'password' : '?2', $or: [{'username' : '?0'}, {'email' : '?1'}] }")
    User findUserByUsernameOrEmailAndPassword(String username, String email, String password);

    List<User> findUserByIsVerified(Boolean isVerified, Pageable pageable);

}
