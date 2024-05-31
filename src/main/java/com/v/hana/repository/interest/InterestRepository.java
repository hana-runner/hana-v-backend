package com.v.hana.repository.interest;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.interest.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "InterestRepository", description = "관심사 레포지토리 인터페이스")
@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {}
