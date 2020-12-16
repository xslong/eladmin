package com.xxx.pri.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import com.xxx.pri.base.PriBaseEntity;

/**
* @website https://el-admin.vip
* @description /
* @author xslong
* @date 2020-12-16
**/
@Entity
@Data
@Table(name="pri_member")
@org.hibernate.annotations.GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Member extends PriBaseEntity<String> {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    private String id;
    @Column(name = "username",unique = true)
    @ApiModelProperty(value = "用户名")
    private String username;
    @Column(name = "nick_name")
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @Column(name = "gender")
    @ApiModelProperty(value = "性别")
    private String gender;
    @Column(name = "phone")
    @ApiModelProperty(value = "手机号码")
    private String phone;
    @Column(name = "email",unique = true)
    @ApiModelProperty(value = "邮箱")
    private String email;
    @Column(name = "avatar_path")
    @ApiModelProperty(value = "头像真实路径")
    private String avatarPath;
    @Column(name = "password")
    @ApiModelProperty(value = "密码")
    private String password;
    @Column(name = "enabled")
    @ApiModelProperty(value = "状态：1启用、0禁用")
    private Long enabled;
    @Column(name = "pwd_reset_time")
    @ApiModelProperty(value = "修改密码的时间")
    private Timestamp pwdResetTime;

    public void copy(Member source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}