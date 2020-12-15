package com.xxx.pri.dto;

import com.xxx.pri.common.annotation.Status;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xslong
 * @time 2020/5/11 21:45
 */
@Data
public class PriCompanyForm {


    /** ID */
    private String id;

    /** 经销商ID */
    @NotBlank
    private String dealerId;

    /** 代码 */
    @NotBlank
    private String code;

    /** 名字 */
    @NotBlank
    private String name;

    @NotNull
    private Status status;

}
