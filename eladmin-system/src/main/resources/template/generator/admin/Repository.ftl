package ${package}.repository;

import ${package}.domain.${className};
import com.xxx.pri.base.PriBaseRepository;

/**
* @website https://el-admin.vip
* @author ${author}
* @date ${date}
**/
public interface ${className}Repository extends PriBaseRepository<${className}, ${pkColumnType}> {
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>
    /**
    * 根据 ${column.capitalColumnName} 查询
    * @param ${column.columnName} /
    * @return /
    */
    ${className} findBy${column.capitalColumnName}(${column.columnType} ${column.columnName});
        </#if>
    </#list>
</#if>
}