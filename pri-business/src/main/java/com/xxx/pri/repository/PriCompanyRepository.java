package com.xxx.pri.repository;

import com.xxx.pri.base.PriBaseRepository;
import com.xxx.pri.domain.PriCompany;

/**
* @author xslong
* @date 2020-05-07
*/
public interface PriCompanyRepository extends PriBaseRepository<PriCompany, String> {
    /**
    * 根据 Code 查询
    * @param code /
    * @return /
    */
    PriCompany findByCode(String code);
    /**
    * 根据 Name 查询
    * @param name /
    * @return /
    */
    PriCompany findByName(String name);
}