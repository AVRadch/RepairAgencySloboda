package com.my.repairagency007.util.query;

import com.my.repairagency007.controller.command.admin.AdminFilteredRepairedUserCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public abstract class QueryBuilder {

    private static final Logger log = LoggerFactory.getLogger(QueryBuilder.class);
    protected final List<String> filters = new ArrayList<>();
    private String sortField;
    private String order = "ASC";
    private int offset = 0;
    private int records = 5;

    protected QueryBuilder(String sortField) {
        this.sortField = sortField;
    }

    public QueryBuilder setUserIdFilter(long userIdFilter) {
        filters.add("user_id=" + userIdFilter);
        return this;
    }

    public QueryBuilder setDateFilter(String dateFilter) {
        if (dateFilter != null && dateFilter.equals("passed")) {
            filters.add("date < now()");
        } else if (dateFilter != null && dateFilter.equals("upcoming")) {
            filters.add("date > now()");
        }
        return this;
    }

    public QueryBuilder setRoleFilter(String roleFilter) {
        if (roleFilter != null && isPositiveInt(roleFilter)) {
            filters.add("role_id=" + roleFilter);
        }
        return this;
    }

    public QueryBuilder setSortField(String sortField) {
        if (sortField != null) {
            this.sortField = checkSortField(sortField);
        }
        return this;
    }

    public QueryBuilder setOrder(String order) {
        if (order != null && order.equalsIgnoreCase("DESC")) {
            this.order = "DESC";
        }
        return this;
    }

    public QueryBuilder setLimits(String offset, String records) {
        if (offset != null && isPositiveInt(offset)) {
            this.offset = Integer.parseInt(offset);
        }
        if (records != null && isPositiveInt(records)) {
            this.records = Integer.parseInt(records);
        }
        return this;
    }

    public String getQuery() {
        log.info("getQuery filter query => " + getFilterQuery());
        return getFilterQuery() + getGroupByQuery() + getSortQuery() + getLimitQuery();
    }

    public String getRecordQuery() {
        return getFilterQuery();
    }

    private String getFilterQuery() {
        log.info ("Inside getFilterQuery filters = " + filters);
        if (filters.isEmpty()) {
            return "";
        }
        StringJoiner stringJoiner = new StringJoiner(" AND ", " WHERE ", " ");
        filters.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    protected abstract String getGroupByQuery();

    private String getSortQuery() {
        return " ORDER BY " + sortField + " " + order;
    }

    private String getLimitQuery() {
        return " LIMIT " + offset + ", " + records;
    }

    protected abstract String checkSortField(String sortField);

    protected boolean isPositiveInt(String intString) {
        try {
            int i = Integer.parseInt(intString);
            if (i < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }



}
