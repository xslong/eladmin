package com.xxx.pri.service;

import com.xxx.pri.domain.Member;
import com.xxx.pri.dto.MemberDto;
import com.xxx.pri.dto.MemberQueryCriteria;
import com.xxx.pri.base.PriService;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author xslong
* @date 2020-12-16
**/
public interface MemberService extends PriService<Member, String>{

    /**
    * 创建
    * @param resources /
    * @return MemberDto
    */
    MemberDto create(Member resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Member resources);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(MemberQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<MemberDto>
    */
    List<MemberDto> queryAll(MemberQueryCriteria criteria);
}