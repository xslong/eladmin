package com.xxx.pri.repository;

import com.xxx.pri.domain.Member;
import com.xxx.pri.base.PriBaseRepository;

/**
* @website https://el-admin.vip
* @author xslong
* @date 2020-12-16
**/
public interface MemberRepository extends PriBaseRepository<Member, String> {
    /**
    * 根据 Username 查询
    * @param username /
    * @return /
    */
    Member findByUsername(String username);
    /**
    * 根据 Email 查询
    * @param email /
    * @return /
    */
    Member findByEmail(String email);
}