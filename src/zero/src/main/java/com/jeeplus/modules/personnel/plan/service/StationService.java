/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.service;

import java.text.DecimalFormat;
import java.util.List;

import com.jeeplus.common.json.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.plan.entity.Station;
import com.jeeplus.modules.personnel.plan.mapper.StationMapper;

/**
 * 岗位管理Service
 * @author 王伟
 * @version 2019-02-15
 */
@Service
@Transactional(readOnly = true)
public class StationService extends CrudService<StationMapper, Station> {

	@Autowired
	private StationMapper stationMapper;

	public Station get(String id) {
		return super.get(id);
	}

	public List<Station> findList(Station station) {
		return super.findList(station);
	}

	public Page<Station> findPage(Page<Station> page, Station station) {
		return super.findPage(page, station);
	}

	@Transactional(readOnly = false)
	public void save(Station station) {
		super.save(station);
	}
	@Transactional(readOnly = false)
	public AjaxJson preserve(Station station) {
		AjaxJson j= new AjaxJson();
		try{
//			String maxnum = "";
//			Integer num = stationMapper.getMaxNumber() ;
//			DecimalFormat countFormat = new DecimalFormat("0000");
//			maxnum = countFormat.format(num + 1);
//			station.setNumber(maxnum);
			if (station.getIsNewRecord()){
				if (station.getGradeNumber()!=null){
					Station s = new Station();
					s.setGradeNumber(station.getGradeNumber());
					int count = stationMapper.count(s);
					if (count>0){
						j.setSuccess(false);
						j.setMsg("岗位编号重复");
						return j;
					}
				}
				if (station.getName()!=null){
					Station s = new Station();
					s.setName(station.getName());
					int count = stationMapper.count(s);
					if (count>0){
						j.setSuccess(false);
						j.setMsg("岗位名称重复");
						return j;
					}
				}
			}else {
				if (station.getGradeNumber()!=null){
					Station s = new Station();
					s.setGradeNumber(station.getGradeNumber());
					Station station1 = stationMapper.find(s);
					if (station1!=null&&!station1.getId().equals(station.getId())){
						j.setSuccess(false);
						j.setMsg("岗位编号重复");
						return j;
					}
				}
				if (station.getName()!=null){
					Station s = new Station();
					s.setName(station.getName());
					Station station1 = stationMapper.find(s);
					if (station1!=null&&!station1.getId().equals(station.getId())){
						j.setSuccess(false);
						j.setMsg("岗位名称重复");
						return j;
					}
				}
			}
		}catch (Exception e ){
			e.printStackTrace();
		}
		super.save(station);
		j.setSuccess(true);
		j.setMsg("保存岗位成功");
		return j;
	}


	@Transactional(readOnly = false)
	public void delete(Station station) {
		super.deleteByLogic(station);
	}

	public Integer getMaxNumber(){
		return stationMapper.getMaxNumber();
	}

}