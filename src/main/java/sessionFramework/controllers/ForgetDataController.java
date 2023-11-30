package sessionFramework.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sessionFramework.entity.Question;
import sessionFramework.entity.SeUser;
import sessionFramework.requestBody.ObjUser;
import sessionFramework.requestBody.UserResponseQuestion;
import sessionFramework.requestBody.UserSessionForgetData;
import sessionFramework.responseUser.ErrorResponse;
import sessionFramework.responseUser.OkResponse;
import sessionFramework.service.QuestionService;
import sessionFramework.service.UserService;
import sessionFramework.utils.Encryptor;
import sessionFramework.utils.SendEmail;

@Controller
@RequestMapping("forgetData")
public class ForgetDataController {

	@Value("${resetAttemp}")
	private int resetAttempNumber;
	
	@Autowired
	UserService us;
	@Autowired
	QuestionService qs;
	@Autowired
	SendEmail sendEmail;
	
	@GetMapping
	public ResponseEntity<?> getForgetData(){
		return getObjectResponse("Estas en el Get envia el usuario y response preguntas para obtener una nueva password", HttpStatus.OK);
	}
	
	@PostMapping
	public RedirectView forgetData(
			HttpSession objSession,
			HttpServletResponse response,
			@RequestBody ObjUser objUser
	){
		String name = objUser.getUser();
		boolean isCorrect = objUser.getUser() instanceof String;
		if(!isCorrect) {
			return redirect("/redirect?format", response);
			}
		
		SeUser userDB = us.getUserByName(name);			
		if(userDB == null) {
			return redirect("/redirect?typeResponse=user", response);
		}
		if(userDB.getBlUser())
			return redirect("/redirect?typeResponse=block", response);
		
		String emailUser = userDB.getEmUser();
		List<Question> questions = qs.getAllQuestionByUser(name);
		List<Integer> ids = new ArrayList<Integer>();
		for(Question q: questions) {
			ids.add(q.getId());
		}
//		System.out.println(ids);
		UserSessionForgetData userSession = new UserSessionForgetData(name,emailUser , ids);
		objSession.setAttribute(objSession.getId(), userSession);
		return redirect("/forgetData/questions", response);
	}
//	
	@GetMapping("/questions")
	@ResponseBody
	public ResponseEntity<?> viewQuestion(
			HttpSession objSession
	){
		UserSessionForgetData objUserSession = (UserSessionForgetData)objSession.getAttribute(objSession.getId());
		if(objUserSession == null) {
			return getErrorResponse("No cuenta con una session valida", HttpStatus.UNAUTHORIZED);
		}
		List<Integer> ids = objUserSession.getIdQuestions();
		Map<String, String> viewQuestion = new HashMap<String,String>();
		int cont = 0;
		for(Integer id: ids) {
//			System.out.println(id);
			cont++;
			viewQuestion.put("Pregunta "+cont, qs.getQuestionById(id).getDeQuestion());
		}
		return ResponseEntity.ok().body(viewQuestion);
	}
	
	@PostMapping("/questions")
	@ResponseBody
	public ResponseEntity<?> validateResponse(
			HttpSession objSession,
			@RequestBody UserResponseQuestion objUser
	){
		boolean isCorrectOne = objUser.getResp1() instanceof String;
		boolean isCorrectTwo = objUser.getResp2() instanceof String;
		
//		System.out.println(isCorrectOne+" "+isCorrectTwo);
		if(!isCorrectOne || !isCorrectTwo)
			return getErrorResponse("Las respuestas son requeridas, verifique", HttpStatus.BAD_REQUEST);
		
		UserSessionForgetData objUserSession = (UserSessionForgetData)objSession.getAttribute(objSession.getId());
		if(objUserSession == null) {
			return getErrorResponse("No cuenta con una session valida", HttpStatus.UNAUTHORIZED);
		}
		String userName = objUserSession.getUser();
		String emailUser = objUserSession.getEmail();
		SeUser userDB = us.getUserByName(userName);
		int attemps = userDB.getAtUser();
		
		if(attemps <= 0)
			us.blockUser(userName);
		
		boolean isBlock = userDB.getBlUser();
		if(isBlock)
			return getErrorResponse("Este usuario se encuentra bloqueado", HttpStatus.BAD_REQUEST);
		
		List<Integer> ids = objUserSession.getIdQuestions();
		Map<Integer, String> resp = new HashMap<Integer, String>();
		for(Integer id: ids) {
			resp.put(id, qs.getResponseByQuestion(id).getResQuestion());
		}
		String resp1 = objUser.getResp1().toLowerCase();
		String resp2 = objUser.getResp2().toLowerCase();
		int count = 0;
	    for (String val : resp.values()) {
	        if (Encryptor.compare(resp1, val) || Encryptor.compare(resp2, val)) {
	            count++;
	        }
	    }
	    if(count != 2) {
	    	us.reduceAttemps(userName);
	    	return getErrorResponse("Las respuestas enviadas no son correctas por favor verifique", HttpStatus.NOT_FOUND);
	    }
	    	    
		String newPass = getRandomPass(7);
		String hashNewPass = Encryptor.encrypt(newPass);
		us.resetAttemps(userName, resetAttempNumber);
		try {
			System.out.println("Esta es la nueva pass: "+ newPass);
			int modified = us.modifiedPass(hashNewPass, userName);
			if(modified <= 0)
				return getErrorResponse("Hubo un error al modificar su clave", HttpStatus.INTERNAL_SERVER_ERROR);
			
			sendEmail.sendEmail(emailUser, "Cambio de clave", newPass);
			return getObjectResponse("Se ha cambiado su clave de manera exitosa, la nueva clave fue enviada a su correo", HttpStatus.OK);
			
		}catch(Exception e) {
			System.out.println("Hubo un error con el envio del correo con la nueva clave: "+ e);
			return getErrorResponse("Hubo un error al enviar el correo con su nueva clave debido a fallas con el internet del server", HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	public String getRandomPass(int length) {
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();

		Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
	
	private RedirectView redirect(String url, HttpServletResponse response) {
		response.addHeader("Internal-Redirect", "verified");
		RedirectView r = new RedirectView();
		r.setUrl(url);
		return r;
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
