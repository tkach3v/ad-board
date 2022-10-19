package com.tkachev.adboard.repositories;

import com.tkachev.adboard.entity.Chat;
import com.tkachev.adboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByUsersIn(Set<User> users);
}
