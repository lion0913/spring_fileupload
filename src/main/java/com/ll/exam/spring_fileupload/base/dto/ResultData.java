package com.ll.exam.spring_fileupload.base.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public class ResultData<T> {
    private final String resultCode;
    private final String msg;
    private final T body;

    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }

    public boolean isFail() {
        return isSuccess() == false;
    }
}
