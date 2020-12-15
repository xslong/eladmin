/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package com.xxx.pri.service.impl;

import com.xxx.pri.domain.Member;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.xxx.pri.repository.MemberRepository;
import com.xxx.pri.service.MemberService;
import com.xxx.pri.service.dto.MemberDto;
import com.xxx.pri.service.dto.MemberQueryCriteria;
import com.xxx.pri.service.mapstruct.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author xslong
* @date 2020-12-15
**/
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public Map<String,Object> queryAll(MemberQueryCriteria criteria, Pageable pageable){
        Page<Member> page = memberRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(memberMapper::toDto));
    }

    @Override
    public List<MemberDto> queryAll(MemberQueryCriteria criteria){
        return memberMapper.toDto(memberRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public MemberDto findById(String id) {
        Member member = memberRepository.findById(id).orElseGet(Member::new);
        ValidationUtil.isNull(member.getId(),"Member","id",id);
        return memberMapper.toDto(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberDto create(Member resources) {
        resources.setId(IdUtil.simpleUUID()); 
        if(memberRepository.findByUsername(resources.getUsername()) != null){
            throw new EntityExistException(Member.class,"username",resources.getUsername());
        }
        if(memberRepository.findByEmail(resources.getEmail()) != null){
            throw new EntityExistException(Member.class,"email",resources.getEmail());
        }
        return memberMapper.toDto(memberRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Member resources) {
        Member member = memberRepository.findById(resources.getId()).orElseGet(Member::new);
        ValidationUtil.isNull( member.getId(),"Member","id",resources.getId());
        Member member1 = null;
        member1 = memberRepository.findByUsername(resources.getUsername());
        if(member1 != null && !member1.getId().equals(member.getId())){
            throw new EntityExistException(Member.class,"username",resources.getUsername());
        }
        member1 = memberRepository.findByEmail(resources.getEmail());
        if(member1 != null && !member1.getId().equals(member.getId())){
            throw new EntityExistException(Member.class,"email",resources.getEmail());
        }
        member.copy(resources);
        memberRepository.save(member);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            memberRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<MemberDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MemberDto member : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户名", member.getUsername());
            map.put("昵称", member.getNickName());
            map.put("性别", member.getGender());
            map.put("手机号码", member.getPhone());
            map.put("邮箱", member.getEmail());
            map.put("头像真实路径", member.getAvatarPath());
            map.put("密码", member.getPassword());
            map.put("状态：1启用、0禁用", member.getEnabled());
            map.put("创建者", member.getCreateBy());
            map.put("更新着", member.getUpdateBy());
            map.put("修改密码的时间", member.getPwdResetTime());
            map.put("创建日期", member.getCreateTime());
            map.put("更新时间", member.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}