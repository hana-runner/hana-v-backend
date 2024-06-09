package com.v.hana.repository.interest;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.interest.Interest;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "InterestRepository", description = "관심사 레포지토리 인터페이스")
@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    @Query("SELECT i FROM Interest i WHERE i.id IN :ids")
    ArrayList<Interest> findAllById(ArrayList<Long> ids);

    ArrayList<Interest> findTitleAndColorAndById(Long id);
}
