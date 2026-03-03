package com.scnu.service.bus;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.common.PageResult;
import com.scnu.entity.bus.BusComment;
import com.scnu.mapper.bus.BusCommentMapper;
import com.scnu.utils.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusCommentService {

    @Resource
    private BusCommentMapper busCommentMapper;

    /**
     * 新增
     */
    public void add(BusComment busComment) {
        busComment.setTime(DateUtil.now());
        if (busComment.getPid() != null) {  // 表示这是一个回复
            BusComment pComment = this.selectById(busComment.getPid());  // 查出父级的评论
            if (pComment == null) {
                return;
            }
            if (pComment.getRootId() != null) {  // 这不是根节点
                busComment.setRootId(pComment.getRootId());
            } else {
                busComment.setRootId(pComment.getId());  // 这是根节点
            }
        }
        busCommentMapper.insert(busComment);
    }

    /**
     * 后台删除
     */
    public void deleteById(Integer id) {
        busCommentMapper.deleteById(id);
    }

    /**
     * 前台递归删除
     */
    public void deepDelete(Integer pid) {
        busCommentMapper.deleteById(pid);
        List<BusComment> children = busCommentMapper.selectByPid(pid);
        if (CollUtil.isNotEmpty(children)) {
            for (BusComment comment : children) {
                pid = comment.getId();
                this.deepDelete(pid);
            }
        }
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            busCommentMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(BusComment busComment) {
        busCommentMapper.updateById(busComment);
    }

    /**
     * 根据ID查询
     */
    public BusComment selectById(Integer id) {
        return busCommentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<BusComment> selectAll(BusComment busComment) {
        return busCommentMapper.selectAll(busComment);
    }

    /**
     * 分页查询
     */
    public PageInfo<BusComment> selectPage(BusComment busComment, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BusComment> list = busCommentMapper.selectAll(busComment);
        return PageInfo.of(list);
    }

    public List<BusComment> selectTree(Integer fid, String module) {
        List<BusComment> commentList = busCommentMapper.selectTree(fid, module);
        // 筛选出最外层的节点
        List<BusComment> rootList = commentList.stream().filter(comment -> comment.getPid() == null).collect(Collectors.toList());
        for (BusComment root : rootList) {
            Integer rootId = root.getId();
            List<BusComment> children = commentList.stream().filter(comment -> rootId.equals(comment.getRootId())).collect(Collectors.toList());
            root.setChildren(children);
        }
        return rootList;
    }

    public PageInfo<BusComment> selectTreeByPage(Integer fid, String module,Integer pageNum, Integer pageSize) {
        //查询所有
        List<BusComment> commentList = busCommentMapper.selectTree(fid, module);

        //查询一级
        BusComment busComment = new BusComment();
        busComment.setModule(module);
        PageHelper.startPage(pageNum, pageSize);
        busComment.setFid(fid);
        List<BusComment> list = busCommentMapper.selectOneAll(busComment);

        PageInfo<BusComment> pageInfo = new PageInfo<>(list);
        PageResult<BusComment> pageResult = PageUtils.getPageResult(pageInfo);
        for (BusComment item : pageResult.getDataList()) {
            item.setChildren(getChildrenComment(commentList,item.getId()));
        }
        return PageInfo.of(list);
    }

    private List<BusComment> getChildrenComment(List<BusComment> commentList, int id) {
        List<BusComment> nextCommentList = commentList.stream().filter(comment -> comment.getPid() != null && comment.getPid() == id).collect(Collectors.toList());
        for (BusComment nextComment : nextCommentList) {
            nextComment.setChildren(getChildrenComment(commentList, nextComment.getId()));
        }
        return nextCommentList;
    }

    public Integer selectCount(Integer fid, String module) {
        return busCommentMapper.selectCount(fid, module);
    }

}