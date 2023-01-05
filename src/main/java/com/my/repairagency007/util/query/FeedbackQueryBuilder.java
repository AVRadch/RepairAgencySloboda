package com.my.repairagency007.util.query;

import com.my.repairagency007.util.query.QueryBuilder;

import java.util.HashSet;
import java.util.Set;

public class FeedbackQueryBuilder extends QueryBuilder {

    private static final Set<String> FEEDBACK_SORT_FIELDS_SET = new HashSet<>();

    static {
        FEEDBACK_SORT_FIELDS_SET.add("date");
        FEEDBACK_SORT_FIELDS_SET.add("rating");
        FEEDBACK_SORT_FIELDS_SET.add("repairer_id");
    }

    public FeedbackQueryBuilder() {
        super("feedback.id");
    }

    @Override
    protected String getGroupByQuery() {
        return " GROUP BY feedback.id ";
    }

    @Override
    protected String checkSortField(String sortField) {
        if (FEEDBACK_SORT_FIELDS_SET.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return "request.id";
    }
}
