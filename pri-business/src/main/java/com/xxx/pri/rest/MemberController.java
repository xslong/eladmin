package com.xxx.pri.rest;

import me.zhengjie.annotation.Log;
import com.xxx.pri.domain.Member;
import com.xxx.pri.service.MemberService;
import com.xxx.pri.dto.MemberDto;
import com.xxx.pri.dto.MemberQueryCriteria;
import me.zhengjie.utils.FileUtil;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @website https://el-admin.vip
* @author xslong
* @date 2020-12-16
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "member管理")
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberDto.Mapper mapper;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('member:list')")
    public void download(HttpServletResponse response, MemberQueryCriteria criteria) throws IOException {
        List<MemberDto> all = memberService.queryAll(criteria);
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

    @GetMapping
    @Log("查询member")
    @ApiOperation("查询member")
    @PreAuthorize("@el.check('member:list')")
    public ResponseEntity<Object> query(MemberQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(memberService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增member")
    @ApiOperation("新增member")
    @PreAuthorize("@el.check('member:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Member resources){
        return new ResponseEntity<>(memberService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改member")
    @ApiOperation("修改member")
    @PreAuthorize("@el.check('member:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Member resources){
        memberService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除member")
    @ApiOperation("删除member")
    @PreAuthorize("@el.check('member:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody String[] ids) {
        memberService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}