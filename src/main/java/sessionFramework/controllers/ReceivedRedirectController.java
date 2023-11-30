package sessionFramework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import sessionFramework.responseUser.ErrorResponse;
import sessionFramework.responseUser.OkResponse;

@Controller
@RequestMapping("/redirect")
public class ReceivedRedirectController {
	
	
	@GetMapping
	public ResponseEntity<?> detectResponse(
			@RequestParam String typeResponse,
			HttpServletRequest request
	){
//		String valor = request.getHeader("Internal-Redirect");
//		if(valor != "verified")
//			return getErrorResponse("No tiene permisos para entrar a este EndPoint "+valor, HttpStatus.UNAUTHORIZED);
		
		if(typeResponse.equals("session")) {
			return getErrorResponse("No cuenta con una session valida", HttpStatus.UNAUTHORIZED);
		}
		
		if(typeResponse.equals("password")) {
			return getErrorResponse("La password ingresada es incorrecta", HttpStatus.BAD_REQUEST);
		}
		
		if(typeResponse.equals("format")) {
			return getErrorResponse("Hubo un error al enviar sus datos verifique",HttpStatus.BAD_REQUEST);
		}
		if(typeResponse.equals("user")) {
			return getErrorResponse("El usuario ingresado no existe, vuelva a intertarlo", HttpStatus.NOT_FOUND);
		}
		
		return getObjectResponse("Hubo un error", HttpStatus.BAD_REQUEST);
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
