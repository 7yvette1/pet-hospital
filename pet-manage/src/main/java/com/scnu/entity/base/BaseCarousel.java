package com.scnu.entity.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 轮播图信息表
*/
@Data
public class BaseCarousel implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    /** 轮播图 */
    private String image;
    /** 是否使用 */
    private String isinuse;
    /** 创建时间 */
    private String time;
    /** 创建人 */
    private String user;


}