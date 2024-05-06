package com.editpdf.edit.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.editpdf.edit.dto.PDFRequest;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PDFServiceImpl implements PDFService {

    @Override
    public String editPDF(PDFRequest pdfRequest) {
        try {
            PdfReader pdfReader = new PdfReader(pdfRequest.getPdfPath());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfStamper pdfStamper = new PdfStamper(pdfReader, bos);

            Document document = new Document();
            PdfWriter writer = pdfStamper.getWriter();
            document.open();
            document.add(new Paragraph("Hello World!"));
            document.close();

            byte[] editedPdf = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(pdfRequest.getNewPdfPath());
            fos.write(editedPdf);
            fos.close();

            return "PDF edited successfully";
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            return "Error editing PDF";
        }
    }
}