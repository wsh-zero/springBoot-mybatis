package com.wsh.config.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {

    public String findOneAuthor(int id) {
        return "dddd";
    }

}
