package phoneBook;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
//import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;

/**
 *
 * @author Adam
 */
public class PdfGeneration {
    
        public void pdfGeneration(String fileName, ObservableList<Person> data) {
        Document document = new Document();
        try {
            /*
            LOGO
            */
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();
            Image image1 = Image.getInstance(getClass().getResource("/logo.jpg"));
            image1.scaleToFit(400, 172);
            image1.setAbsolutePosition(160f, 650f);
            document.add(image1);
            
            //document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + data, FontFactory.getFont("font", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
            
            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n\n"));
            
            /*
            TABULAR
            */
            float[] columnWidths = {2, 4, 4, 6};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("ContactList"));
            cell.setBackgroundColor(GrayColor.GRAYWHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(4);
            table.addCell(cell);
            
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Counter");
            table.addCell("Last name");
            table.addCell("First name");
            table.addCell("Email");
            
            table.setHeaderRows(1);
            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
            for (int counter = 1; counter <= data.size(); counter++) {
                Person actualPerson = data.get(counter - 1);
                
                table.addCell(String.valueOf(counter));
                table.addCell(actualPerson.getLastName());
                table.addCell(actualPerson.getFirstName());
                table.addCell(actualPerson.getEmail());
            }
            
            document.add(table);
            
            /*
            SIGNATURE
            */
            Chunk signature = new Chunk("\n\n It generated with the help of the PhoneBook App.");
            Paragraph base = new Paragraph(signature);
            document.add(base);
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }
    
}
