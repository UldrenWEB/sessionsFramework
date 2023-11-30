package sessionFramework.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import sessionFramework.requestBody.UserSessionFinalStep;
import sessionFramework.requestBody.UserSessionFirstStep;
import sessionFramework.responseUser.ErrorResponse;
import sessionFramework.responseUser.OkResponse;

@Controller
@RequestMapping("/selectProfile")
public class SelectProfile {
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<?> chargeQuestions(
			HttpSession objSession
			){
		UserSessionFirstStep objUserSession = (UserSessionFirstStep) objSession.getAttribute(objSession.getId());
		if(objUserSession == null) {
			return getErrorResponse("No cuenta con una session valida", HttpStatus.UNAUTHORIZED);
		}
		List<Integer> profiles = objUserSession.getProfile();
		
		Map<Integer, String> profileMap = new HashMap<>();
		if(profiles.size() > 1) {
			//Aqui muestra los perfiles que tiene ese usuario
			for(Integer id: profiles) {
				profileMap.put(id, "admin");
			}
			return ResponseEntity.ok().body(profileMap);
		}
		
		UserSessionFinalStep userSession = new UserSessionFinalStep(objUserSession.getUser(), objUserSession.getEmail(), profiles.getFirst());
		objSession.setAttribute(objSession.getId(), userSession);
		return getObjectResponse("Cuenta con un solo perfil y ya se le fue asignado", HttpStatus.OK);
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
