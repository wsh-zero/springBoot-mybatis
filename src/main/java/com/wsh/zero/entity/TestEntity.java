package com.wsh.zero.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class TestEntity {
    public String id;
    public Set<TestEntity1> testEntity;
}

@Data
class TestEntity1 {
    public String field;
    public String value;
    public String splicing;
}

