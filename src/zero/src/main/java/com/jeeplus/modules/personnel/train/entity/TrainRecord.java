package com.jeeplus.modules.personnel.train.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.personnel.plan.entity.Station;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * Created by DRYJKUIL on 2019/2/19.
 */
public class TrainRecord extends DataEntity<TrainRecord>{
    private static final long serialVersionUID = 1L;
    private Train train;		// 通知通告ID
    private User user;		// 接受人
    private String readFlag;		// 阅读标记（0：未读；1：已读）
    private Date readDate;		// 阅读时间
    private String qualifyFlag;    //合格标记（0：不合格；1：合格）
    private Office depart;   //部门
    private Station station; //岗位

    public TrainRecord() {
        super();
    }

    public TrainRecord(String id){
        super(id);
    }

    public Office getDepart() {
        return depart;
    }

    public void setDepart(Office depart) {
        this.depart = depart;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public TrainRecord(Train train){
        this.train = train;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Length(min=0, max=1, message="阅读标记（0：未读；1：已读）长度必须介于 0 和 1 之间")
    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getReadDate() {
        return readDate;
    }


    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    @Length(min=0, max=1, message="阅读标记（0：未读；1：已读）长度必须介于 0 和 1 之间")
    public String getQualifyFlag() {
        return qualifyFlag;
    }
    @Length(min=0, max=1, message="合格标记（0：不合格；1：合格）长度必须介于 0 和 1 之间")
    public void setQualifyFlag(String qualifyFlag) {
        this.qualifyFlag = qualifyFlag;
    }
}
