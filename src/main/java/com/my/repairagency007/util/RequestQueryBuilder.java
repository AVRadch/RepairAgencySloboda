package com.my.repairagency007.util;

import java.util.HashSet;
import java.util.Set;

public class RequestQueryBuilder extends QueryBuilder{

    private static final Set<String> REQUEST_SORT_FIELDS_SET = new HashSet<>();

    static {
        REQUEST_SORT_FIELDS_SET.add("date");
        REQUEST_SORT_FIELDS_SET.add("completion_status_id");
        REQUEST_SORT_FIELDS_SET.add("payment_status_id");
        REQUEST_SORT_FIELDS_SET.add("total_cost");
    }

    public RequestQueryBuilder() {
        super("request.id");
    }
    @Override
    protected String getGroupByQuery() {
        return " GROUP BY request.id ";
    }

    @Override
    protected String checkSortField(String sortField) {
        if (REQUEST_SORT_FIELDS_SET.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return "request.id";
    }
}
