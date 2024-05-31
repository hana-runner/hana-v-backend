package com.v.hana.exception.category;

import com.v.hana.common.exception.BaseCodeException;
import com.v.hana.common.exception.CategoryHttpCode;

public class CategoryIdNotFoundException extends BaseCodeException {
    public CategoryIdNotFoundException() {
        super(CategoryHttpCode.CATEGORY_ID_NOT_FOUND);
    }
}
