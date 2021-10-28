package com.fileupload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fileupload.helper.FileUploadHelper;

@RestController
@RequestMapping("/image")
public class FileUploadController {
	
	@Autowired
	private FileUploadHelper fileUploadHelper;

	@PostMapping("/upload-image")
	public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file){
		ResponseEntity<String> response = null;
		if(file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is not found");
		}
		
		System.out.println(file.getContentType());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		System.out.println(file.isEmpty());
		
		if(!file.getContentType().equals("image/jpeg")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File format must be JPEG");
		}
		boolean f=fileUploadHelper.fileUpload(file);
		if(f) {
			response= new ResponseEntity<String>(ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(file.getOriginalFilename()).toUriString(),HttpStatus.OK );
		
		}
		return response;
		
	}
	
}
