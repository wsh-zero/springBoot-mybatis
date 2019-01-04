package com.wsh.zero.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TestEntity {
    public String id;
    public String name;
    @NotNull(message = "用户行政区划不能为空")
    public TestEntity1 zero;
    public List<TestEntity1> zeroList;
}

@Data
class TestEntity1 {
    public String field;
    public String value;
    public String splicing;
}

