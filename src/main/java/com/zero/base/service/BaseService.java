package com.zero.base.service;

import com.zero.base.mapper.BaseMapper;
import com.zero.util.ResultUtil;
import com.zero.util.TableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class BaseService<M extends BaseMapper, Q, V, E> {
    @Autowired
    private M baseMapper;

    public TableUtil getList(Q q, int pageNum, int pageSize) {
        TableUtil<V> table = new TableUtil<>();
        int count = baseMapper.getCount(q);
        List<V> list = baseMapper.getList(q, (pageNum - 1) * pageSize, pageSize);
        table.setCount(count);
        table.setData(list);
        return table;
    }

    @Transactional
    public ResultUtil save(E e) {
        baseMapper.save(e);
        return new ResultUtil<>("保存成功");
    }

}
