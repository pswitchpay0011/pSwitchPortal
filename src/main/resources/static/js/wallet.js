function verifyBankInfo() {
    const accountNumber = $('#accountNumber').val();
    const ifscCode = $('#ifscCode').val();
    const accountType = $('#accountType').val();
    const bankName = $('#bankName').val();

    if (accountNumber!=="" && ifscCode!=="" && accountType!=="" && bankName!=="") {
        const params = {
            stepNo: "4",
            accountNumber: accountNumber,
            ifscCode: ifscCode,
            accountType: accountType,
        };
        ajaxCall("/user/verifyBankDetails", params, 'GET', function(data) {
            if (data.error == true) {
                showError(data.result.message);
            } else {
                $("#accountHolderName").val(data.result.data.nameAtBank);
                $("#accountNumber").prop('disabled', true);
                $("#accountType").prop('disabled', true);
                $("#ifscCode").prop('disabled', true);
                $("#bankName").prop('disabled', true);
                showSuccess(data.result.message);
                window.location.replace("/user/viewWallet");
            }
        }, function(error) {}, function(complete) {});
    }else{
        if(bankName === ""){
            showError("Bank Name is required");
        }else if(ifscCode === ""){
            showError("Bank IFSC Code is required");
        }else if(accountType === ""){
            showError("Bank Account type is required");
        }else if(accountNumber === ""){
            showError("Bank Account number is required");
        }
    }
}