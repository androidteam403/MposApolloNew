package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionPdfScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model.EPrescriptionModelClassResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.EPrescriptionMedicineDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.EPrescriptionMedicineDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionMedicineResponse;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

public class EPrescriptionPdfActivity extends AppCompatActivity  {


    List<EPrescriptionModelClassResponse> prescriptionLineList;
    List<EPrescriptionMedicineResponse> medicineResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_prescription_pdf);



//        if (getIntent() != null) {
//            prescriptionLineList = (List<EPrescriptionModelClassResponse>) getIntent().getSerializableExtra("ePrescriptionList");
////            medicineResponseList = (List<EPrescriptionMedicineResponse>) getIntent().getSerializableExtra("medicineResponse");
//        }

//        try {
//            createPdf();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }



    }



//    private void createPdf()  throws FileNotFoundException {
//
//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//        File file  = new File(pdfPath, "myPDF.pdf");
//        OutputStream outputStream = new FileOutputStream(file);
//
//
//        PdfWriter writer = new PdfWriter(file);
//        PdfDocument pdfDocument = new PdfDocument(writer);
//        Document document = new Document(pdfDocument);
//
//        DeviceRgb invoiceGreen = new DeviceRgb(51, 204, 51);
//
//
////        Paragraph paragraph = new Paragraph("Hello PdfViewer");
////
////        Text text1  = new Text("Bold ").setBold();
////        Text text2  = new Text("Italic ").setItalic();
////        Text text3  = new Text("Underlined ").setUnderline();
////
////        Paragraph paragraph1 = new Paragraph();
////        paragraph1.add(text1)
////                .add(text2)
////                .add(text3);
////
////        document.add(paragraph);
////        document.add(paragraph1);
////
////        List list = new List();
////        list.add("Android");
////        list.add("Java");
////        document.add(list);
//
////        float columnWidth[] = {200f, 200f};
////        Table table = new Table(columnWidth);
////        table.addCell("Name");
////        table.addCell("Age");
////        table.addCell("Srivardhini");
////        table.addCell("32");
////        table.addCell("Murugula");
////        table.addCell("21");
//
//
//        float columnWidth[] ={140, 140, 140, 140};
//        Table table1 = new Table(columnWidth);
//
//        table1.addCell(new Cell(3, 1).add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell(1, 2).add(new Paragraph("Invoice").setFontSize(26f).setFontColor(invoiceGreen)).setBorder(Border.NO_BORDER));
////        table1.addCell(new Cell().add(new Paragraph("")));
//
//
////        table1.addCell(new Cell().add(new Paragraph("")));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("Invoice:")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("#23456789")).setBorder(Border.NO_BORDER));
//
//
////        table1.addCell(new Cell().add(new Paragraph("")));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("Invoice Date: ")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("22-06-2021")).setBorder(Border.NO_BORDER));
//
//
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("Account No: ")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("234234234")).setBorder(Border.NO_BORDER));
//
//
//        table1.addCell(new Cell().add(new Paragraph("\n")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//
//
//        table1.addCell(new Cell().add(new Paragraph("To")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//
//
//        table1.addCell(new Cell().add(new Paragraph("Srivardhini")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("Payment Method: ")).setBold().setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//
//
//
//        table1.addCell(new Cell().add(new Paragraph("523 Andheri west")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell(1, 2).add(new Paragraph("Paypal: payments@greenleaf.com")).setBorder(Border.NO_BORDER));
////        table1.addCell(new Cell().add(new Paragraph("")));
//
//
//        table1.addCell(new Cell().add(new Paragraph("Mumbai")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table1.addCell(new Cell(1, 2).add(new Paragraph("Card Payment: We accept Visa, MasterCard")).setBorder(Border.NO_BORDER));
////        table1.addCell(new Cell().add(new Paragraph("")));
//
//
//        float columnWidth2[] = {62, 162, 112, 112, 112};
//
//        Table table2 = new Table(columnWidth2);
//
//
//
//
//        table2.addCell(new Cell().add(new Paragraph("S.No")).setBackgroundColor(invoiceGreen).setFontColor(ColorConstants.WHITE));
//        table2.addCell(new Cell().add(new Paragraph("ITEM DESCRIPTIONS")).setBackgroundColor(invoiceGreen).setFontColor(ColorConstants.WHITE));
//        table2.addCell(new Cell().add(new Paragraph("RATE")).setBackgroundColor(invoiceGreen).setFontColor(ColorConstants.WHITE));
//        table2.addCell(new Cell().add(new Paragraph("QTY")).setBackgroundColor(invoiceGreen).setFontColor(ColorConstants.WHITE));
//        table2.addCell(new Cell().add(new Paragraph("PRICE")).setBackgroundColor(invoiceGreen).setFontColor(ColorConstants.WHITE));
//
//        table2.addCell(new Cell().add(new Paragraph("1")));
//        table2.addCell(new Cell().add(new Paragraph("MARIGOLD SEEDS")));
//        table2.addCell(new Cell().add(new Paragraph("50")));
//        table2.addCell(new Cell().add(new Paragraph("2")));
//        table2.addCell(new Cell().add(new Paragraph("100")));
//
//        table2.addCell(new Cell().add(new Paragraph("2")));
//        table2.addCell(new Cell().add(new Paragraph("FERTILIZER")));
//        table2.addCell(new Cell().add(new Paragraph("300")));
//        table2.addCell(new Cell().add(new Paragraph("2")));
//        table2.addCell(new Cell().add(new Paragraph("100")));
//
//        table2.addCell(new Cell().add(new Paragraph("3")));
//        table2.addCell(new Cell().add(new Paragraph("TOOL")));
//        table2.addCell(new Cell().add(new Paragraph("150")));
//        table2.addCell(new Cell().add(new Paragraph("1")));
//        table2.addCell(new Cell().add(new Paragraph("400")));
//
//        table2.addCell(new Cell().add(new Paragraph("4")));
//        table2.addCell(new Cell().add(new Paragraph("CACTUS PLANT")));
//        table2.addCell(new Cell().add(new Paragraph("400")));
//        table2.addCell(new Cell().add(new Paragraph("1")));
//        table2.addCell(new Cell().add(new Paragraph("400")));
//
//        table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table2.addCell(new Cell().add(new Paragraph("Sub-Total")));
//        table2.addCell(new Cell().add(new Paragraph("1100")));
//
//        table2.addCell(new Cell(1, 2).add(new Paragraph("Terms & Conditions: ")).setBorder(Border.NO_BORDER));
////        table2.addCell(new Cell().add(new Paragraph("")));
//        table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table2.addCell(new Cell().add(new Paragraph("GST (12%")));
//        table2.addCell(new Cell().add(new Paragraph("132")));
//
//        table2.addCell(new Cell(1, 2).add(new Paragraph("Goods sold are not returnable and exchangable")).setBorder(Border.NO_BORDER));
////        table2.addCell(new Cell().add(new Paragraph("")));
//        table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
//        table2.addCell(new Cell().add(new Paragraph("Grand Total")).setBold().setFontSize(16));
//        table2.addCell(new Cell().add(new Paragraph("1232")).setBold().setFontSize(16));
//
//
//
//
//        document.add(table1);
//        document.add(new Paragraph("\n"));
//        document.add(table2);
//        document.add(new Paragraph("\n\n\n\n\n\n (Authorised Signatory)\n\n\n").setTextAlignment(TextAlignment.RIGHT));
//
//        document.close();
//        Toast.makeText(getApplicationContext(), "Pdf Created", Toast.LENGTH_SHORT).show();
//    }
//    }
