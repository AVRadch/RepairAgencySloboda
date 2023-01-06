package com.my.repairagency007.util;

import jakarta.servlet.http.HttpServletRequest;

public final class PaginationUtil {

    public static void paginate(int totalRecords, HttpServletRequest request) {
        int records = getInt(request.getParameter("records"), 1, 5);
        int offset = getInt(request.getParameter("offset"), 0, 0);
        setAttributes(request, totalRecords, records, offset);
    }

    private static void setAttributes(HttpServletRequest request, int totalRecords, int records, int offset) {
        int pages = totalRecords / records + (totalRecords % records != 0 ? 1 : 0);
        int currentPage = offset / records + 1;
        int startPage = currentPage == pages ? Math.max(currentPage - 2, 1)
                : Math.max(currentPage - 1, 1);
        int endPage = Math.min(startPage + 2, pages);
        request.setAttribute("offset", offset);
        request.setAttribute("records", records);
        request.setAttribute("pages", pages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("start", startPage);
        request.setAttribute("end", endPage);
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
