package com.xxx.pri.service;


import com.xxx.pri.base.PriService;
import com.xxx.pri.domain.PriCompany;
import com.xxx.pri.dto.PriCompanyForm;

import java.util.Collection;
import java.util.List;

/**
 * @author xslong
 * @date 2020-05-07
 */
public interface PriCompanyService extends PriService<PriCompany, String> {

    PriCompany findByCode(String code);

    PriCompany findByName(String name);

    List<PriCompany> listByIds(Collection<String> ids);

    PriCompany save(PriCompanyForm form) ;

    boolean delete(String[] ids);
}