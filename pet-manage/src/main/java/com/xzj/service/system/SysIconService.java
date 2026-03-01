package com.xzj.service.system;

import com.xzj.entity.system.SysIcon;
import com.xzj.mapper.system.SysIconMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuzhijun
 * @date 2024/5/21 16:48
 */
@Service
public class SysIconService {

    @Resource
    private SysIconMapper sysIconMapper;


    /**
     * 查询所有
     */
    public List<SysIcon> selectAll(SysIcon sysIcon) {
        return sysIconMapper.selectAll(sysIcon);
    }

}
