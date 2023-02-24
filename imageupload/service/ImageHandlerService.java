package com.example.imageupload.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.imageupload.entity.ImageAttachment;
import com.example.imageupload.errorHandaler.ResponseHandler;
import com.example.imageupload.errorHandaler.SuccessResponse;
import com.example.imageupload.repository.ImageUploadRepo;

@Service
public class ImageHandlerService implements ImageHandlerInterface {
	private String location = "D:/JavaTrainning/Imageupload/imageupload/imageupload/src/main/resources/static/image";
	@Autowired
	private ImageUploadRepo imageUploadRepo;

	@Override
	public String uploadImage(MultipartFile file) throws Exception {
		String msg;
		try {
			Files.copy(file.getInputStream(), Paths.get(location + File.separator + file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			ImageAttachment imageAttachment = new ImageAttachment();
			imageAttachment.setImagename(file.getOriginalFilename());
			imageAttachment.setImageType(file.getContentType());
			imageAttachment.setImagePath(location);
			imageUploadRepo.save(imageAttachment);
			msg = ResponseHandler.FILE_UPLOAD_SUCCESS_MSG;
		} catch (Exception e) {
			msg = ResponseHandler.FILE_UPLOAD_FAILED + " due to " + e.getMessage();
		}

		return msg;
	}

	@Override
	public ResponseEntity<?> getImage(String fileName) throws FileNotFoundException, Exception {

		String expectedPath = location + "/" + fileName;
		File file = new File(expectedPath);
		String base64File = null;
		FileInputStream istream = new FileInputStream(file);
		byte[] arr = new byte[(int) file.length()];
		istream.read(arr);
		istream.close();
		base64File = Base64.getEncoder().encodeToString(arr);
		return ResponseEntity.status(HttpStatus.OK).body(base64File);
	}
}