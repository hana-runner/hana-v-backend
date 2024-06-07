package com.v.hana.repository.user;

import com.v.hana.entity.user.User;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPw(String username, String pw);

    Optional<User> findByEmail(String email);

    void deleteUserByEmail(String email);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE users u SET u.pw = :newPassword WHERE u.email = :email",
            nativeQuery = true)
    void updatePasswordByEmail(String email, String newPassword);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE users u SET u.email = :newEmail WHERE u.username = :username",
            nativeQuery = true)
    void updateEmailByUsername(String username, String newEmail);

    @Modifying
    @Transactional
    @Query(
            value =
                    "UPDATE users u SET u.is_receive_alarm = :is_receive_alarm WHERE u.username = :username",
            nativeQuery = true)
    void updateIsReceiveAlarmByUsername(String username, boolean is_receive_alarm);
}
