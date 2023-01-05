package com.my.repairagency007.util.query;

import com.my.repairagency007.util.query.QueryBuilder;

import java.util.HashSet;
import java.util.Set;

public class UserQueryBuilder extends QueryBuilder {

    private static final Set<String> USER_SORT_FIELDS_SET = new HashSet<>();

    static {
        USER_SORT_FIELDS_SET.add("email");
        USER_SORT_FIELDS_SET.add("name");
        USER_SORT_FIELDS_SET.add("surname");
    }

    public UserQueryBuilder() {
        super("id");
    }

    @Override
    protected String getGroupByQuery() {
        return "";
    }

    @Override
    protected String checkSortField(String sortField) {
        if (USER_SORT_FIELDS_SET.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return "id";
    }
}
