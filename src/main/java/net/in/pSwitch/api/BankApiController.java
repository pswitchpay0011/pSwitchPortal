package net.in.pSwitch.api;

import net.in.pSwitch.model.UserInfo;
import net.in.pSwitch.model.database.CDMConfirmation;
import net.in.pSwitch.model.database.ConfirmTransaction;
import net.in.pSwitch.model.database.TransactionValidation;
import net.in.pSwitch.model.request.AuthRequest;
import net.in.pSwitch.model.request.CDMConfirmationRequest;
import net.in.pSwitch.model.request.ConfirmTransactionRequest;
import net.in.pSwitch.model.request.TokenRequest;
import net.in.pSwitch.model.request.TransactionValidationRequest;
import net.in.pSwitch.model.response.CDMConfirmationResponse;
import net.in.pSwitch.model.response.ConfirmTransactionResponse;
import net.in.pSwitch.model.response.TokenResponse;
import net.in.pSwitch.model.response.TransactionValidationResponse;
import net.in.pSwitch.repository.CDMConfirmationRepository;
import net.in.pSwitch.repository.ConfirmTransactionRepository;
import net.in.pSwitch.repository.TransactionValidationRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api")
public class BankApiController {
	Logger logger = LoggerFactory.getLogger(BankApiController.class);

	static final String CUSTOM_PATTERN = "YYYY-MM-DD HH:mm:ss";
	static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(CUSTOM_PATTERN);

	@Autowired
	private AuthService authService;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private CDMConfirmationRepository cdmConfirmationRepository;
	@Autowired
	private TransactionValidationRepository transactionValidationRepository;
	@Autowired
	private ConfirmTransactionRepository confirmTransactionRepository;

	@PostMapping(value = "/token")
	public ResponseEntity<?> generateAuthenticationToken(@RequestBody TokenRequest tokenRequest){
		TokenResponse response = new TokenResponse();
		try{
			AuthRequest authenticationRequest = new AuthRequest();
			authenticationRequest.setPassword(tokenRequest.getPassword());
			authenticationRequest.setUsername(tokenRequest.getUserName());

			Map data = authService.authenticate(authenticationRequest);
			Long exp = (Long) data.get("exp");
			String token = (String) data.get("token");
			response.setToken(token);
			response.setExpiration(Instant.ofEpochMilli(exp*1000).atZone(ZoneId.systemDefault()).toLocalDateTime().toString());
		} catch (DisabledException e) {
			logger.error("Error: {}", e);
			response.setError(true);
			response.setMessage("User account is Disabled");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		} catch (BadCredentialsException e) {
			logger.error("Error: {}", e);
			response.setError(true);
			response.setMessage("Invalid user Credentials");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}catch (Exception e) {
			logger.error("Error: {}", e);
			response.setError(true);
			response.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping(value = "/confirmTCDMTransaction")
	public ResponseEntity<?> confirmTCDMTransaction(@RequestBody CDMConfirmationRequest cdmConfirmation) {
		CDMConfirmationResponse response = new CDMConfirmationResponse();

//		try{
			CDMConfirmation cdm = new CDMConfirmation();
			cdm.setAppLUserID(cdmConfirmation.getApplUserID());
			cdm.setUtr(cdmConfirmation.getUtr());
			cdm.setCDMCardNo(cdmConfirmation.getCDMCardNo());
			cdm.setAgentId(cdmConfirmation.getAgentId());
			cdm.setTxnAmount(cdmConfirmation.getTxnAmnt());
			cdm.setReqDtTime(cdmConfirmation.getReqDtTime());
			cdm.setTxnNumber(cdmConfirmation.getTxnNmbr());
			cdm.setPMode(cdmConfirmation.getPmode());
			cdm.setCorpCode(cdm.getCorpCode());

			response.setTxnid(cdmConfirmation.getUtr());
			response.setApplUserID(cdmConfirmation.getApplUserID());

		if(StringUtils.isEmpty(cdmConfirmation.getCorpCode())){
			response.setStatus("1");
			response.setMessage("Invalid client code");
		}else if(cdmConfirmation.getCorpCode().equalsIgnoreCase("PSPL")) {
			if(cdmConfirmationRepository.findByUTR(cdmConfirmation.getUtr())==null) {
				cdmConfirmationRepository.save(cdm);
				response.setStatus("0");
				response.setMessage("Account credited");
			}else{
				response.setStatus("1");
				response.setMessage("Duplicate UTR");
			}
		}else{
			response.setStatus("1");
			response.setMessage("Incorrect client code");
		}

//		}catch (NullPointerException e) {
//			response.setStatus("1");
//			response.setMessage("Duplicate UTR");
//		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}



	@PostMapping(value = "/confirmTransaction")
	public ResponseEntity<?> confirmTransaction(@RequestBody ConfirmTransactionRequest confirmTransactionRequest){
		ConfirmTransactionResponse response = new ConfirmTransactionResponse();

		ConfirmTransaction confirmTransaction = new ConfirmTransaction();
		confirmTransaction.setUtr(confirmTransactionRequest.getUtr());
		confirmTransaction.setBeneAccNo(confirmTransactionRequest.getBeneAccNo());
		confirmTransaction.setReqType(confirmTransactionRequest.getReqType());

		confirmTransaction.setReqDtTime(confirmTransactionRequest.getReqDtTime());
		confirmTransaction.setTxnAmnt(confirmTransactionRequest.getTxnAmnt());
		confirmTransaction.setReqDtTime(confirmTransactionRequest.getReqDtTime());
		confirmTransaction.setCorpCode(confirmTransactionRequest.getCorpCode());
		confirmTransaction.setPmode(confirmTransactionRequest.getPmode());

		confirmTransaction.setSndrAcnt(confirmTransactionRequest.getSndrAcnt());
		confirmTransaction.setSndrNm(confirmTransactionRequest.getSndrNm());
		confirmTransaction.setSndrAcnt1(confirmTransactionRequest.getSndrAcnt1());
		confirmTransaction.setSndrNm1(confirmTransactionRequest.getSndrNm1());
		confirmTransaction.setSndrIfsc(confirmTransactionRequest.getSndrIfsc());
		confirmTransaction.setTranId(confirmTransactionRequest.getTranId());

		if(confirmTransactionRepository.findByUtr(confirmTransactionRequest.getUtr())==null) {
			confirmTransactionRepository.save(confirmTransaction);
			response.setErrCd("000");
			response.setSttsFlg("S");
			response.setMessage("Account credited");
		}else{
			response.setErrCd("004");
			response.setSttsFlg("F");
			response.setMessage("Duplicate UTR");
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping(value = "/validateTransaction")
	public ResponseEntity<?> validateTransaction(@RequestBody TransactionValidationRequest transactionValidationRequest){
		TransactionValidationResponse response = new TransactionValidationResponse();

		TransactionValidation transactionValidation = new TransactionValidation();
		transactionValidation.setUtr(transactionValidationRequest.getUtr());
		transactionValidation.setBeneAccNo(transactionValidationRequest.getBeneAccNo());
		transactionValidation.setReqType(transactionValidationRequest.getReqType());

		transactionValidation.setReqDtTime(transactionValidationRequest.getReqDtTime());
		transactionValidation.setTxnAmount(transactionValidationRequest.getTxnAmnt());
		transactionValidation.setReqDtTime(transactionValidationRequest.getReqDtTime());
		transactionValidation.setCorpCode(transactionValidationRequest.getCorpCode());
		transactionValidation.setPMode(transactionValidationRequest.getPmode());

		transactionValidation.setSndrAcnt(transactionValidationRequest.getSndrAcnt());
		transactionValidation.setSndrNm(transactionValidationRequest.getSndrNm());
		transactionValidation.setSndrAcnt1(transactionValidationRequest.getSndrAcnt1());
		transactionValidation.setSndrNm1(transactionValidationRequest.getSndrNm1());
		transactionValidation.setSndrIfsc(transactionValidationRequest.getSndrIfsc());

		if(StringUtils.isEmpty(transactionValidationRequest.getUtr()) || !isAlphanumeric(transactionValidationRequest.getUtr())){
			response.setErrCd("001");
			response.setSttsFlg("F");
			response.setMessage("Invalid UTR Number");
		}else if(!isValidAccount(transactionValidationRequest.getBeneAccNo())){
			response.setErrCd("001");
			response.setSttsFlg("F");
			response.setMessage("Invalid Bene Acc Number");
		}else if(transactionValidationRequest.getReqDtTime()==null || isValidLocalDate(transactionValidationRequest.getReqDtTime())==null){
			response.setErrCd("001");
			response.setSttsFlg("F");
			response.setMessage("Invalid Request DateTime");
		}else if(transactionValidationRequest.getTxnAmnt()==null || !isValidAmount(transactionValidationRequest.getTxnAmnt())){
			response.setErrCd("001");
			response.setSttsFlg("F");
			response.setMessage("Invalid Tran Amount");
		}else if(StringUtils.isEmpty(transactionValidationRequest.getCorpCode())){
			response.setErrCd("001");
			response.setSttsFlg("F");
			response.setMessage("Invalid client code");
		}else if(transactionValidationRequest.getPmode().equalsIgnoreCase("NEFT") && ( StringUtils.isEmpty(transactionValidationRequest.getSndrNm()) ||
				StringUtils.isEmpty(transactionValidationRequest.getSndrAcnt()))){
			response.setErrCd("001");
			response.setSttsFlg("F");
			response.setMessage("Invalid Sender Acct/Name");
		}else if(transactionValidationRequest.getPmode().equalsIgnoreCase("IMPS") && ( StringUtils.isEmpty(transactionValidationRequest.getSndrNm1()) ||
				StringUtils.isEmpty(transactionValidationRequest.getSndrAcnt1()))){
			response.setErrCd("001");
			response.setSttsFlg("F");
			response.setMessage("Invalid Sender Acct/Name");
		}else if(transactionValidationRequest.getSndrIfsc()==null || !isValidIFSC(transactionValidationRequest.getSndrIfsc())){
			response.setErrCd("001");
			response.setSttsFlg("F");
			response.setMessage("Invalid Sender Ifsc code");
		}else if(transactionValidationRequest.getCorpCode().equalsIgnoreCase("PSPL")) {

			if (transactionValidationRepository.findByUtr(transactionValidationRequest.getUtr()) == null) {
				transactionValidationRepository.save(transactionValidation);
				response.setErrCd("000");
				response.setSttsFlg("S");
				response.setMessage("Success");
			} else {
				response.setErrCd("004");
				response.setSttsFlg("F");
				response.setMessage("Duplicate UTR");
			}
		}else{
			response.setErrCd("002");
			response.setSttsFlg("F");
			response.setMessage("Incorrect client code");
		}


		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	private boolean isValidAccount(String beneAccNo) {
		if(!beneAccNo.toLowerCase().startsWith("pspl"))
			return false;
		if (beneAccNo.length()<20)
			return false;

		String mobNo = beneAccNo.substring(4, 14);
		String userId = beneAccNo.substring(14);

		if(!userId.chars().allMatch(Character::isDigit) || !mobNo.chars().allMatch(Character::isDigit)){
			return false;
		}
		Optional<UserInfo> optional = userInfoRepository.findById(Integer.parseInt(userId));
		if(!optional.isPresent()){
			return false;
		}else if(!optional.get().getMobileNumber().equals(mobNo)){
			return false;
		}

		return true;
	}

	public Date isValidLocalDate(String dateStr) {

		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
		}catch (Exception e) {
		}
		return date;
	}

	public boolean isValidAmount(String amt){
		final String regExp = "[0-9]+([.][0-9]{1,2})?";
		return amt.matches(regExp);
	}

	public boolean isValidIFSC(String ifsc){
		final String regExp = "^[A-Z]{4}0[A-Z0-9]{6}$";
		return ifsc.matches(regExp);
	}

	private final String ALPHANUMERIC_PATTERN = "^[a-zA-Z0-9-]+$";

	public boolean isAlphanumeric(final String input) {
		return input.matches(ALPHANUMERIC_PATTERN);
	}
}
