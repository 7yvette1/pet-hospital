package com.xzj.service.system;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.entity.other.VerificationCodeVo;
import com.xzj.entity.system.SysLogin;
import com.xzj.mapper.system.SysLoginMapper;
import com.xzj.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuzhijun
 * @date 2024/5/21 16:48
 */
@Service
public class SysLoginService {

    @Resource
    private SysLoginMapper sysLoginMapper;
    @Resource
    private Cache<String, Object> caffeineCache;


    /**
     * 根据ID删除
     */
    public void deleteById(Integer id) {
        sysLoginMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            sysLoginMapper.deleteById(id);
        }
    }

    /**
     * 根据ID查询
     */
    public SysLogin selectById(Integer id) {
        return sysLoginMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<SysLogin> selectAll(SysLogin sysLogin) {
        return sysLoginMapper.selectAll(sysLogin);
    }

    /**
     * 分页查询
     */
    public PageInfo<SysLogin> selectPage(SysLogin sysLogin, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysLogin> list = sysLoginMapper.selectAll(sysLogin);
        return PageInfo.of(list);
    }


    /**
     * 获取验证码
     */
    public VerificationCodeVo getVerificationCode() {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(70, 40, 4, 30);
        String key = UUIDUtils.generateUUID();
        String imageBase64Data = lineCaptcha.getImageBase64Data();
        VerificationCodeVo verificationCodeVo = new VerificationCodeVo();
        verificationCodeVo.setFlagKey(key);
        verificationCodeVo.setDataUrl(imageBase64Data);
        //放入缓存中
        String verificationCode = lineCaptcha.getCode();
        caffeineCache.put(key,verificationCode);
        return verificationCodeVo;
    }
}
