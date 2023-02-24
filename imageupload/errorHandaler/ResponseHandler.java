package com.example.imageupload.errorHandaler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
	public static final String INVALID_FILE_UPLOAD = "Uploaded file is not PDF";

	public static final String FILE_UPLOAD_SUCCESS_MSG = "File uploaded Sucessfully";

	public static final String FILE_UPLOAD_FAILED = "File upload failed";

	public static final String FILE_COPY_FAILED = "Transferring the files failed";

	public static final String BYTE_CONVERSION_FAILED = "Internal conversion failed";

	public static final String FILE_NOT_ATTACHED = "No Filename Attached";

	public static final String FILE_NOT_FOUND = "File not found in Database";

	public static final String FILE_ID_EMPTY = "File id is Null";

	public static final String FILE_RETRIEVED = "File retrieved Successfully";

	public static final String UNSUPPORTED_FILE_FORMAT = "Unsupported File Type. Only png format files are accepted";
	
	public static final String FILE_NOT_UPLOADED_ERR_MSG = "Could not process the file. Please upload the file.";

	public static ResponseEntity<?> response(String error, HttpStatus httpStatus) {
		ErrorHandlerResponse errorResponse = new ErrorHandlerResponse();
		if (httpStatus.value() == 400) {
			errorResponse.setError(error);
			return new ResponseEntity<Object>(errorResponse, httpStatus);
		} else if (httpStatus.value() == 401) {
			errorResponse.setError(error);
			return new ResponseEntity<Object>(errorResponse, httpStatus);
		} else if (httpStatus.value() == 404) {
			errorResponse.setError(error);
			return new ResponseEntity<Object>(errorResponse, httpStatus);
		} else {
			errorResponse.setError(error);
			return new ResponseEntity<Object>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public static ResponseEntity<?> successResponse(String status, HttpStatus http) {
		SuccessResponse successResponse = new SuccessResponse();
		if (http.value() == 200) {
			successResponse.setStatus(status);
			return new ResponseEntity<Object>(successResponse, http);
		} else if (http.value() == 202) {
			successResponse.setStatus(status);
			return new ResponseEntity<Object>(successResponse, http);
		}
		return new ResponseEntity<Object>(successResponse, HttpStatus.OK);

	}
}
