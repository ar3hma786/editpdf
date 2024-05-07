package com.editpdf.edit.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import com.editpdf.edit.dto.WordRequest;
import com.editpdf.edit.dto.WordResponse;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WordServiceImpl implements WordService {

    private static final int EMAILS_PER_LINE = 100;
    private static int EMAILS_BATCH = 1;

    @Override
    public WordResponse addCommaAfterEmails(WordRequest request) {
        WordResponse response = new WordResponse();
        try (FileInputStream fis = new FileInputStream(request.getInputFile());
             XWPFDocument document = new XWPFDocument(fis)) {

            int emailCount = 0;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String paragraphText = paragraph.getText();
                if (paragraphText != null && paragraphText.contains("@")) {
                    // Split the paragraph text by space and add comma after email addresses
                    String[] words = paragraphText.split("\\s+");
                    StringBuilder modifiedText = new StringBuilder();
                    for (String word : words) {
                        if (word.contains("@")) {
                            // Add comma after email address
                            modifiedText.append(word).append(",\n");
                            emailCount++;
                            // Insert a new line if the counter reaches 400
                            if (emailCount % EMAILS_PER_LINE == 0) {
                                modifiedText.append("\n");
                            }
                        } else {
                            modifiedText.append(word).append(" ");
                        }
                    }
                    // Update the paragraph text
                    for (int i = 0; i < paragraph.getRuns().size(); i++) {
                        XWPFRun run = paragraph.getRuns().get(i);
                        if (i == paragraph.getRuns().size() - 1) {
                            run.setText(modifiedText.toString().trim(), 0);
                        } else {
                            run.setText(modifiedText.toString().trim() + " ", 0);
                        }
                    }
                }
            }

            try (FileOutputStream fos = new FileOutputStream(request.getOutputFile())) {
                document.write(fos);
                response.setMessage("Comma added after email addresses in the Word file.");
            } catch (IOException e) {
                response.setMessage("Error writing to the output Word file: " + e.getMessage());
            }

        } catch (IOException e) {
            response.setMessage("Error opening or reading the input Word file: " + e.getMessage());
        }
        return response;
    }

    @Override
    public WordResponse addSpaceAfterEvery400Emails(WordRequest request) {
        WordResponse response = new WordResponse();
        try (FileInputStream fis = new FileInputStream(request.getInputFile());
             XWPFDocument document = new XWPFDocument(fis)) {

            int emailCount = 0;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String paragraphText = paragraph.getText();
                if (paragraphText != null && paragraphText.contains("@")) {
                    // Split the paragraph text by space and add space after every 400 emails
                    String[] words = paragraphText.split("\\s+");
                    StringBuilder modifiedText = new StringBuilder();
                    for (String word : words) {
                        if (word.contains("@")) {
                            // Add comma after email address
                            modifiedText.append(word).append("\n");
                            emailCount++;
                            // Insert a new line if the counter reaches 400
                            if (emailCount % EMAILS_PER_LINE == 0) {
                                modifiedText.append("\n----------------------------------------------------------------------DONE FOR TODAY------------------------ " + "  " + EMAILS_BATCH++ + " " + " ---------------------------------------------\n ");
                                
                            }
                        } else {
                            modifiedText.append(word).append(" ");
                        }
                    }
                    // Update the paragraph text
                    for (int i = 0; i < paragraph.getRuns().size(); i++) {
                        XWPFRun run = paragraph.getRuns().get(i);
                        if (i == paragraph.getRuns().size() - 1) {
                            run.setText(modifiedText.toString().trim(), 0);
                        } else {
                            run.setText(modifiedText.toString().trim() + " ", 0);
                        }
                    }
                }
            }

            try (FileOutputStream fos = new FileOutputStream(request.getOutputFile())) {
                document.write(fos);
                response.setMessage("Space added after every 400 emails in the Word file.");
            } catch (IOException e) {
                response.setMessage("Error writing to the output Word file: " + e.getMessage());
            }

        } catch (IOException e) {
            response.setMessage("Error opening or reading the input Word file: " + e.getMessage());
        }
        return response;
    }

    @Override
    public WordResponse removeRepeatingEmails(WordRequest request) {
        WordResponse response = new WordResponse();
        
        try (FileInputStream fis = new FileInputStream(request.getInputFile());
             XWPFDocument document = new XWPFDocument(fis)) {

            Set<String> uniqueEmails = new HashSet<>();
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            if (paragraphs != null && !paragraphs.isEmpty()) {
                for (XWPFParagraph paragraph : paragraphs) {
                    StringBuilder modifiedText = new StringBuilder();

                    String paragraphText = paragraph.getText();
                    if (paragraphText != null && paragraphText.contains("@")) {
                        String[] words = paragraphText.split("\\s+");
                        for (String word : words) {
                            if (word.contains("@")) {
                                // Check if the email address is unique
                                if (!uniqueEmails.contains(word)) {
                                    modifiedText.append(word).append(",\n");
                                    uniqueEmails.add(word);
                                }
                            } else {
                                modifiedText.append(word).append(" ");
                            }
                        }
                        // Update the paragraph text
                        List<XWPFRun> runs = paragraph.getRuns();
                        if (runs != null && !runs.isEmpty()) {
                            for (XWPFRun run : runs) {
                                run.setText(modifiedText.toString().trim(), 0);
                            }
                        }
                    }
                }
            }

            try (FileOutputStream fos = new FileOutputStream(request.getOutputFile())) {
                document.write(fos);
                response.setMessage("Repeated emails removed from the Word file.");
            } catch (IOException e) {
                response.setMessage("Error writing to the output Word file: " + e.getMessage());
            }

        } catch (IOException e) {
            response.setMessage("Error opening or reading the input Word file: " + e.getMessage());
        } catch (Exception e) {
            response.setMessage("An unexpected error occurred: " + e.getMessage());
        }
        return response;
    }

}
