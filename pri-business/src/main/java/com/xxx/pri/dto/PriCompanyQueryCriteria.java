package com.xxx.pri.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

import java.sql.Timestamp;
import java.util.List;

/**
* @author xslong
* @date 2020-05-07
*/
@Data
public class PriCompanyQueryCriteria {

    @Query(blurry = "name,code",type = Query.Type.INNER_LIKE)
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;


    @Query(propName = "id", joinName = "dealer")
    private String dealerId;


}