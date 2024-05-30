package com.v.hana.repository;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "InterestRepository", description = "예시 서비스 클래스")
@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {}
