package ${package}.service.impl;

import ${package}.domain.${className};
import com.xxx.pri.base.PriBaseRepository;
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>
            <#if column_index = 1>
import me.zhengjie.exception.EntityExistException;
            </#if>
        </#if>
    </#list>
</#if>
import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import ${package}.repository.${className}Repository;
import ${package}.service.${className}Service;
import ${package}.dto.${className}Dto;
import ${package}.dto.${className}QueryCriteria;
import com.xxx.pri.base.PriAbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<#if !auto && pkColumnType = 'Long'>
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
</#if>
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import java.util.List;
import java.util.Map;


/**
* @website https://el-admin.vip
* @description 服务实现
* @author ${author}
* @date ${date}
**/
@Service
@RequiredArgsConstructor
public class ${className}ServiceImpl extends PriAbstractService<${className}, ${pkColumnType}> implements ${className}Service {

    private final ${className}Repository ${changeClassName}Repository;
    private final ${className}Dto.Mapper mapper;

    @Override
    protected PriBaseRepository<${className}, ${pkColumnType}> getRepository() {
        return ${changeClassName}Repository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${className}Dto create(${className} resources) {
    <#if !auto && pkColumnType = 'Long'>
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.set${pkCapitalColName}(snowflake.nextId());
    </#if>
    <#if !auto && pkColumnType = 'String'>
        resources.set${pkCapitalColName}(IdUtil.simpleUUID());
    </#if>
    <#if columns??>
        <#list columns as column>
            <#if column.columnKey = 'UNI'>
        if(${changeClassName}Repository.findBy${column.capitalColumnName}(resources.get${column.capitalColumnName}()) != null){
            throw new EntityExistException(${className}.class, "${column.columnName}", resources.get${column.capitalColumnName}());
        }
            </#if>
        </#list>
    </#if>
        return mapper.toDto(getRepository().save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(${className} resources) {
        ${className} ${changeClassName} = ${changeClassName}Repository.findById(resources.get${pkCapitalColName}()).orElseGet(${className}::new);
        ValidationUtil.isNull( ${changeClassName}.get${pkCapitalColName}(), "${className}", "id", resources.get${pkCapitalColName}());
        <#if columns??>
            <#list columns as column>
                <#if column.columnKey = 'UNI'>
                    <#if column_index = 1>
        ${className} ${changeClassName}1 = null;
                    </#if>
        ${changeClassName}1 = ${changeClassName}Repository.findBy${column.capitalColumnName}(resources.get${column.capitalColumnName}());
        if(${changeClassName}1 != null && !${changeClassName}1.get${pkCapitalColName}().equals(${changeClassName}.get${pkCapitalColName}())){
            throw new EntityExistException(${className}.class, "${column.columnName}", resources.get${column.capitalColumnName}());
        }
                </#if>
            </#list>
        </#if>
        ${changeClassName}.copy(resources);
        getRepository().save(${changeClassName});
    }

    @Override
    public Map<String, Object> queryAll(${className}QueryCriteria criteria, Pageable pageable) {
        Page<${className}> page = getRepository().findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(mapper::toDto));
    }

    @Override
    public List<${className}Dto> queryAll(${className}QueryCriteria criteria) {
        return mapper.toDto(getRepository().findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

}