package com.scnu.entity.bus;

import lombok.Data;

@Data
public class BusCategory {
    private Integer id;
    private String name;
    private String img;
    private String descr;
    private String content;
    private String serviceType;// 服务类型
}
