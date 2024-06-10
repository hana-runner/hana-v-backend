package com.v.hana.dto.interest;

import com.v.hana.common.constant.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserComparisonDto {
    private final Long interestId;
    private final Long categoryId;
    private final String interestTitle;
    private final String categoryTitle;
    private final Long expense;
    private final Long average;
    private final Long difference;
    private final Gender gender;

    @Builder
    public UserComparisonDto(Long interestId, Long categoryId, String interestTitle, String categoryTitle, Long expense, Long average, Long difference, Gender gender) {
        this.interestId = interestId;
        this.categoryId = categoryId;
        this.interestTitle = interestTitle;
        this.categoryTitle = categoryTitle;
        this.expense = expense;
        this.average = average;
        this.difference = difference;
        this.gender = gender;
    }
}
