package com.xxx.pri.rest;

import com.xxx.pri.domain.PriCompany;
import com.xxx.pri.dto.PriCompanyDto;
import com.xxx.pri.dto.PriCompanyForm;
import com.xxx.pri.dto.PriCompanyQueryCriteria;
import com.xxx.pri.service.PriCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.annotation.Log;
import me.zhengjie.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xslong
 * @date 2020-05-07
 */
@Api(tags = "PriCompany管理")
@RestController
@RequestMapping("/api/company")
public class PriCompanyController {

    @Autowired
    private PriCompanyService companyService;

    @Autowired
    private PriCompanyDto.Mapper mapper;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('aiotCompany:list')")
    public void download(HttpServletResponse response, PriCompanyQueryCriteria criteria) throws IOException {
        List<PriCompanyDto> all = mapper.toDto(companyService.queryAll(criteria));
        List<Map<String, Object>> list = new ArrayList<>();
        for (PriCompanyDto aiotCompany : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("代码", aiotCompany.getCode());
            map.put("名字", aiotCompany.getName());
            map.put("状态：", aiotCompany.getStatus());
            map.put("创建人", aiotCompany.getCreator());
            map.put("修改人", aiotCompany.getModifier());
            map.put("创建时间", aiotCompany.getCreateTime());
            map.put("修改时间", aiotCompany.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @GetMapping
    @Log("查询aiotCompany")
    @ApiOperation("查询aiotCompany")
    @PreAuthorize("@el.check('aiotCompany:list')")
    public ResponseEntity<?> getAiotCompanys(PriCompanyQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(companyService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增aiotCompany")
    @ApiOperation("新增aiotCompany")
    @PreAuthorize("@el.check('aiotCompany:add')")
    public ResponseEntity<PriCompany> create(@Validated @RequestBody PriCompanyForm form) {
        return new ResponseEntity<>(save(form), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改aiotCompany")
    @ApiOperation("修改aiotCompany")
    @PreAuthorize("@el.check('aiotCompany:edit')")
    public ResponseEntity<?> update(@Validated @RequestBody PriCompanyForm form) {
        save(form);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private PriCompany save(PriCompanyForm form) {
        return companyService.save(form);
    }

    @Log("删除aiotCompany")
    @ApiOperation("删除aiotCompany")
    @PreAuthorize("@el.check('aiotCompany:del')")
    @DeleteMapping
    public ResponseEntity<?> deleteAll(@RequestBody String[] ids) {
        companyService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}