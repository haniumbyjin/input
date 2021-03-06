package com.conference.push.model.wrapper;

import com.conference.push.model.enums.ResStatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> {

    private int code;
    private String message;
    private T data;

    public ResponseWrapper (ResStatusCode resStatusCode) {
        this.code = resStatusCode.getCode();
        this.message = resStatusCode.getMessage();
    }

    public ResponseWrapper (ResStatusCode resStatusCode, T t) {
        this.code = resStatusCode.getCode();
        this.message = resStatusCode.getMessage();
        this.data = t;
    }


}
