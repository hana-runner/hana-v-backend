package com.v.hana.repository;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "UserRepository", description = "예시 서비스 클래스")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
}
