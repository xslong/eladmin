package com.xxx.pri.base;

import com.sun.jna.platform.win32.COM.DispatchVTable;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class PriBaseEntity<ID> extends BaseEntity {

    public abstract ID getId();

    public abstract void setId(ID id);

}
