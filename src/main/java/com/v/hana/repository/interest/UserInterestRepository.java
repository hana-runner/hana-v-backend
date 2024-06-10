package com.v.hana.repository.interest;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.interest.UserInterest;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@TypeInfo(name = "UserInterestRepository", description = "예시 서비스 클래스")
@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
    ArrayList<UserInterest> findByUserId(Long userId);

    ArrayList<UserInterest> findByInterestIdAndUserId(Long interestId, Long userId);

    @Query(
            "SELECT imageUrl FROM UserInterest WHERE user.id = :userId AND interest.id = :interestId")
    String findImageUrlByUserIdAndInterestId(
            @Param("userId") Long userId, @Param("interestId") Long interestId);

    @Modifying
    @Transactional
    @Query(
            value =
                    "UPDATE user_interests SET subtitle = :subtitle, image_url = :imageUrl WHERE user_id = :userId AND interest_id = :interestId",
            nativeQuery = true)
    void updateUserInterest(
            @Param("userId") Long userId,
            @Param("interestId") Long interestId,
            @Param("subtitle") String subtitle,
            @Param("imageUrl") String imageUrl);

    @MethodInfo(name = "deleteByUserIdAndInterestId", description = "사용자 관심사를 삭제합니다.")
    @Modifying
    @Transactional
    @Query("DELETE FROM UserInterest thd WHERE thd.user.id = :userId AND thd.interest.id = :interestId")
    void deleteByUserIdAndInterestId(@Param("userId") Long userId, @Param("interestId") Long interestId);
}
