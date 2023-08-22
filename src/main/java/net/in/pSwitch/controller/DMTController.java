package net.in.pSwitch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import net.in.pSwitch.dto.CreateRemitterDTO;
import net.in.pSwitch.eko.CustomerDetails;
import net.in.pSwitch.eko.EKODataBuilder;
import net.in.pSwitch.eko.model.AddRecipientResponse;
import net.in.pSwitch.eko.model.InitiateTransaction;
import net.in.pSwitch.eko.model.Recipient;
import net.in.pSwitch.eko.model.RecipientData;
import net.in.pSwitch.eko.model.RecipientDetails;
import net.in.pSwitch.eko.model.Refund;
import net.in.pSwitch.eko.model.ResendRefundOTP;
import net.in.pSwitch.eko.model.TransactionInquiry;
import net.in.pSwitch.eko.request.CustomerAddress;
import net.in.pSwitch.eko.response.EKOResponse;
import net.in.pSwitch.model.BusinessDetails;
import net.in.pSwitch.model.City;
import net.in.pSwitch.model.States;
import net.in.pSwitch.model.user.UserInfo;
import net.in.pSwitch.model.UserServiceId;
import net.in.pSwitch.model.api.AadhaarVerification;
import net.in.pSwitch.repository.AadhaarRepository;
import net.in.pSwitch.repository.CityRepository;
import net.in.pSwitch.repository.ConfirmTransactionRepository;
import net.in.pSwitch.repository.StatesRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.repository.UserServiceIdRepository;
import net.in.pSwitch.repository.eko.EKOBankListRepository;
import net.in.pSwitch.repository.eko.EkoBeneficiaryRepository;
import net.in.pSwitch.repository.eko.EkoCustomerDetailsRepository;
import net.in.pSwitch.repository.eko.EkoCustomerTransactionLimitRepository;
import net.in.pSwitch.repository.eko.EkoRecipientDataRepository;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.service.eko.EKOCustomerInfoService;
import net.in.pSwitch.service.eko.EKOOnBoardService;
import net.in.pSwitch.service.eko.EKORecipientsService;
import net.in.pSwitch.service.eko.EKOTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/retailer/dmt")
public class DMTController {

    Logger logger = LoggerFactory.getLogger(DMTController.class);

    @Autowired
    private BinderService binderService;
    @Autowired
    private StatesRepository statesRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ConfirmTransactionRepository confirmTransactionRepository;
    @Autowired
    private EKOCustomerInfoService ekoCustomerInfoService;

    @Autowired
    private EkoCustomerDetailsRepository ekoCustomerDetailsRepository;
    @Autowired
    private EkoCustomerTransactionLimitRepository ekoCustomerLimitRepository;
    @Autowired
    private EkoBeneficiaryRepository ekoBeneficiaryRepository;
    @Autowired
    private EkoRecipientDataRepository recipientDataRepository;
    @Autowired
    private EKORecipientsService ekoRecipientsService;
    @Autowired
    private EKOTransactionService ekoTransactionService;
    @Autowired
    private EKOOnBoardService ekoOnBoardService;
    @Autowired
    private EKODataBuilder ekoDataBuilder;
    @Autowired
    private UserServiceIdRepository userServiceIdRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private AadhaarRepository aadhaarRepository;
    @Autowired
    private EKOBankListRepository ekoBankListRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping({"/", ""})
    public String home(Model model, @LoginUser LoginUserInfo loginUserInfo) {
        model = binderService.bindUserDetails(model, loginUserInfo);

        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);

        if(userInfo.getServiceId()==null || StringUtils.isEmpty(userInfo.getServiceId().getEkoUserCode())) {
            EKOResponse<CustomerDetails> ekoResponse = ekoOnBoardProcess(userInfo, userInfo.getServiceId());
        }

        model.addAttribute("states", statesRepository.findByCountryIdOrderByNameAsc(101l));
        model.addAttribute("cities", cityRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));

        return "dmt/dmtHome";
    }

    @RequestMapping({"/beneficiary/{mobileNumber}"})
    public String beneficiaryList(Model model, @LoginUser LoginUserInfo loginUserInfo,
                                 @PathVariable String mobileNumber) {
        model = binderService.bindUserDetails(model, loginUserInfo);
        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);
        EKOResponse<RecipientData> response = ekoRecipientsService.getRecipientList(mobileNumber, userInfo.getServiceId().getEkoUserCode());
        List<Recipient> recipientList = new ArrayList<>();
        if(!response.isError() && response.getData()!=null){
            RecipientData recipientData = response.getData();
            recipientList = recipientData.getRecipientList();

            Optional<RecipientData> databaseRecipientData  = recipientDataRepository.findById(mobileNumber);

            recipientData.setMobileNumber(mobileNumber);
            databaseRecipientData.ifPresent(details -> recipientData.setCreatedOn(details.getCreatedOn()));

            recipientDataRepository.save(recipientData);
        }
        model.addAttribute("recipientList", recipientList);
        model.addAttribute("mobileNumber", mobileNumber);

        return "dmt/beneficiaryList";
    }

    @RequestMapping({"/beneficiary/add/{mobileNumber}"})
    public String addBeneficiary(Model model, @LoginUser LoginUserInfo loginUserInfo,
                                 @PathVariable String mobileNumber) {
        model = binderService.bindUserDetails(model, loginUserInfo);
        model.addAttribute("mobileNumber", mobileNumber);
        model.addAttribute("bankList", ekoBankListRepository.findAll());
        return "dmt/addBeneficiary";
    }

    @RequestMapping({"/beneficiary/{mobileNumber}/{beneficiaryId}"})
    public String viewBeneficiary(Model model, @LoginUser LoginUserInfo loginUserInfo,
                                  @PathVariable String mobileNumber,
                                  @PathVariable Integer beneficiaryId) {
        model = binderService.bindUserDetails(model, loginUserInfo);
        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);

        Optional<Recipient> databaseRecipientData  = ekoBeneficiaryRepository.findById(beneficiaryId);
        if(databaseRecipientData.isPresent()){
            model.addAttribute("recipient", databaseRecipientData.get());
        }

        model.addAttribute("beneficiaryId", beneficiaryId);
        model.addAttribute("mobileNumber", mobileNumber);
        model.addAttribute("view", 1);
        model.addAttribute("bankList", ekoBankListRepository.findAll());

        return "dmt/addBeneficiary";
    }

    @PostMapping("/beneficiary/save")
    public EKOResponse<AddRecipientResponse> addBeneficiary(@RequestParam String mobileNumber,
                                                            @RequestParam String accIfsc,
                                                            @LoginUser LoginUserInfo loginUserInfo) {

        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("initiator_id", ekoDataBuilder.getInitiatorId());
        params.add("recipient_mobile", "9115597890");
        params.add("bank_id", "56");
        params.add("recipient_type", "3");
        params.add("recipient_name", "Aditya");
        params.add("user_code", userInfo.getServiceId().getEkoUserCode());

        return ekoRecipientsService.addRecipient(mobileNumber, accIfsc, params);
    }

    public EKOResponse<CustomerDetails> ekoOnBoardProcess(UserInfo userInfo, UserServiceId userServiceId) {

        EKOResponse<CustomerDetails> ekoResponse = new EKOResponse<>();

        Optional<AadhaarVerification> aadhaarVerification = aadhaarRepository.findById(userInfo.getAadhaarNumber());
        if (aadhaarVerification.isPresent()) {
            AadhaarVerification aadhaar = aadhaarVerification.get();

            BusinessDetails businessDetails = userInfo.getBusinessDetails();

            MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
            data.add("initiator_id", ekoDataBuilder.getInitiatorId());
            data.add("pan_number", userInfo.getPancardNumber());
            data.add("mobile", userInfo.getMobileNumber());
            data.add("first_name", userInfo.getFirstName());
            data.add("last_name", userInfo.getLastName());
            data.add("email", userInfo.getUsername());
            data.add("dob", aadhaar.getDob());
            data.add("shop_name", userInfo.getBusinessDetails().getBusinessName());

            CustomerAddress address = new CustomerAddress();
            address.setLine("India");
            address.setArea(businessDetails.getAddress());
            address.setPincode(businessDetails.getZipcode());

            Optional<City> cityData = cityRepository.findById(businessDetails.getCity());
            if (cityData.isPresent()) {
                address.setCity(cityData.get().getName());
                address.setDistrict(cityData.get().getName());
            } else {
                EKOResponse<CustomerDetails> response = new EKOResponse<>();
                response.setMessage("Invalid city data");
                response.setError(true);
                return response;
            }

            Optional<States> stateData = statesRepository.findById(businessDetails.getState());
            if (stateData.isPresent()) {
                address.setState(stateData.get().getName());
            } else {
                EKOResponse<CustomerDetails> response = new EKOResponse<>();
                response.setMessage("Invalid state data");
                response.setError(true);
                return response;
            }

            logger.info(address.toString());
            try {
                data.add("residence_address", objectMapper.writeValueAsString(address));
            } catch (JsonProcessingException e) {
                logger.error("Error: ", e);
            }

            ekoResponse = ekoOnBoardService.onboardUser(data);

            if (!ekoResponse.isError() && ekoResponse.getData()!=null && !StringUtils.isEmpty(ekoResponse.getData().getUserCode())) {
                userServiceId = userServiceId==null ? new UserServiceId():userServiceId;
                userServiceId.setEkoUserCode(ekoResponse.getData().getUserCode());
                userServiceId.setId(userInfo.getUserId());
                userInfo.setServiceId(userServiceIdRepository.save(userServiceId));
                userInfoRepository.save(userInfo);
            }
        }else {
            ekoResponse.setMessage("Aadhaar card details is not present. please complete the KYC process");
            ekoResponse.setError(true);
        }
        return ekoResponse;
    }


    @GetMapping("/getRemitterDetails")
    @ResponseBody
    public EKOResponse<CustomerDetails> getCustomerDetails(@RequestParam String mobileNumber, @LoginUser LoginUserInfo loginUserInfo) {

        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("initiator_id", ekoDataBuilder.getInitiatorId());
        data.add("user_code", userInfo.getServiceId().getEkoUserCode());

        EKOResponse<CustomerDetails>  ekoResponse = ekoCustomerInfoService.getRemitterInfo(mobileNumber, data);//8870778821

        try {
            if (!ekoResponse.isError() && ekoResponse.getData() != null && !StringUtils.isEmpty(ekoResponse.getData().getCustomerId())) {
                CustomerDetails customerDetails = ekoResponse.getData();

//            List<Limit> limit = customerDetails.getLimit();
//            limit.forEach(limit1 -> limit1.setCustomerDetails(customerDetails));
//
//            ekoCustomerLimitRepository.saveAll();

                Optional<CustomerDetails> databaseCustomerDetails = ekoCustomerDetailsRepository.findById(customerDetails.getCustomerId());

                databaseCustomerDetails.ifPresent(details -> customerDetails.setCreatedOn(details.getCreatedOn()));

                CustomerDetails details = ekoCustomerDetailsRepository.save(customerDetails);
                logger.info("customerDetails: ", details);
            }
        }catch(Exception e){
            logger.error("Error: ", e);
        }

        return ekoResponse;
    }

    @PostMapping("/createRemitter")
    @ResponseBody
    public EKOResponse<CustomerDetails> createRemitter(Model model,
                                                       @RequestBody CreateRemitterDTO createRemitterDTO,
                                                       @LoginUser LoginUserInfo loginUserInfo) {

        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);
        MultiValueMap<String, String> requestData = new LinkedMultiValueMap<>();

        CustomerAddress address = new CustomerAddress();
        address.setLine("India");
        address.setArea(createRemitterDTO.getAddress());
        address.setPincode(createRemitterDTO.getZipcode());

        Optional<AadhaarVerification> aadhaarVerification = aadhaarRepository.findById(userInfo.getAadhaarNumber());
        aadhaarVerification.ifPresent(verification -> requestData.add("dob", verification.getDob()));

        Optional<City> cityData = cityRepository.findById(Long.parseLong(createRemitterDTO.getCity()));
        if (cityData.isPresent()) {
            address.setCity(cityData.get().getName());
            address.setDistrict(cityData.get().getName());
        } else {
            EKOResponse<CustomerDetails> response = new EKOResponse<>();
            response.setMessage("Invalid city data");
            response.setError(true);
            return response;
        }

        Optional<States> stateData = statesRepository.findById(Long.parseLong(createRemitterDTO.getState()));
        if (stateData.isPresent()) {
            address.setState(stateData.get().getName());
        } else {
            EKOResponse<CustomerDetails> response = new EKOResponse<>();
            response.setMessage("Invalid state data");
            response.setError(true);
            return response;
        }


        requestData.add("initiator_id", ekoDataBuilder.getInitiatorId());
        requestData.add("user_code", userInfo.getServiceId().getEkoUserCode());
        requestData.add("name", createRemitterDTO.getFullName());

        try {
            requestData.add("residence_address", objectMapper.writeValueAsString(address));
        } catch (JsonProcessingException e) {
            logger.error("Error:", e);
        }
        requestData.add("skip_verification", "true");

        return ekoCustomerInfoService.createRemitter(createRemitterDTO.getRemitterMobileNumber(), requestData);
    }

    @GetMapping("/verification")
    @ResponseBody
    public EKOResponse<CustomerDetails> verificationRemitter(@LoginUser LoginUserInfo loginUserInfo,
                                                             @RequestParam String mobileNumber,
                                                             @RequestParam String otp) {

        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("initiator_id", ekoDataBuilder.getInitiatorId());
        requestBody.add("user_code", userInfo.getServiceId().getEkoUserCode());

        requestBody.add("id_type", "mobile_number");
        requestBody.add("id", mobileNumber);

        return ekoCustomerInfoService.verifyRemitterOTP(requestBody, otp);
    }


    @GetMapping("/resendOTP")
    @ResponseBody
    public EKOResponse<CustomerDetails> resendOTP(@LoginUser LoginUserInfo loginUserInfo) {

        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("initiator_id", ekoDataBuilder.getInitiatorId());
        queryParams.add("user_code", userInfo.getServiceId().getEkoUserCode());

        return ekoCustomerInfoService.resendRemitterOTP(queryParams, userInfo.getMobileNumber());
    }


    @GetMapping("/getBeneficiaryList")
    @ResponseBody
    public EKOResponse<RecipientData> getBeneficiaryList(@RequestParam String mobileNumber,
                                                         @LoginUser LoginUserInfo loginUserInfo) {
        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);
        return ekoRecipientsService.getRecipientList(mobileNumber, userInfo.getServiceId().getEkoUserCode());
    }

    @GetMapping("/getBeneficiaryDetails")
    @ResponseBody
    public EKOResponse<RecipientDetails> getBeneficiaryDetails(@RequestParam String mobileNumber,
                                                               @RequestParam String recipientId,
                                                               @LoginUser LoginUserInfo loginUserInfo) {
        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);
        return ekoRecipientsService.getRecipientDetails(mobileNumber, recipientId, userInfo.getServiceId().getEkoUserCode());
    }


    @GetMapping("/initiateTransaction")
    @ResponseBody
    public EKOResponse<InitiateTransaction> initiateTransaction(@RequestParam String customerId,
                                                                @RequestParam String recipientId,
                                                                @RequestParam String amount,
                                                                @RequestParam String channel,
                                                                @LoginUser LoginUserInfo loginUserInfo) {

        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("initiator_id", ekoDataBuilder.getInitiatorId());
        requestBody.add("customer_id", customerId);
        requestBody.add("recipient_id", recipientId);
        requestBody.add("amount", amount);
        requestBody.add("channel", channel);
        requestBody.add("state", "1");
        Timestamp ts = Timestamp.from(Instant.now());
        requestBody.add("timestamp", ts.toString());
        requestBody.add("currency", "INR");
        requestBody.add("latlong", "26.8863786,75.7393589");
        requestBody.add("client_ref_id", "RIM10011909045679290");
        requestBody.add("user_code", userInfo.getServiceId().getEkoUserCode());
        return ekoTransactionService.initiateTransaction(requestBody);
    }

    @GetMapping("/transactionInquiry")
    @ResponseBody
    public EKOResponse<TransactionInquiry> transactionInquiry(@RequestParam String transactionId,
                                                              @LoginUser LoginUserInfo loginUserInfo) {
        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);
        return ekoTransactionService.transactionInquiry(transactionId, userInfo.getServiceId().getEkoUserCode());
    }

    @GetMapping("/refundTransaction")
    @ResponseBody
    public EKOResponse<Refund> refundTransaction(@RequestParam String transactionId,
                                                 @LoginUser LoginUserInfo loginUserInfo) {

        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("initiator_id", ekoDataBuilder.getInitiatorId());
        requestBody.add("otp", "3682953466");
        requestBody.add("state", "1");
        requestBody.add("user_code", userInfo.getServiceId().getEkoUserCode());
        return ekoTransactionService.refundTransaction(requestBody, transactionId);
    }

    @GetMapping("/refundTransactionOTP")
    @ResponseBody
    public EKOResponse<ResendRefundOTP> refundTransactionOTP(@RequestParam String transactionId,
                                                             @LoginUser LoginUserInfo loginUserInfo) {
        UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);
        return ekoTransactionService.refundTransactionOTP(transactionId, userInfo.getServiceId().getEkoUserCode());
    }
}
