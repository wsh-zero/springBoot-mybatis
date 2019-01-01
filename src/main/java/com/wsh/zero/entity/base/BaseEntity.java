package com.wsh.zero.entity.base;

import com.wsh.util.DateTimeUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BaseEntity {
    private Timestamp gmtTime = DateTimeUtil.getCurrentTime();
}