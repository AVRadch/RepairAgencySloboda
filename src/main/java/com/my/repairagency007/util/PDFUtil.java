package com.my.repairagency007.util;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class PDFUtil {


    private static final Logger logger = LoggerFactory.getLogger(PDFUtil.class);
    private final ServletContext servletContext;
    private static final String FONT = "fonts/arial.ttf";
    private static final Color LIGHT_GREY = new DeviceRgb(220, 220, 220);
    private static final int TITLE_SIZE = 20;
    private static final Paragraph LINE_SEPARATOR = new Paragraph(new Text("\n"));
    private static final String USER_TITLE = "users";
    private static final String[] USER_CELLS = new String[]{"id", "email", "name", "surname", "role"};
    private static final String EVENT_TITLE = "events";
    private static final String[] EVENT_CELLS =
            new String[]{"title", "date", "location", "reports", "registrations", "visitors"};

    public PDFUtil(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ByteArrayOutputStream createUsersPdf(List<UserDTO> users, String locale) {
        ResourceBundle resourceBundle = getBundle(locale);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = getDocument(output);
        document.add(getTableTitle(resourceBundle.getString(USER_TITLE).toUpperCase()));
        document.add(LINE_SEPARATOR);
        document.add(getUserTable(users, resourceBundle));
        document.close();
        return output;
    }

    public ByteArrayOutputStream createEventsPdf(List<RequestDTO> events, String locale) {
        ResourceBundle resourceBundle = getBundle(locale);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = getDocument(output);
        document.add(getTableTitle(resourceBundle.getString(EVENT_TITLE).toUpperCase()));
        document.add(LINE_SEPARATOR);
        document.add(getEventTable(events, resourceBundle));
        document.close();
        return output;
    }

    private Document getDocument(ByteArrayOutputStream output) {
        PdfWriter writer = new PdfWriter(output);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4.rotate());
        PdfFont font = getPdfFont();
        if (font != null) {
            document.setFont(font);
        }
        return document;
    }

    private static Paragraph getTableTitle(String tableTitle) {
        return new Paragraph(new Text(tableTitle))
                .setFontSize(TITLE_SIZE)
                .setTextAlignment(TextAlignment.CENTER);
    }

    private Table getUserTable(List<UserDTO> users, ResourceBundle resourceBundle) {
        Table table = new Table(new float[]{4, 12, 6, 6, 6});
        table.setWidth(UnitValue.createPercentValue(100));
        addTableHeader(table, USER_CELLS, resourceBundle);
        addUserTableRows(table, users);
        return table;
    }

    private Table getEventTable(List<RequestDTO> events, ResourceBundle resourceBundle) {
        Table table = new Table(new float[]{7, 3, 3, 2, 2, 2});
        table.setWidth(UnitValue.createPercentValue(100));
        addTableHeader(table, EVENT_CELLS, resourceBundle);
        addEventTableRows(table, events);
        return table;
    }

    private void addTableHeader(Table table, String[] cells, ResourceBundle resourceBundle) {
        Stream.of(cells)
                .forEach(columnTitle -> {
                    Cell header = new Cell();
                    header.setBackgroundColor(LIGHT_GREY);
                    header.setBorder(new SolidBorder(1));
                    header.add(new Paragraph(resourceBundle.getString(columnTitle)));
                    table.addCell(header);
                });
    }

    private void addUserTableRows(Table table, List<UserDTO> users) {
        users.forEach(user ->
                {
                    table.addCell(String.valueOf(user.getId()));
                    table.addCell(user.getEmail());
                    table.addCell(user.getFirstName());
                    table.addCell(user.getLastName());
                    table.addCell(user.getRole());
                }
        );
    }

    private void addEventTableRows(Table table, List<RequestDTO> events) {
        events.forEach(event -> {
                   // table.addCell(event.getId());
                    table.addCell(event.getDate());
       /*             table.addCell(event.getLocation());
                    table.addCell(String.valueOf(event.getReports()));
                    table.addCell(String.valueOf(event.getRegistrations()));
                    table.addCell(String.valueOf(event.getVisitors()));*/
                }
        );
    }

    private PdfFont getPdfFont() {
        try {
            URL resource = servletContext.getResource(FONT);
            String fontUrl = resource.getFile();
            return PdfFontFactory.createFont(fontUrl);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private ResourceBundle getBundle(String locale) {
        String resources = "resources";
        if (locale.contains("_")) {
            int index = locale.indexOf("_");
            String lang = locale.substring(0, index);
            String country = locale.substring(index + 1);
            return ResourceBundle.getBundle(resources, new Locale(lang, country));
        } else {
            return ResourceBundle.getBundle(resources, new Locale(locale));
        }
    }
}