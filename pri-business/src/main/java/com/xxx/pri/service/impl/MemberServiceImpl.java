package com.xxx.pri.service.impl;

import com.xxx.pri.domain.Member;
import com.xxx.pri.base.PriBaseRepository;
import me.zhengjie.exception.EntityExistException;
import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import com.xxx.pri.repository.MemberRepository;
import com.xxx.pri.service.MemberService;
import com.xxx.pri.dto.MemberDto;
import com.xxx.pri.dto.MemberQueryCriteria;
import com.xxx.pri.base.PriAbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
* @author xslong
* @date 2020-12-16
**/
@Service
@RequiredArgsConstructor
public class MemberServiceImpl extends PriAbstractService<Member, String> implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberDto.Mapper mapper;

    @Override
    protected PriBaseRepository<Member, String> getRepository() {
        return memberRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberDto create(Member resources) {
        resources.setId(IdUtil.simpleUUID());
        if(memberRepository.findByUsername(resources.getUsername()) != null){
            throw new EntityExistException(Member.class, "username", resources.getUsername());
        }
        if(memberRepository.findByEmail(resources.getEmail()) != null){
            throw new EntityExistException(Member.class, "email", resources.getEmail());
        }
        return mapper.toDto(getRepository().save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Member resources) {
        Member member = memberRepository.findById(resources.getId()).orElseGet(Member::new);
        ValidationUtil.isNull( member.getId(), "Member", "id", resources.getId());
        Member member1 = null;
        member1 = memberRepository.findByUsername(resources.getUsername());
        if(member1 != null && !member1.getId().equals(member.getId())){
            throw new EntityExistException(Member.class, "username", resources.getUsername());
        }
        member1 = memberRepository.findByEmail(resources.getEmail());
        if(member1 != null && !member1.getId().equals(member.getId())){
            throw new EntityExistException(Member.class, "email", resources.getEmail());
        }
        member.copy(resources);
        getRepository().save(member);
    }

    @Override
    public Map<String, Object> queryAll(MemberQueryCriteria criteria, Pageable pageable) {
        Page<Member> page = getRepository().findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(mapper::toDto));
    }

    @Override
    public List<MemberDto> queryAll(MemberQueryCriteria criteria) {
        return mapper.toDto(getRepository().findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

}