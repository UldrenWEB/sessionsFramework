package sessionFramework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sessionFramework.requestBody.UserSessionFinalStep;
import sessionFramework.responseUser.ErrorResponse;
import sessionFramework.responseUser.OkResponse;

@Controller
@RequestMapping("/logout")
public class LogautController {

	@GetMapping
	@PostMapping
	public ResponseEntity<?> destroySession(HttpSession objSession) {
		
		UserSessionFinalStep objUserSession = (UserSessionFinalStep) objSession.getAttribute(objSession.getId());
		if(objUserSession == null) {
			return getErrorResponse("No cuenta con una session valida", HttpStatus.UNAUTHORIZED);
		}
		
		objSession.invalidate();
		return getObjectResponse("Se cerro la session correctamente", HttpStatus.OK);
	}
	
	public ResponseEntity<?> getObjectResponse(String message, HttpStatus status) {
		OkResponse response = new OkResponse(message);
		return new ResponseEntity<>(response, status);
	}
	
	public ResponseEntity<?> getErrorResponse(String error, HttpStatus status) {
		ErrorResponse errorResponse = new ErrorResponse(error);
		return new ResponseEntity<>(errorResponse, status);
	}
	
}
