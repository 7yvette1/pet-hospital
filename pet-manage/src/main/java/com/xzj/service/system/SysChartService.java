package com.xzj.service.system;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuzhijun
 * @date 2024年07月26日 10:38
 */
@Service
public class SysChartService {
    public Map<String, Object> selectline() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("周一","周二","周三","周四","周五","周六","周日"));
        map.put("y", CollUtil.newArrayList(11, 11, 15, 13, 12, 13, 10));//HuTool_集合工具中的一个可以直接弄成list
        map.put("y1", CollUtil.newArrayList(1, -2, 2, 5, 3, 2, 0));
        return map;
    }
    public Map<String, Object> selectbar() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"));
        map.put("y", CollUtil.newArrayList(2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3));//HuTool_集合工具中的一个可以直接弄成list
        map.put("y1", CollUtil.newArrayList(2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3));
        return map;
    }

    public List<String> selectpie() {
        List<String> list = CollUtil.newArrayList("335", "310", "234", "135", "1548");
        return list;
    }

    public Map<String, Object> selectradar() {
        Map<String, Object> map = new HashMap<>();
        map.put("legend", CollUtil.newArrayList("罗纳尔多","舍普琴科"));
        map.put("y", CollUtil.newArrayList(97, 42, 88, 94, 90, 86));//HuTool_集合工具中的一个可以直接弄成list
        map.put("y1", CollUtil.newArrayList(97, 32, 74, 95, 88, 92));
        return map;
    }
}
