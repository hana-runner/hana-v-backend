package com.v.hana.usecase.category;

import com.v.hana.command.category.FindCategoryByIdCommand;
import com.v.hana.entity.category.Category;

public interface CategoryUseCase {
    Category readCategoryById(FindCategoryByIdCommand findCategoryByIdCommand);
}
