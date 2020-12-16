package ${package}.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
<#if isNotNullColumns??>
import javax.validation.constraints.*;
</#if>
<#if hasDateAnnotation>
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
</#if>
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
import com.xxx.pri.base.PriBaseEntity;

/**
* @website https://el-admin.vip
* @description /
* @author ${author}
* @date ${date}
**/
@Entity
@Data
@Table(name="${tableName}")
<#if !auto && pkColumnType = 'String'>
@org.hibernate.annotations.GenericGenerator(name = "jpa-uuid", strategy = "uuid")
</#if>
<#assign ignoreCol = ['create_by', 'update_by', 'create_time', 'update_time']>
public class ${className} extends PriBaseEntity<${pkColumnType}> {
<#if columns??>
    <#list columns as column>
<#if !(ignoreCol?seq_contains(column.columnName))>
    <#if column.columnKey = 'PRI'>
    @Id
    <#if auto>
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    <#if !auto && pkColumnType = 'String'>
    @GeneratedValue(generator = "jpa-uuid")
    </#if>
    </#if>
    @Column(name = "${column.columnName}"<#if column.columnKey = 'UNI'>,unique = true</#if><#if column.istNotNull && column.columnKey != 'PRI'>,nullable = false</#if>)
    <#if column.istNotNull && column.columnKey != 'PRI'>
        <#if column.columnType = 'String'>
    @NotBlank
        <#else>
    @NotNull
        </#if>
    </#if>
    <#if (column.dateAnnotation)??>
    <#if column.dateAnnotation = 'CreationTimestamp'>
    @CreationTimestamp
    <#else>
    @UpdateTimestamp
    </#if>
    </#if>
    <#if column.remark != ''>
    @ApiModelProperty(value = "${column.remark}")
    <#else>
    @ApiModelProperty(value = "${column.changeColumnName}")
    </#if>
    private ${column.columnType} ${column.changeColumnName};
</#if>
    </#list>
</#if>

    public void copy(${className} source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}