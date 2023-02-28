package com.my.repairagency007.util;


import com.my.repairagency007.controller.command.admin.AllUsersCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class PaginationUtil {

    private static final Logger log = LoggerFactory.getLogger(PaginationUtil.class);

    public static void paginate(int totalRecords, HttpServletRequest request) {
        int records = getInt(request.getParameter("records"), 1, 5);
        int offset = getInt(request.getParameter("offset"), 0, 0);
        setAttributes(request, totalRecords, records, offset);
    }

    private static void setAttributes(HttpServletRequest request, int totalRecords, int records, int offset) {

        log.info("Total records" + totalRecords);
        log.info("Records = " + records);
        log.info("Offset = " + offset);
        int pages = totalRecords / records + (totalRecords % records != 0 ? 1 : 0);
        log.info("pages = " + pages);
        int currentPage = offset / records + 1;
        log.info("current Pages = " + currentPage);
        int startPage = currentPage == pages ? Math.max(currentPage - 2, 1)
                : Math.max(currentPage - 1, 1);
        log.info("Start page = " + startPage);
        int endPage = Math.min(startPage + 2, pages);
        log.info("End page = " + endPage);
        final HttpSession session = request.getSession();
        request.setAttribute("offset", offset);
        session.setAttribute("offset", offset);
        request.setAttribute("records", records);
        session.setAttribute("records", records);
        request.setAttribute("pages", pages);
        session.setAttribute("pages", pages);
        request.setAttribute("currentPage", currentPage);
        session.setAttribute("currentPage", currentPage);
        request.setAttribute("start", startPage);
        session.setAttribute("start", startPage);
        request.setAttribute("end", endPage);
        session.setAttribute("end", endPage);
    }

    private static int getInt(String value, int min, int defaultValue) {
        try {
            int records = Integer.parseInt(value);
            if (records >= min) {
                return records;
            }
        } catch (NumberFormatException e) {
            return defaultValue;
        }
        return defaultValue;
    }

    private PaginationUtil() {}
}
