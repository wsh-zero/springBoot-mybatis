package com.wsh.zero.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysMenuVO implements Comparable<SysMenuVO> {
    private String id;
    private String name;
    private String title;
    private Boolean spread;
    private String icon;
    private String jump;
    private String parent;
    private Integer level;
    private List list;

    @Override
    public int compareTo(@NotNull SysMenuVO o) {
        return this.getLevel() - o.getLevel();
    }
}