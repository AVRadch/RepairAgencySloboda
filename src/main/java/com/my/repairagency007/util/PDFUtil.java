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

    private static final Logger log = LoggerFactory.getLogger(PDFUtil.class);
    private final ServletContext servletContext;
    private static final Color LIGHT_GREY = new DeviceRgb(220, 220, 220);
    private static final String[] REQUEST_CELLS = new String[]{"table.id", "table.user", "table.description",
            "table.date", "table.completion", "table.repairer", "table.payment", "table.totalCost"};

    public PDFUtil(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ByteArrayOutputStream createPdfRequests(List<RequestDTO> requestDTOS, String language) {
        ResourceBundle resourceBundle = getBundle(language);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = getDocument(output);
        Paragraph pdfParagraf = new Paragraph(resourceBundle.getString("requestsTitle"))
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(pdfParagraf);
        document.add(new Paragraph(new Text("\n")));
        document.add(getRequestTable(requestDTOS, resourceBundle));
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

    private Table getRequestTable(List<RequestDTO> requestDTOS, ResourceBundle resourceBundle) {
        Table table = new Table(new float[]{2, 5, 8, 4, 3, 6, 3, 2});
        table.setWidth(UnitValue.createPercentValue(100));
        addTableHeader(table, REQUEST_CELLS, resourceBundle);
        addRequestTableRows(table, requestDTOS);
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

    private void addRequestTableRows(Table table, List<RequestDTO> requestDTOS) {
        requestDTOS.forEach(requestDTO ->
                {
                    table.addCell(String.valueOf(requestDTO.getId()));
                    table.addCell(requestDTO.getUserFirstName() + " " + requestDTO.getUserLastName());
                    table.addCell(requestDTO.getDescription());
                    table.addCell(requestDTO.getDate());
                    table.addCell(requestDTO.getCompletionStatus());
                    String repairerName = requestDTO.getRepairerLastName();
                    if (repairerName == null) {
                        repairerName = "";
                    }
                    table.addCell(repairerName);
                    table.addCell(requestDTO.getPaymentStatus());
                    table.addCell(requestDTO.getTotalCost());
                }
        );
    }

    private PdfFont getPdfFont() {
        try {
            URL resource = servletContext.getResource("fonts/arial.ttf");
            String fontUrl = resource.getFile();
            log.info("fonts url => " + fontUrl);
            return PdfFontFactory.createFont(fontUrl);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private ResourceBundle getBundle(String language) {
        String resources = "language";
        log.info("Language resources => " + language);
        if (language.contains("_")) {
            String[] splitLocale = language.split("_");
            return ResourceBundle.getBundle(resources, new Locale(splitLocale[0], splitLocale[1]));
        } else {
            return ResourceBundle.getBundle(resources, new Locale(language));
        }
    }
}
