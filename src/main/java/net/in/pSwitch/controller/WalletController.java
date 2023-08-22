package net.in.pSwitch.controller;

import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import net.in.pSwitch.model.Response;
import net.in.pSwitch.model.user.UserInfo;
import net.in.pSwitch.model.api.BankVerificationResponse;
import net.in.pSwitch.model.wallet.UserBankDetails;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.repository.UserBankDetailsRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.service.CashFreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class WalletController {

    Logger logger = LoggerFactory.getLogger(UserAccountController.class);
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private BinderService binderService;
    @Autowired
    private UserBankDetailsRepository userBankDetailsRepository;
    @Autowired
    private CashFreeService cashFreeService;

    @GetMapping("/walletBalance")
    public String getWalletBalance(@LoginUser LoginUserInfo loginUserInfo) {
        return "" + binderService.walletBalance(loginUserInfo);
    }

    @GetMapping("/verifyBankDetails")
    public Response<BankVerificationResponse> verifyBankDetails(@RequestParam(value = "accountNumber", required = true) String accountNumber,
                                                                @RequestParam(value = "ifscCode", required = true) String ifscCode,
                                                                @RequestParam(value = "accountType", required = true) String accountType, @LoginUser LoginUserInfo loginUserInfo) {
        Response response = cashFreeService.verifyBankDetails(accountNumber, ifscCode);
        if (!response.isError()) {
            BankVerificationResponse verification = (BankVerificationResponse) response.getResult();
            if (verification.getStatus() != null && verification.getStatus().equalsIgnoreCase("SUCCESS")
                    || verification.getAccountStatus() != null && verification.getAccountStatus().equalsIgnoreCase("VALID")) {
                UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);

                UserBankDetails bankDetails = new UserBankDetails();
                bankDetails.setAccountHolderName(verification.getData().getNameAtBank());
                bankDetails.setAccountNumber(accountNumber);
                bankDetails.setBankName(verification.getData().getBankName());
                bankDetails.setIfscCode(ifscCode);
                bankDetails.setAccountType(accountType);
                bankDetails.setUserInfo(userInfo);
                bankDetails.setCreatedBy(userInfo.getUserId());
                bankDetails.setUpdatedBy(userInfo.getUserId());
                userBankDetailsRepository.save(bankDetails);
            }
        }
        return response;
    }

}
