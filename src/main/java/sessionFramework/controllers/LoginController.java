package sessionFramework.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sessionFramework.entity.SeUser;
import sessionFramework.requestBody.ObjUser;
import sessionFramework.requestBody.UserSessionFinalStep;
import sessionFramework.requestBody.UserSessionFirstStep;
import sessionFramework.responseUser.ErrorResponse;
import sessionFramework.responseUser.OkResponse;
import sessionFramework.service.UserProfileService;
import sessionFramework.service.UserService;
import sessionFramework.utils.Encryptor;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Value("${resetAttemp}")
	private int resetAttempNumber;
	
	@Autowired
	UserService us;
	@Autowired
	UserProfileService ups;
	
	@PostMapping
    public RedirectView handlePostRequest(
    		HttpSession objSession,
    		@RequestBody ObjUser objUser,
    		HttpServletResponse response
    		) 
	{
		ObjUser userData = objUser;
		boolean isPassCorrect = userData.getPass() instanceof String;
		boolean isUserCorrect = userData.getUser() instanceof String;
//		System.out.println(""+isPassCorrect+" "+isUserCorrect);
		
		if(!isPassCorrect || !isUserCorrect) {
			return  redirect("/redirect?typeResponse=format", response);
		}
		String name = userData.getUser();
		String pass = userData.getPass();
		
		SeUser userDB = us.getUserByName(name);
		if(userDB == null) {
			return redirect("/redirect?typeResponse=user", response);
		}
		int attemps = userDB.getAtUser();
//		System.out.println("Aqui intentos "+attemps);
		
		if(attemps <= 0)
			us.blockUser(name);

		
		boolean isBlock = userDB.getBlUser();
		
		if(isBlock)
			return redirect("/redirect?typeResponse=block", response);
		
		if(!Encryptor.compare(pass, userDB.getPwUser())) {
			us.reduceAttemps(name);
			return redirect("/redirect?typeResponse=password", response);
		}
		us.resetAttemps(name, resetAttempNumber);
		List<Integer> profiles = ups.getProfileByUser(name);
		UserSessionFirstStep userSession = new UserSessionFirstStep(name, userDB.getEmUser(), profiles);
		objSession.setAttribute(objSession.getId(), userSession);	

		return redirect("/selectProfile", response);
//		return new ResponseEntity<>(getObjectResponse("Cuenta con un solo perfil el cual se le asigno"), HttpStatus.OK);
        
	}
	
	private RedirectView redirect(String url, HttpServletResponse response) {
		response.addHeader("Internal-Redirect", "verified");
		RedirectView r = new RedirectView();
		r.setUrl(url);
		return r;
	}
}
