package net.in.pSwitch.api;

import net.in.pSwitch.model.EmailDetails;
import net.in.pSwitch.model.LoginInfo;
import net.in.pSwitch.model.Response;
import net.in.pSwitch.model.user.UserInfo;
import net.in.pSwitch.model.request.AuthRequest;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.service.AuthService;
import net.in.pSwitch.service.EmailService;
import net.in.pSwitch.service.UtilServiceImpl;
import net.in.pSwitch.utility.LoginTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

//@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PublicApiController {

	@Autowired
	private EmailService emailService;

	@Autowired
	AuthService authService;
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private LoginTracker loginTracker;

	@Autowired
	private UtilServiceImpl utilService;


	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<Response<Map<String, Object>>> generateAuthenticationToken(@RequestBody AuthRequest authenticationRequest){
		Response<Map<String, Object>> response = new Response<>();
		try{
			response.setResult(authService.authenticate(authenticationRequest));
		} catch (DisabledException e) {
			response.setError(true);
			response.setMessage("User account is Disabled");
		} catch (BadCredentialsException e) {
			response.setError(true);
			response.setMessage("Invalid user Credentials");
		}catch (Exception e) {
			response.setError(true);
			response.setMessage(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@RequestMapping("/healthcheck")
	public ResponseEntity<String> healthcheck(){
		return ResponseEntity.status(HttpStatus.OK).body("Success");
	}

	@RequestMapping("/sendMail")
	public String sendMail(@RequestParam("body") final String body, @RequestParam("subject") final String subject)
	{
		EmailDetails details = new EmailDetails();
		details.setRecipient("tripathi251989@gmail.com");
		details.setSubject(subject);
		details.setMsgBody(body);
		String status = emailService.sendSimpleMail(details);
		return status;
	}

	@RequestMapping("/sendMailOTP")
	public String sendOTP(@RequestParam("userId") final Integer userId)
	{
		Optional<UserInfo> user = userInfoRepository.findById(userId);
		if(user.isPresent()) {
			UserInfo userInfo = user.get();
			userInfo.setVerificationCode(utilService.encodedData(utilService.generateOTP()));
			utilService.sendOTPMail(userInfo);
			return "Success";
		}
		return "Failed";
	}

	@RequestMapping("/getLoginDetails")
	public Page<LoginInfo> getLogin(@RequestParam("userId") final Integer userId) {
		return loginTracker.getAllLogin(userId);
	}
}
