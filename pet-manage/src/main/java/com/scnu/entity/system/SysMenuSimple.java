package com.scnu.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class SysMenuSimple {

    private String label;

    private String menuId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SysMenuSimple> children;

    @JsonIgnore
    private String parentId;
}
