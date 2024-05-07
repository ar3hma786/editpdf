package com.editpdf.edit.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.editpdf.edit.dto.WordRequest;
import com.editpdf.edit.dto.WordResponse;
import com.editpdf.edit.service.WordService;

@RestController
public class WordController {

    private final WordService wordEditService;

    public WordController(WordService wordEditService) {
        this.wordEditService = wordEditService;
    }

    @PostMapping("/editWord")
    public WordResponse editWord(@RequestBody WordRequest request) {
        return wordEditService.addCommaAfterEmails(request);
    }
    
    @PostMapping("/addSpace")
    public WordResponse addSpaceAfterEvery400Emails(@RequestBody WordRequest request) {
        return wordEditService.addSpaceAfterEvery400Emails(request);
    }
    
    @PostMapping("/removeRepeatingEmails")
    public WordResponse removeRepeatingEmails(WordRequest request)
    {
    	return wordEditService.removeRepeatingEmails(request);
    }
}
