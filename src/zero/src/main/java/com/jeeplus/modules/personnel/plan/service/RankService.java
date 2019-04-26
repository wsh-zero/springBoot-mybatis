/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.service;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.kotlin.dto.MapDto;
import com.jeeplus.modules.personnel.plan.entity.Rank;
import com.jeeplus.modules.personnel.plan.mapper.RankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 职级管理Service
 *
 * @author 王伟
 * @version 2019-02-14
 */
@Service
@Transactional(readOnly = true)
public class RankService extends CrudService<RankMapper, Rank> {
    @Autowired
    private RankMapper rankMapper;

    public Rank get(String id) {
        return super.get(id);
    }

    public List<MapDto> getIdAndName() {
        return rankMapper.getIdAndName();
    }

    public List<Rank> findList(Rank rank) {
        return super.findList(rank);
    }

    public Page<Rank> findPage(Page<Rank> page, Rank rank) {
        return super.findPage(page, rank);
    }

    @Transactional(readOnly = false)
    public void save(Rank rank) {
        super.save(rank);
    }

    @Transactional(readOnly = false)
    public AjaxJson preserve(Rank rank) {
        AjaxJson j = new AjaxJson();
        try {
            if (rank.getIsNewRecord()) {
                if (StringUtils.isNotBlank(rank.getRankName())) {
                    Rank r = new Rank();
                    r.setRankName(rank.getRankName());
                    int count = rankMapper.count(r);
                    if (count > 0) {
                        j.setSuccess(false);
                        j.setMsg("职级名称重复");
                        return j;
                    }
                }
            } else {
                if (StringUtils.isNotBlank(rank.getRankName())) {
                    Rank r = new Rank();
                    r.setRankName(rank.getRankName());
                    Rank k = rankMapper.find(r);
                    if (!k.getId().equals(rank.getId()) && k != null) {
                        j.setSuccess(false);
                        j.setMsg("岗位类别重复");
                        return j;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.save(rank);
        j.setSuccess(true);
        j.setMsg("保存职级成功");
        return j;
    }

    @Transactional(readOnly = false)
    public void delete(Rank rank) {
        super.deleteByLogic(rank);
    }


}