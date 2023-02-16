package com.my.repairagency007.util.query;

import com.my.repairagency007.model.entity.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public class RequestQueryBuilder extends QueryBuilder {

    private static final Logger log = LoggerFactory.getLogger(QueryBuilder.class);

    private static final Set<String> REQUEST_SORT_FIELDS_SET = new HashSet<>();

    static {
        REQUEST_SORT_FIELDS_SET.add("date");
        REQUEST_SORT_FIELDS_SET.add("completion_status_id");
        REQUEST_SORT_FIELDS_SET.add("payment_status_id");
        REQUEST_SORT_FIELDS_SET.add("total_cost");
    }

    public RequestQueryBuilder() {
        super("r_id");
    }
    @Override
    protected String getGroupByQuery() {
        return " GROUP BY r_id ";
    }

    @Override
    protected String checkSortField(String sortField) {
        if (REQUEST_SORT_FIELDS_SET.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return "r_id";
    }
    public RequestQueryBuilder setCompletionStatusFilter(String completionStatusIdFilter) {

        if (completionStatusIdFilter != null && isPositiveInt(completionStatusIdFilter)) {
            filters.add("completion_status_id=" + completionStatusIdFilter);
        }
        return this;
    }

    public RequestQueryBuilder getQueryBuilder(HttpServletRequest request) {
        RequestQueryBuilder queryBuilder = new RequestQueryBuilder();
        queryBuilder.setDateFilter(request.getParameter("date"));
        queryBuilder.setCompletionStatusFilter(request.getParameter("status-id") + 1);
        log.info("Request querry filters => " + filters);
        queryBuilder.setSortField(request.getParameter("sort"));
        queryBuilder.setOrder(request.getParameter("order"));
        return queryBuilder;
 /*               new RequestQueryBuilder()
                .setDateFilter(request.getParameter("date"))
                .setCompletionStatusFilter(request.getParameter("status-id") + 1)
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));  */
    }
}
