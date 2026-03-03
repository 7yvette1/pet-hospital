package com.scnu.entity.bus;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusNews implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 类型
     */
    private String type;
    /**
     * 简介
     */
    private String descr;
    /**
     * 内容
     */
    private String content;
    /**
     * 封面
     */
    private String cover;
    /**
     * 发布人
     */
    private String user;
    /**
     * 发布时间
     */
    private String time;
}
