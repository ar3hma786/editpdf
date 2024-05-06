package com.editpdf.edit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.editpdf.edit.dto.PDFRequest;
import com.editpdf.edit.service.PDFService;


@RestController
@RequestMapping("/api")
public class PDFController {

    @Autowired
    private PDFService pdfService;

    @PostMapping("/edit-pdf")
    public ResponseEntity<PDFResponse> editPDF(@RequestBody PDFRequest pdfRequest) {
        try {
            String editPDF = pdfService.editPDF(pdfRequest);
            return new ResponseEntity<>(new PDFResponse(editPDF), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new PDFResponse("Error editing PDF: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

class PDFResponse {

    private String result;

    public PDFResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}