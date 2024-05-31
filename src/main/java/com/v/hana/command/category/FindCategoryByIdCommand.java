package com.v.hana.command.category;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindCategoryByIdCommand {
    private final Long id;

    @Builder
    public FindCategoryByIdCommand(Long id) {
        this.id = id;
    }
}
