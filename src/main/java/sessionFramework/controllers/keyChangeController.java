package sessionFramework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import sessionFramework.entity.SeUser;
import sessionFramework.requestBody.UserKeyChange;
import sessionFramework.requestBody.UserSessionFinalStep;
import sessionFramework.responseUser.ErrorResponse;
import sessionFramework.responseUser.OkResponse;
import sessionFramework.service.UserService;
import sessionFramework.utils.Encryptor;

@Controller
@RequestMapping("/keyChange")
public class keyChangeController {
	
	@Autowired
	UserService us;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<?> keyChangeGet(){
		return getObjectResponse("Estas en el get de cambiar clave, si quieres cambiar clave envia tus datos por un POST", HttpStatus.CONTINUE);
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<?> keyChange(
			@RequestBody UserKeyChange userKeyChange,
			HttpSession objSession
	){
		UserSessionFinalStep objUserSession = (UserSessionFinalStep) objSession.getAttribute(objSession.getId());
		if(objUserSession == null) {
			return getErrorResponse("No cuenta con una session valida", HttpStatus.UNAUTHORIZED);
		}
		
		boolean isNewPass = userKeyChange.getNewPass() instanceof String;
		boolean isPass = userKeyChange.getPass() instanceof String;
		if(!isNewPass || !isPass)
			return getErrorResponse("Los datos enviados no son validos o faltan datos", HttpStatus.BAD_REQUEST);
		String userName = objUserSession.getUser();
		SeUser userDB = us.getUserByName(userName);
		
		String oldPass = userDB.getPwUser();
		if(!Encryptor.compare(userKeyChange.getPass(), oldPass)){
			return getErrorResponse("La clave enviada es incorrecta, vuelva a enviar su clave actual", HttpStatus.BAD_REQUEST);
		}
		
		String hashNewPass = Encryptor.encrypt(userKeyChange.getNewPass());
		int modified = us.modifiedPass(hashNewPass, userName);
		if(modified <= 0)
			return getErrorResponse("Hubo un error al modificar su clave", HttpStatus.INTERNAL_SERVER_ERROR);
		
		objSession.invalidate();
		return getObjectResponse("Su clave fue cambiada con exito, la nueva clave se ha enviado a su correo. Se ha cerrado su sesion", HttpStatus.OK);
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
