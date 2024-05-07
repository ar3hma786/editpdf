package com.editpdf.edit.service;

import com.editpdf.edit.dto.WordRequest;
import com.editpdf.edit.dto.WordResponse;

//Service interface
public interface WordService {
	public WordResponse addCommaAfterEmails(WordRequest request);
	
	public WordResponse addSpaceAfterEvery400Emails(WordRequest request);
	
	public WordResponse removeRepeatingEmails(WordRequest request);
}
