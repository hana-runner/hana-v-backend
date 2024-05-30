package com.v.hana.command.transaction;

import com.v.hana.entity.category.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateTransactionHistoryCommand {
    private final Long id;
    private final Category category;

    @Builder
    public UpdateTransactionHistoryCommand(Long id, Category category) {
        this.id = id;
        this.category = category;
    }
}
