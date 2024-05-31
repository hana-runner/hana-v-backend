package com.v.hana.service.category;

import com.v.hana.command.category.FindCategoryByIdCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.category.Category;
import com.v.hana.exception.category.CategoryIdNotFoundException;
import com.v.hana.repository.category.CategoryRepository;
import com.v.hana.usecase.category.CategoryUseCase;
import org.springframework.stereotype.Service;

@TypeInfo(name = "CategoryService", description = "카테고리 서비스 클래스")
@Service
public class CategoryService implements CategoryUseCase {
    private final CategoryRepository categoryRepository;

    @MethodInfo(name = "readCategoryById", description = "카테고리 ID로 카테고리를 조회합니다.")
    @Override
    public Category readCategoryById(FindCategoryByIdCommand findCategoryByIdCommand) {
        return categoryRepository
                .findById(findCategoryByIdCommand.getId())
                .orElseThrow(CategoryIdNotFoundException::new);
    }

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
