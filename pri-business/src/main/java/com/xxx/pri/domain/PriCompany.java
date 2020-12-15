package com.xxx.pri.domain;

import com.xxx.pri.base.PriBaseEntity;
import com.xxx.pri.common.annotation.Status;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* @author xslong
* @date 2020-05-07
*/
@Entity
@Data
@Table(name="pri_company")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class PriCompany extends PriBaseEntity<String> {

    /** ID */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    /** 代码 */
    @Column(name = "code",unique = true,nullable = false)
    @NotBlank
    private String code;

    /** 名字 */
    @Column(name = "name",unique = true,nullable = false)
    @NotBlank
    private String name;

    /** 状态： */
    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

}