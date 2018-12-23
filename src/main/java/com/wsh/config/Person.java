package com.wsh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private Integer age;
    private String name;
    private Boolean boss;
    private Date birth;
    private Map<String, Object> maps;
    private List<String> list;
    private MalePerson malePerson;
    private List<MalePerson> malePersonList;
}

@Data
class MalePerson {
    private String name;
    private JavaPerson javaPerson;

}

@Data
class JavaPerson {
    private String name;

}