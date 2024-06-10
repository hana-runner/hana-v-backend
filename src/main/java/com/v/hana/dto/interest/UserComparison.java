package com.v.hana.dto.interest;

import com.v.hana.common.constant.Gender;

public interface UserComparison {
    Long getInterestId();

    Long getCategoryId();

    String getInterestTitle();

    String getCategoryTitle();

    Long getExpense();

    Long getAverage();

    Long getDifference();

}
