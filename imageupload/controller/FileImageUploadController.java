package com.example.imageupload.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.imageupload.errorHandaler.ResponseHandler;

import com.example.imageupload.service.ImageHandlerService;

@RestController
public class FileImageUploadController {
	private static final Logger loc = LoggerFactory.getLogger(FileImageUploadController.class);

	@Autowired
	private ImageHandlerService imageHandlerService;

	/**
	 * This Method is used for to upload the Image
	 * 
	 * @param file name of the image
	 * @return response with message and HTTP status
	 * @throws Exception
	 */
	@PostMapping("/uploadFile")
	public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile files) throws Exception {
		if (files != null && !files.isEmpty()) {
			if (!files.getOriginalFilename().toLowerCase().endsWith(".png")) {
				return ResponseHandler.response(ResponseHandler.UNSUPPORTED_FILE_FORMAT, HttpStatus.BAD_REQUEST);
			}
			String statusMsg = imageHandlerService.uploadImage(files);
			if (statusMsg.contains(ResponseHandler.FILE_UPLOAD_FAILED)) {
				return ResponseHandler.response(statusMsg, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				return ResponseHandler.successResponse(statusMsg, HttpStatus.ACCEPTED);
			}

		} else {
			return ResponseHandler.response(ResponseHandler.FILE_NOT_UPLOADED_ERR_MSG, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This method is used converting the image to Base64 String format
	 * 
	 * @param filename
	 * @return response with message and HTTP status
	 */
	@GetMapping("/convertBase64")
	public ResponseEntity<?> convertBase64(@RequestParam("filename") String fileName) {
		try {
			if (fileName != null && !fileName.isEmpty()) {

				ResponseEntity<?> file = imageHandlerService.getImage(fileName);
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(file);
			}
		} catch (Exception e) {
			loc.info(fileName);
		}
		return ResponseHandler.response(ResponseHandler.FILE_NOT_FOUND, HttpStatus.BAD_REQUEST);
	}
}
