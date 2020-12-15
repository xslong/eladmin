package com.xxx.pri.service.impl;

import com.google.common.collect.Lists;
import com.xxx.pri.base.PriAbstractService;
import com.xxx.pri.base.PriBaseRepository;
import com.xxx.pri.domain.PriCompany;
import com.xxx.pri.domain.QPriCompany;
import com.xxx.pri.dto.PriCompanyDto;
import com.xxx.pri.dto.PriCompanyForm;
import com.xxx.pri.repository.PriCompanyRepository;
import com.xxx.pri.service.PriCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;


/**
 * @author xslong
 * @date 2020-05-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PriCompanyServiceImpl extends PriAbstractService<PriCompany, String> implements PriCompanyService {

    @Autowired
    private PriCompanyRepository priCompanyRepository;

    @Autowired
    private PriCompanyDto.Mapper mapper;

    @Override
    protected PriBaseRepository<PriCompany, String> getRepository() {
        return priCompanyRepository;
    }

    @Override
    public PriCompany findByCode(String code) {
        QPriCompany q = QPriCompany.priCompany;
        return getRepository().findOne(q.code.eq(code)).orElse(null);
    }

    @Override
    public PriCompany findByName(String name) {
        QPriCompany q = QPriCompany.priCompany;
        return getRepository().findOne(q.name.eq(name)).orElse(null);
    }

    @Override
    public List<PriCompany> listByIds(Collection<String> ids) {
        QPriCompany q = QPriCompany.priCompany;
        return Lists.newArrayList(getRepository().findAll(q.id.in(ids)));
    }

    @Override
    public PriCompany save(PriCompanyForm form) {
        String code = form.getCode().toLowerCase();
        PriCompany company = null;
        if (form.getId() != null) {
            company = findById(form.getId());
        } else {
            company = new PriCompany();
            company.setCode(code);
        }
        company.setName(form.getName());
        company.setStatus(form.getStatus());
        save(company);
        return company;
    }

    @Transactional
    @Override
    public boolean delete(String[] ids) {
        for (String id : ids) {
            delete(id);
        }
        return true;
    }
}