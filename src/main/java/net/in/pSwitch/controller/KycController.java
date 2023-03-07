package net.in.pSwitch.controller;

import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import net.in.pSwitch.dto.KycDetails;
import net.in.pSwitch.dto.UserBankDetailsDTO;
import net.in.pSwitch.model.BusinessDetails;
import net.in.pSwitch.model.City;
import net.in.pSwitch.model.MccType;
import net.in.pSwitch.model.Response;
import net.in.pSwitch.model.ShopType;
import net.in.pSwitch.model.States;
import net.in.pSwitch.model.UserBankDetails;
import net.in.pSwitch.model.UserInfo;
import net.in.pSwitch.model.api.AadhaarOTPResponse;
import net.in.pSwitch.model.api.AadhaarVerification;
import net.in.pSwitch.model.api.BankVerificationResponse;
import net.in.pSwitch.model.api.PanCardVerification;
import net.in.pSwitch.model.api.PostOffice;
import net.in.pSwitch.model.api.PostalPincode;
import net.in.pSwitch.repository.BankListRepository;
import net.in.pSwitch.repository.BusinessDetailsRepository;
import net.in.pSwitch.repository.CityRepository;
import net.in.pSwitch.repository.MCCRepository;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.repository.ShopTypeRepository;
import net.in.pSwitch.repository.StatesRepository;
import net.in.pSwitch.repository.UserBankDetailsRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.repository.UserWalletRepository;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.service.CashFreeService;
import net.in.pSwitch.service.UtilServiceImpl;
import net.in.pSwitch.utility.StringLiteral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/kyc")
public class KycController {

    Logger logger = LoggerFactory.getLogger(KycController.class);
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserBankDetailsRepository userBankDetailsRepository;
    @Autowired
    private BinderService binderService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private StatesRepository statesRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ShopTypeRepository shopTypeRepository;
    @Autowired
    private MCCRepository mccRepository;
    @Autowired
    private UtilServiceImpl utilService;
    @Autowired
    private BusinessDetailsRepository businessDetailsRepository;
    @Autowired
    private CashFreeService cashFreeService;
    @Autowired
    private BankListRepository bankListRepository;
    @Autowired
    private UserWalletRepository userWalletRepository;


    @GetMapping({"/", ""})
    public String kyc(Model model, @LoginUser LoginUserInfo loginUserInfo) {
        try {
            model = binderService.bindUserDetails(model,loginUserInfo);
            UserInfo userInfo = (UserInfo) model.getAttribute("user");
            String kycStep = "";
            boolean isBD = false;
            if (userInfo.getBusinessDetails() == null) {
                kycStep = "0";
                isBD = true;
            } else if (StringUtils.isEmpty(userInfo.getAadhaarNumber())) {
                kycStep = "1";
            } else if (StringUtils.isEmpty(userInfo.getPancardNumber())) {
                kycStep = "2";
            } else if (userInfo.getAgreementAccept() == null || userInfo.getESignAgreement() == null || userInfo.getAgreementAccept() == 0 || userInfo.getESignAgreement() == 0) {
                kycStep = "3";
            } else if (userInfo.getBankDetails() == null) {
                kycStep = "4";
                model.addAttribute("bankList", bankListRepository.findAll(Sort.by(Sort.Direction.ASC, "bankName")));
            }
            if (kycStep.isEmpty()) {
                userInfo.setKycCompleted(true);
                userInfo.setAccountState(StringLiteral.ACCOUNT_CREATION_STATE_STEP_5);
                userInfoRepository.save(userInfo);
//            userInfoRepository.updateAccountState(userInfo.getUserId(),StringLiteral.ACCOUNT_CREATION_STATE_STEP_5, LocalDateTime.now());
                return "redirect:/index";
            }
            if (isBD) {
                model.addAttribute("states", statesRepository.findByCountryIdOrderByNameAsc(101l));
                model.addAttribute("cities", cityRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
                model.addAttribute("typeOfMCC", mccRepository.findAllOrderByMccType());
                Map<String, List<ShopType>> listOfShops = shopTypeRepository.findAll().stream()
                        .sorted(Comparator.comparing(ShopType::getIndustry))
                        .collect(Collectors.groupingBy(ShopType::getBusinessType));
                model.addAttribute("typeOfShops", listOfShops);
            } else {
                model.addAttribute("states", statesRepository.findAllById(Arrays.asList(userInfo.getBusinessDetails().getState())));
                model.addAttribute("cities", cityRepository.findAllById(Arrays.asList(userInfo.getBusinessDetails().getCity())));
                model.addAttribute("typeOfMCC", mccRepository.findAllById(Arrays.asList(userInfo.getBusinessDetails().getTypeOfMcc())));
                Map<String, List<ShopType>> listOfShops = shopTypeRepository.findAllById(Arrays.asList(userInfo.getBusinessDetails().getTypeOfShop())).stream()
                        .sorted(Comparator.comparing(ShopType::getIndustry))
                        .collect(Collectors.groupingBy(ShopType::getBusinessType));
                model.addAttribute("typeOfShops", listOfShops);
            }

            model.addAttribute("kycDetails", getKycDetails(userInfo, kycStep));
            model.addAttribute("stepNo", kycStep);
            model.addAttribute("userId", userInfo.getUserId());

        }catch (Exception e){
            logger.error("Error: {}", e);
        }
        return "kyc/kyc";
    }

    private KycDetails getKycDetails(UserInfo userInfo, String kycStep) {
        KycDetails kycDetails = new KycDetails();
        kycDetails.setStepNo(kycStep);
        kycDetails.setUserId(String.valueOf(userInfo.getUserId()));
        kycDetails.setAadhaarCardNumber(userInfo.getAadhaarNumber());
        kycDetails.setPanCard(userInfo.getPancardNumber());
        if (userInfo.getAgreementAccept() != null) {
            kycDetails.setTermAndConditions(String.valueOf(userInfo.getAgreementAccept()));
        }
        if (userInfo.getESignAgreement() != null) {
            kycDetails.setESignAgreement(String.valueOf(userInfo.getESignAgreement()));
        }
        BusinessDetails businessDetails = userInfo.getBusinessDetails();
        if (businessDetails != null) {
            kycDetails.setBusinessName(businessDetails.getBusinessName());
            kycDetails.setAddress(businessDetails.getAddress());
            kycDetails.setZipcode(businessDetails.getZipcode());
            if (businessDetails.getState() != null)
                kycDetails.setState(String.valueOf(businessDetails.getState()));
            if (businessDetails.getCity() != null)
                kycDetails.setCity(String.valueOf(businessDetails.getCity()));
            if (businessDetails.getTypeOfShop() != null)
                kycDetails.setTypeOfShops(String.valueOf(businessDetails.getTypeOfShop()));
            if (businessDetails.getTypeOfMcc() != null)
                kycDetails.setTypeOfMcc(String.valueOf(businessDetails.getTypeOfMcc()));
        }
        UserBankDetails bankDetails = userInfo.getBankDetails();
        if (bankDetails != null) {
            kycDetails.setBankName(bankDetails.getBankName());
            kycDetails.setIfscCode(bankDetails.getIfscCode());
            kycDetails.setAccountNumber(bankDetails.getAccountNumber());
            kycDetails.setAccountHolderName(bankDetails.getAccountHolderName());
        }

        return kycDetails;
    }

    @PostMapping("/updateKyc")
    public String updateKyc(Model model, KycDetails kycDetails, @LoginUser LoginUserInfo loginUserInfo) {

        UserInfo userInfo  = binderService.getCurrentUser(loginUserInfo);
        if (userInfo!=null) {

            if (kycDetails.getStepNo().equals("0")) {

//            businessDetails form data
                BusinessDetails businessDetails = new BusinessDetails();
                businessDetails.setBusinessName(kycDetails.getBusinessName());
                businessDetails.setAddress(kycDetails.getAddress());
                businessDetails.setZipcode(kycDetails.getZipcode());
                businessDetails.setState(Long.parseLong(kycDetails.getState()));
                businessDetails.setCity(Long.parseLong(kycDetails.getCity()));
                businessDetails.setTypeOfShop(Long.parseLong(kycDetails.getTypeOfShops()));
                businessDetails.setTypeOfMcc(Long.parseLong(kycDetails.getTypeOfMcc()));
                businessDetails.setUserInfo(userInfo);
                businessDetails = businessDetailsRepository.save(businessDetails);
                userInfo.setBusinessDetails(businessDetails);
            } else if (kycDetails.getStepNo().equals("1")) {

//            AadhaarNumber form data
//                userInfo.setAadhaarNumber(kycDetails.getAadhaarCardNumber());
            } else if (kycDetails.getStepNo().equals("2")) {
//            PancardNumber form data
//                userInfo.setPancardNumber(kycDetails.getPanCard());
            } else if (kycDetails.getStepNo().equals("3")) {
//            AgreementAccept form data
                if (kycDetails.getTermAndConditions() != null) {
                    userInfo.setAgreementAccept(kycDetails.getTermAndConditions().equalsIgnoreCase("on") ? 1l : 0);
                    userInfo.setESignAgreement(kycDetails.getTermAndConditions().equalsIgnoreCase("on") ? 1l : 0);// TODO remove this after NSDL API
                }
//            ESignAgreement form data
                if (kycDetails.getESignAgreement() != null)
                    userInfo.setESignAgreement(kycDetails.getESignAgreement().equalsIgnoreCase("on") ? 1l : 0);
            } else if (kycDetails.getStepNo().equals("4")) {

                try {
                    if (kycDetails.getTermAndConditions() != null) {
                        userInfo.setAgreementAccept(kycDetails.getTermAndConditions().equalsIgnoreCase("on") ? 1l : 0);
                        userInfo.setESignAgreement(kycDetails.getTermAndConditions().equalsIgnoreCase("on") ? 1l : 0);// TODO remove this after NSDL API
                    }
//            ESignAgreement form data
                    if (kycDetails.getESignAgreement() != null)
                        userInfo.setESignAgreement(kycDetails.getESignAgreement().equalsIgnoreCase("on") ? 1l : 0);
                } catch (Exception e) {
                    logger.error("Error: {}", e);
                }
            } else if (kycDetails.getStepNo().equals("5")) {

                UserBankDetails bankDetails = new UserBankDetails();
                bankDetails.setAccountHolderName(kycDetails.getAccountHolderName());
                bankDetails.setAccountNumber(kycDetails.getAccountNumber());
                bankDetails.setBankName(kycDetails.getBankName());
                bankDetails.setIfscCode(kycDetails.getIfscCode());
                bankDetails.setUserInfo(userInfo);
                bankDetails = userBankDetailsRepository.save(bankDetails);
                userInfo.setBankDetails(bankDetails);
                utilService.sendKYCCompleteMail(userInfo);
            }

            userInfoRepository.save(userInfo);

        }


        return "redirect:/kyc/";
    }

//    @GetMapping("/updateBusinessDetails")
//    @ResponseBody
//    public Response businessDetails(HttpServletRequest request, Model model) {
//
//
//        Response response = new Response();
//        response.setMessage("Success");
//        response.setError(false);
//        response.setResult("Success");
//        return response;
//    }

    @GetMapping("/verifyAadhaarCard")
    @ResponseBody
    public Response<AadhaarVerification> verifyAadhaarCard(@RequestParam(value = "aadhaarCard", required = true) String aadhaarCard,
                                                           @RequestParam(value = "refId", required = true) String refId,
                                                           @RequestParam(value = "otp", required = true) String otp, @LoginUser LoginUserInfo loginUserInfo) {
        Response response = cashFreeService.verifyAadhaarOTP(refId, otp);
        if (!response.isError()) {
            AadhaarVerification verification = (AadhaarVerification) response.getResult();
            if (verification.getStatus() != null && verification.getStatus().equalsIgnoreCase("VALID")) {
                UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);
                userInfo.setAadhaarNumber(aadhaarCard);
                userInfoRepository.save(userInfo);
            }
        }
        return response;
    }

    @GetMapping("/generateAadhaarCardOtp")
    @ResponseBody
    public Response<AadhaarOTPResponse> generateAadhaarCardOtp(@RequestParam(value = "aadhaarCard", required = true) String aadhaarCard) {
        return cashFreeService.generateAadhaarOTP(aadhaarCard);
    }

    @GetMapping("/verifyPanCard")
    @ResponseBody
    public Response<PanCardVerification> verifyPanCard(@RequestParam(value = "panCard", required = true) String panCard, @LoginUser LoginUserInfo loginUserInfo) {
        Response response = cashFreeService.verifyPanCard(panCard);
        if (!response.isError()) {
            PanCardVerification verification = (PanCardVerification) response.getResult();
            if (verification.getValid() != null && verification.getValid()) {
                UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);
                userInfo.setPancardNumber(panCard);
                userInfoRepository.save(userInfo);
            }
        }
        return response;
    }

    @GetMapping("/verifyBankDetails")
    @ResponseBody
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
                bankDetails = userBankDetailsRepository.save(bankDetails);
                userInfo.setBankDetails(bankDetails);
                userInfoRepository.save(userInfo);
                if(userInfo.getUserPSwitchId().contains("XXX")) {
                    utilService.sendKYCCompleteMail(userInfo);
                }
            }
        }
        return response;
    }

    @GetMapping("/eSignAgreement")
    @ResponseBody
    public Response<String> eSignAgreement(HttpServletRequest request) {
//        call PanCard API
        Response response = new Response();
        response.setMessage("Success");
        response.setError(false);
        response.setResult("Success");
        return response;
    }

    @GetMapping("/cities")
    @ResponseBody
    public Response<List<City>> cities(@RequestParam(value = "stateId", required = true) Long stateId) {
        Response response = new Response();
        try {
            response.setMessage("");
            response.setError(false);
            response.setResult(cityRepository.findByStateId(stateId));
            return response;
        } catch (Exception e) {
            logger.error("Error: {}", e);
        }

        response.setMessage("Error while fetching city");
        response.setError(true);
        response.setResult(null);

        return response;
    }

    @GetMapping("/postalPincode")
    @ResponseBody
    public Response<PostOffice> postalpincode(@RequestParam(value = "zipcode", required = true) String zipcode) {
        try {
            String url = "https://api.postalpincode.in/pincode/" + zipcode;
            PostalPincode[] postalPincodes = restTemplate.getForObject(url, PostalPincode[].class);
            if (postalPincodes != null && postalPincodes.length > 0) {
                PostalPincode result = postalPincodes[0];
                if (result != null && result.getStatus().equalsIgnoreCase("Success") && !CollectionUtils.isEmpty(result.getPostOffice())) {
                    PostOffice postOffice = result.getPostOffice().get(0);
                    States state = statesRepository.findByName(postOffice.getState());
                    if(state!=null)
                        postOffice.setState(String.valueOf(state.getId()));
                    City city = cityRepository.findByName(postOffice.getDistrict());
                    if(city!=null)
                        postOffice.setDistrict(String.valueOf(city.getId()));

                    Response response = new Response();
                    response.setMessage("Postal code found");
                    response.setError(false);
                    response.setResult(postOffice);

                    return response;
                }
            }
        } catch (RestClientException e) {
            logger.error("Error: {}", e);
        }
        return null;
    }

    @GetMapping("/getTypeOfShops")
    @ResponseBody
    public Map<String, List<ShopType>> getTypeOfShops() {

        Map<String, List<ShopType>> listOfShops = shopTypeRepository.findAll().stream()
                .collect(Collectors.groupingBy(ShopType::getBusinessType));

        return listOfShops;
    }

    @GetMapping("/getTypeOfMCC")
    @ResponseBody
    public List<MccType> getTypeOfMCC() {
        return mccRepository.findAll();
    }

    @PostMapping("/updateBankDetails")
    public String updateUserBankDetails(Model model, UserBankDetailsDTO bankDetailsDto, @LoginUser LoginUserInfo loginUserInfo) {

        boolean isValid = true;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserBankDetailsDTO>> violations = validator.validate(bankDetailsDto);
        List<String> errorMessage = new ArrayList<>();
        for (ConstraintViolation<UserBankDetailsDTO> violation : violations) {
            logger.error(violation.getMessage());
            errorMessage.add(violation.getMessage());
            isValid = false;
        }

        if (!isValid) {
            model.addAttribute("updateError", errorMessage);
        } else {
            Optional<UserInfo> userOptional = userInfoRepository.findById(Integer.parseInt(bankDetailsDto.getUserId()));
            if (userOptional != null) {
                UserInfo userProfile = userOptional.get();

                UserBankDetails bankDetails = new UserBankDetails();
                bankDetails.setAccountHolderName(bankDetailsDto.getAccountHolderName());
                bankDetails.setAccountNumber(bankDetailsDto.getAccountNumber());
                bankDetails.setBankName(bankDetailsDto.getBANK_NAME());
                bankDetails.setIfscCode(bankDetailsDto.getIFSC_CODE());
                bankDetails.setUserInfo(userProfile);
                userBankDetailsRepository.save(bankDetails);
                userProfile.setBankDetails(bankDetails);

                userInfoRepository.save(userProfile);
                model.addAttribute("updateMessage", "Bank Details update successfully");
            }
        }

        model = binderService.bindUserDetails(model,loginUserInfo);
        return "kyc/kyc";
    }
}
