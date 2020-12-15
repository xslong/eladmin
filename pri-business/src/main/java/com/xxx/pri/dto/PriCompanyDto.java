package com.xxx.pri.dto;

import com.xxx.pri.domain.PriCompany;
import lombok.Data;
import me.zhengjie.base.BaseMapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author xslong
* @date 2020-05-07
*/
@Data
public class PriCompanyDto implements Serializable {

    /** ID */
    private String id;

    /** 代码 */
    private String code;

    /** 名字 */
    private String name;

    /** 状态： */
    private String status;

    /** 创建人 */
    private String creator;

    /** 修改人 */
    private String modifier;

    /** 创建时间 */
    private Timestamp createTime;

    /** 修改时间 */
    private Timestamp updateTime;

    @org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Mapper extends BaseMapper<PriCompanyDto, PriCompany> {

        @Override
        @Mappings({
//                @Mapping(source = "dealer.name", target = "dealerName"),
//                @Mapping(source = "dealer.id", target = "dealerId"),
        })
        PriCompanyDto toDto(PriCompany t);
    }
}