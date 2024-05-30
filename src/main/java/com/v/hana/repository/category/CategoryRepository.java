package com.v.hana.repository.category;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "CategoryRepository", description = "카테고리 레포지토리 인터페이스")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}
