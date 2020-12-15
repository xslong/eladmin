package com.xxx.pri.common.annotation;

import lombok.Getter;

/**
 * @author xslong
 * @time 2020/5/7 22:28
 */
public enum Status {

    Normal("正常"),
    Disabled("禁用");

    @Getter
    private String name;

    Status(String name){
        this.name = name;
    }
}
