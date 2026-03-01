package com.xzj.utils;

import com.github.pagehelper.PageInfo;
import com.xzj.common.PageResult;

/**
 * @author xuzhijun
 * @date 22:36 2024/2/4
 */
public class PageUtils {

    /**
     * 封装 pagehelper 分页结果
     *
     * @param pageInfo
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> getPageResult(PageInfo<T> pageInfo) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setpageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setDataList(pageInfo.getList());
        pageResult.setSize(pageInfo.getSize());
        return pageResult;
    }

}
