package com.v.hana.exception.interest;

import com.v.hana.common.exception.BaseCodeException;
import com.v.hana.common.exception.ImageHttpCode;

public class ImageException extends BaseCodeException {

    public ImageException() {
        super(ImageHttpCode.IMAGE__UPLOAD_FAILED);
    }

}
