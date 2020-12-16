package com.xxx.pri.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.xxx.pri.domain.Member;
import me.zhengjie.base.BaseMapper;
/**
* @website https://el-admin.vip
* @description /
* @author xslong
* @date 2020-12-16
**/
@Data
public class MemberDto implements Serializable {

    @org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Mapper extends BaseMapper<MemberDto, Member> {
    }


    /** ID */
    private String id;

    /** 用户名 */
    private String username;

    /** 昵称 */
    private String nickName;

    /** 性别 */
    private String gender;

    /** 手机号码 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 头像真实路径 */
    private String avatarPath;

    /** 密码 */
    private String password;

    /** 状态：1启用、0禁用 */
    private Long enabled;

    /** 创建者 */
    private String createBy;

    /** 更新着 */
    private String updateBy;

    /** 修改密码的时间 */
    private Timestamp pwdResetTime;

    /** 创建日期 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;

}