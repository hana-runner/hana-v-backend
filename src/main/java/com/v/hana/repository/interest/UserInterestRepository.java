package com.v.hana.repository.interest;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.interest.UserInterest;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "UserInterestRepository", description = "예시 서비스 클래스")
@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
    ArrayList<UserInterest> findByUserId(Long userId);
}
