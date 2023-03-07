package net.in.pSwitch.api;

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
import net.in.pSwitch.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api")
public class BankApiController {
	Logger logger = LoggerFactory.getLogger(BankApiController.class);

	@Autowired
	private AuthService authService;

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

			response.setTxnid(cdmConfirmation.getUtr());
			response.setApplUserID(cdmConfirmation.getApplUserID());

			if(cdmConfirmationRepository.findByUTR(cdmConfirmation.getUtr())==null) {
				cdmConfirmationRepository.save(cdm);
				response.setStatus("0");
				response.setMessage("Account credited");
			}else{
				response.setStatus("1");
				response.setMessage("Duplicate UTR");
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
			response.setMessage("Account credited");
		}else{
			response.setErrCd("004");
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


		if(transactionValidationRepository.findByUtr(transactionValidationRequest.getUtr())==null) {
			transactionValidationRepository.save(transactionValidation);
			response.setErrCd("000");
			response.setMessage("Success");
		}else{
			response.setErrCd("004");
			response.setMessage("Duplicate UTR");
		}


		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
