package net.in.pSwitch.controller;

import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import net.in.pSwitch.model.FundRequest;
import net.in.pSwitch.model.MPIN;
import net.in.pSwitch.model.Status;
import net.in.pSwitch.model.Transaction;
import net.in.pSwitch.model.constant.TransactionType;
import net.in.pSwitch.model.UserInfo;
import net.in.pSwitch.model.UserWallet;
import net.in.pSwitch.repository.FundRequestRepository;
import net.in.pSwitch.repository.MPINRepository;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.repository.TransactionRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.repository.UserWalletRepository;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.service.UtilService;
import net.in.pSwitch.utility.SMSManager;
import net.in.pSwitch.utility.StringLiteral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/fund")
public class FundController {

	Logger logger = LoggerFactory.getLogger(UserAccountController.class);
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private FundRequestRepository fundRequestRepository;
	@Autowired
	private BinderService binderService;
	@Autowired
	private UtilService utilService;
	@Autowired
	private MPINRepository mpinRepository;
	@Autowired
	private UserWalletRepository userWalletRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private SMSManager smsManager;

	@RequestMapping("/manageFundRequest")
	public String manageFundRequest(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		UserInfo userInfo = (UserInfo) model.getAttribute("user");
		List<FundRequest> list = fundRequestRepository.findByParentOrderByCreatedTsDesc(userInfo);
		model.addAttribute("fundRequestList", list);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_FUND_REQUEST);
		return "fundRequest/manageFundRequestList";
	}

	@RequestMapping("/rejectFundRequest/{requestId}")
	public String enableProduct(Model model, @PathVariable("requestId") Integer requestId) {
		Optional<FundRequest> optional = fundRequestRepository.findById(requestId);
		if (optional != null) {
			FundRequest info = optional.get();
			info.setStatus(Status.REJECTED);
			fundRequestRepository.save(info);
		}
		return "redirect:/fund/manageFundRequest";
	}

	@RequestMapping("/acceptFundRequest/{requestId}")
	public String acceptFundRequest(Model model, @PathVariable("requestId") Integer requestId, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		Optional<FundRequest> optional = fundRequestRepository.findById(requestId);
		if (optional != null) {
			FundRequest fundRequest = optional.get();
			model.addAttribute("fundRequest", fundRequest);
		}
		return "fundRequest/fundRequestDetail";
	}

	@RequestMapping("/transferAmount")
	public String transferAmount(Model model, Integer id, String mpin, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		UserInfo userInfo = (UserInfo) model.getAttribute("user");

		boolean isValid = true;
		List<String> errorMessage = new ArrayList<>();

		FundRequest fundRequest = null;
		Optional<FundRequest> optional = fundRequestRepository.findById(id);
		if (optional != null) {
			fundRequest = optional.get();
		}

		if (fundRequest != null) {

			if (userInfo.getIsVerified() != null && 1l == userInfo.getIsVerified()
					&& userInfo.getContactVerified() != null && 1l == userInfo.getContactVerified()) {

				MPIN userMpin = mpinRepository.findByUser(userInfo);
				if (utilService.encodedData(mpin).equals(userMpin.getPin())) {

					UserWallet currentUserWallet = userWalletRepository.findByUser(userInfo);
					if (currentUserWallet != null
							&& currentUserWallet.getCurrentAmount() >= fundRequest.getTotalAmount()) {
						UserWallet userWallet = userWalletRepository.findByUser(fundRequest.getUser());

						if (userWallet != null) {
							userWallet.setOpeningAmount(userWallet.getCurrentAmount());
							userWallet.setCurrentAmount(userWallet.getCurrentAmount() + fundRequest.getTotalAmount());
							userWallet.setClosingAmount(fundRequest.getTotalAmount());
							userWalletRepository.save(userWallet);
						} else {
							userWallet = new UserWallet();
							userWallet.setCurrentAmount(fundRequest.getTotalAmount());
							userWallet.setUser(fundRequest.getUser());
							userWallet.setClosingAmount(fundRequest.getTotalAmount());
							userWallet.setOpeningAmount(0l);
							userWalletRepository.save(userWallet);
						}

						Transaction transaction = new Transaction();
						transaction.setToUser(fundRequest.getUser());
						transaction.setFromUser(userInfo);
						transaction.setAmount(fundRequest.getTotalAmount());
						transaction.setTransactionType(TransactionType.WALLET);
						transactionRepository.save(transaction);

						fundRequest.setStatus(Status.APPROVED);
						fundRequestRepository.save(fundRequest);
					} else {
						isValid = false;
						errorMessage.add(
								"Insufficient ammount in your wallet, Please add amount in your wallet before any transaction.");
					}
				} else {
					isValid = false;
					errorMessage.add("Invalid MPIN");
				}
			} else {
				isValid = false;
				errorMessage.add(
						"your account is not completed and not verified, Please verified account before any transaction");
			}
		} else {
			isValid = false;
			errorMessage.add("Invalid fund request");
		}

		if (!isValid) {
			model.addAttribute("updateError", errorMessage);
		} else {
			return "redirect:/fund/manageFundRequest";
		}
		model.addAttribute("fundRequest", fundRequest);
		return "fundRequest/fundRequestDetail";
	}

	@RequestMapping("/generateOTP")
	@ResponseBody
	public String generateOTP(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);

		UserInfo userInfo = (UserInfo) model.getAttribute("user");
		if (userInfo.getMobileNumber() != null) {
			userInfo.setContactOTP(utilService.encodedData(smsManager.sendOTP(userInfo.getMobileNumber().trim())));
			userInfoRepository.save(userInfo);
		}
		return "success";
	}
}