package com.xzj.mapper.system;

import com.xzj.entity.system.SysIcon;
import java.util.List;

/**
 * @author xuzhijun
 * @date 2024年05月21日 15:22
 */
public interface SysIconMapper{

    /**
     * 查询所有
     */
    List<SysIcon> selectAll(SysIcon sysIcon);

}




