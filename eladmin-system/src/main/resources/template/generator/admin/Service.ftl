package ${package}.service;

import ${package}.domain.${className};
import ${package}.dto.${className}Dto;
import ${package}.dto.${className}QueryCriteria;
import com.xxx.pri.base.PriService;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author ${author}
* @date ${date}
**/
public interface ${className}Service extends PriService<${className}, ${pkColumnType}>{

    /**
    * 创建
    * @param resources /
    * @return ${className}Dto
    */
    ${className}Dto create(${className} resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(${className} resources);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(${className}QueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<${className}Dto>
    */
    List<${className}Dto> queryAll(${className}QueryCriteria criteria);
}