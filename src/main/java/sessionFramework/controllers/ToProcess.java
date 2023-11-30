package sessionFramework.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import sessionFramework.requestBody.UserSessionFinalStep;
import sessionFramework.requestBody.UserSessionForgetData;
import sessionFramework.requestBody.UserToProcess;
import sessionFramework.responseUser.ErrorResponse;
import sessionFramework.responseUser.OkResponse;
import sessionFramework.security.Security;
import sessionFramework.service.PermissionService;

@Controller
@RequestMapping("/toProcess")
public class ToProcess {
	@Autowired
	LoadMapPermissions lp;
	@Autowired
	PermissionService ps;
	
	Security instanceS = Security.getInstance();
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<?> seePermissions(
			HttpSession objSession,
    		@RequestBody UserToProcess objUser
    		){
		boolean isModule = objUser.getModule() instanceof String;
		boolean isObject = objUser.getObject() instanceof String;
		boolean isMethod = objUser.getMethod() instanceof String;
		
		if(!isModule || !isObject ||!isMethod) {
			return getErrorResponse("El nombre del modulo, objeto y metodo son requeridos para iniciar la solicitud de ejecucion", HttpStatus.BAD_REQUEST);
		}
		UserSessionFinalStep objUserSession = (UserSessionFinalStep)objSession.getAttribute(objSession.getId());
		if(objUserSession == null) {
			return getErrorResponse("No cuenta con una session valida", HttpStatus.BAD_REQUEST);
		}
		String module = objUser.getModule();
		String object = objUser.getObject();
		String method = objUser.getMethod();
		java.lang.Object[] params = objUser.getParams().toArray(new Object[0]);
		
		boolean response = instanceS.hasPermission(objUserSession.getProfile(), module, object, method);
		if(!response) {
			return getErrorResponse("No tiene permisos para ejecutar este metodo", HttpStatus.UNAUTHORIZED);
		}
		Object resultado = instanceS.executeMethod(module, object, method, params);
		
//		System.out.println("Hey aqui ejecutando la reflection:-> "+resultado);
		return getObjectResponse("Se ha ejecutado el metodo correctamente, la respuesta es: "+ resultado, HttpStatus.OK);
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
