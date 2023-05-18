/* ------------------------------------------------------------------------------
 *
 *  # Steps wizard
 *
 *  Demo JS code for form_wizard.html page
 *
 * ---------------------------------------------------------------------------- */


// Setup module
// ------------------------------
var FormWizard = function() {


    //
    // Setup module components
    //


    // Wizard
    var _componentWizard = function() {
        if (!$().steps) {
            console.warn('Warning - steps.min.js is not loaded.');
            return;
        }

        // Basic wizard setup
        $('.steps-basic').steps({
            headerTag: 'h6',
            bodyTag: 'fieldset',
            transitionEffect: 'fade',
            titleTemplate: '<span class="number">#index#</span> #title#',
            labels: {
                previous: '<i class="icon-arrow-left13 mr-2" /> Previous',
                next: 'Next <i class="icon-arrow-right14 ml-2" />',
                finish: 'Submit form <i class="icon-arrow-right14 ml-2" />'
            },
            onFinished: function(event, currentIndex) {
                alert('Form submitted.');
            }
        });

        // Async content loading
        $('.steps-async').steps({
            headerTag: 'h6',
            bodyTag: 'fieldset',
            transitionEffect: 'fade',
            titleTemplate: '<span class="number">#index#</span> #title#',
            loadingTemplate: '<div class="card-body text-center"><i class="icon-spinner2 spinner mr-2"></i>  #text#</div>',
            labels: {
                previous: '<i class="icon-arrow-left13 mr-2" /> Previous',
                next: 'Next <i class="icon-arrow-right14 ml-2" />',
                finish: 'Submit form <i class="icon-arrow-right14 ml-2" />'
            },
            onContentLoaded: function(event, currentIndex) {
                $(this).find('.card-body').addClass('hide');

                // Re-initialize components
                _componentSelect2();
                _componentUniform();
            },
            onFinished: function(event, currentIndex) {
                alert('Form submitted.');
            }
        });

        // Saving wizard state
        $('.steps-state-saving').steps({
            headerTag: 'h6',
            bodyTag: 'fieldset',
            titleTemplate: '<span class="number">#index#</span> #title#',
            labels: {
                previous: '<i class="icon-arrow-left13 mr-2" /> Previous',
                next: 'Next <i class="icon-arrow-right14 ml-2" />',
                finish: 'Submit form <i class="icon-arrow-right14 ml-2" />'
            },
            transitionEffect: 'fade',
            saveState: true,
            autoFocus: true,
            onFinished: function(event, currentIndex) {
                alert('Form submitted.');
            }
        });

        // Specify custom starting step
        $('.steps-starting-step').steps({
            headerTag: 'h6',
            bodyTag: 'fieldset',
            titleTemplate: '<span class="number">#index#</span> #title#',
            labels: {
                previous: '<i class="icon-arrow-left13 mr-2" /> Previous',
                next: 'Next <i class="icon-arrow-right14 ml-2" />',
                finish: 'Submit form <i class="icon-arrow-right14 ml-2" />'
            },
            transitionEffect: 'fade',
            startIndex: 2,
            autoFocus: true,
            onFinished: function(event, currentIndex) {
                alert('Form submitted.');
            }
        });

        // Enable all steps and make them clickable
        $('.steps-enable-all').steps({
            headerTag: 'h6',
            bodyTag: 'fieldset',
            transitionEffect: 'fade',
            enableAllSteps: true,
            titleTemplate: '<span class="number">#index#</span> #title#',
            labels: {
                previous: '<i class="icon-arrow-left13 mr-2" /> Previous',
                next: 'Next <i class="icon-arrow-right14 ml-2" />',
                finish: 'Submit form <i class="icon-arrow-right14 ml-2" />'
            },
            onFinished: function(event, currentIndex) {
                alert('Form submitted.');
            }
        });


        //
        // Wizard with validation
        //

        // Stop function if validation is missing
        if (!$().validate) {
            console.warn('Warning - validate.min.js is not loaded.');
            return;
        }

        // Show form
        var form = $('.steps-validation').show();
        var $stopChanging = false;

        // Initialize wizard
        $('.steps-validation').steps({
            headerTag: 'h6',
            bodyTag: 'fieldset',
            titleTemplate: '<span class="number">#index#</span> #title#',
            labels: {
                previous: '<i class="icon-arrow-left13 mr-2" /> Previous',
                next: 'Next And Save <i class="icon-arrow-right14 ml-2" />',
                finish: 'Submit form <i class="icon-arrow-right14 ml-2" />'
            },
            transitionEffect: 'fade',
            autoFocus: true,
            startIndex: parseFloat(document.getElementById("stepNo").value),
            onStepChanging: function(event, currentIndex, newIndex) {

                // Allways allow previous action even if the current form is not valid!
                if (currentIndex > newIndex) {
                    return true;
                }

                // Needed in some cases if the user went back (clean up)
                if (currentIndex < newIndex) {

                    // To remove error styles
                    form.find('.body:eq(' + newIndex + ') label.error').remove();
                    form.find('.body:eq(' + newIndex + ') .error').removeClass('error');
                }

                form.validate().settings.ignore = ':disabled,:hidden';
                let isFormValid = form.valid();
                var isDisabled = false;
                if (currentIndex == 0) {
                    isDisabled = $('#businessName').prop('disabled');
                } else if (currentIndex == 1) {
                    isDisabled = true;
                    // showSuccess("Saving information ");
                } else if (currentIndex == 2) {
                    isDisabled = true;
                    // showSuccess("Saving information ");
                } else if (currentIndex == 3) {
                    $("#stepNo").val("3");
                    // isDisabled = $('#termAndConditions').prop('disabled');
                }

                if (isFormValid && !isDisabled) {
                    showSuccess("Saving information ");
                    $("body").addClass("loading");
                    $('#kycForm').submit();
                }
                return isFormValid;
            },
            onFinishing: function(event, currentIndex) {
                form.validate().settings.ignore = ':disabled,:hidden';
                return form.valid();
            },
            onFinished: function(event, currentIndex) {
                $stopChanging = false;
                var accountHolderName = $('#accountHolderName').val();
                if (form.valid() && accountHolderName !== "") {
                    window.location.replace("/index");
                }
            },
            // saveState: true,
            // onContentLoaded: function (event, currentIndex) {
            //     $stopChanging = false;
            // }
        });


        // Initialize validation
        $('.steps-validation').validate({
            ignore: 'input[type=hidden], .select2-search__field', // ignore hidden fields
            errorClass: 'validation-invalid-label',
            highlight: function(element, errorClass) {
                $(element).removeClass(errorClass);
            },
            unhighlight: function(element, errorClass) {
                $(element).removeClass(errorClass);
            },

            // Different components require proper error label placement
            errorPlacement: function(error, element) {

                // Unstyled checkboxes, radios
                if (element.parents().hasClass('form-check')) {
                    error.appendTo(element.parents('.form-check').parent());
                }

                // Input with icons and Select2
                else if (element.parents().hasClass('form-group-feedback') || element.hasClass('select2-hidden-accessible')) {
                    error.appendTo(element.parent());
                }

                // Input group, styled file input
                else if (element.parent().is('.uniform-uploader, .uniform-select') || element.parents().hasClass('input-group')) {
                    error.appendTo(element.parent().parent());
                }

                // Other elements
                else {
                    error.insertAfter(element);
                }
            },
            rules: {
                email: {
                    email: true
                }
            }
        });
    };

    // Uniform
    var _componentUniform = function() {
        if (!$().uniform) {
            console.warn('Warning - uniform.min.js is not loaded.');
            return;
        }

        // Initialize
        $('.form-input-styled').uniform({
            fileButtonClass: 'action btn bg-blue'
        });
    };

    // Select2 select
    var _componentSelect2 = function() {
        if (!$().select2) {
            console.warn('Warning - select2.min.js is not loaded.');
            return;
        }

        // Initialize
        var $select = $('.form-control-select2').select2({
            minimumResultsForSearch: Infinity,
            width: '100%'
        });

        // Trigger value change when selection is made
        $select.on('change', function() {
            $(this).trigger('blur');
        });
    };


    //
    // Return objects assigned to module
    //

    return {
        init: function() {
            _componentWizard();
            _componentUniform();
            _componentSelect2();
        }
    }
}();


// Initialize module
// ------------------------------

document.addEventListener('DOMContentLoaded', function() {
    FormWizard.init();
});

function convertFormToJSON(form) {
    return $(form)
        .serializeArray()
        .reduce(function(json, {
            name,
            value
        }) {
            json[name] = value;
            return json;
        }, {});
}

function formatAadhaar() {
    var value = $("#aadhaarCardNumber").val()
    value = value.replace(/\D/g, "").substring(0, 12);
    value = value.replace(/\D/g, "").split(/(?:([\d]{4}))/g).filter(s => s.length > 0).join("-");
    $("#aadhaarCardNumber").val(value);
}

function formatOTP() {
    var value = $("#aadhaarOtp").val()
    value = value.replace(/\D/g, "").substring(0, 6);
    $("#aadhaarOtp").val(value);
}

function generateOtp() {
    let value = $("#aadhaarCardNumber").val();
    value = value.replace(/\D/g, "").substring(0, 12);
    if (value.length == 12) {
        $("#aadhaarCardNumber").prop('disabled', true);
        const params = {
            aadhaarCard: value
        };
        ajaxCall("/kyc/generateAadhaarCardOtp", params, 'GET', function(data) {
            if (data.error == true) {
                $("#aadhaarCardNumber").prop('disabled', false);
            } else {
                $("#refId").val(data.result.ref_id);
                $("#aadhaarOtp").prop('disabled', false);
                $("#verifyOTPBtn").show();
            }
        }, function(error) {}, function(complete) {});
    }
}

function verifyAadhaarOtp() {
    const otpValue = $("#aadhaarOtp").val();
    const refId = $("#refId").val();
    let value = $("#aadhaarCardNumber").val();
    value = value.replace(/\D/g, "").substring(0, 12);

    if (otpValue.length == 6) {
        $("#aadhaarOtp").prop('disabled', true);
        // $("body").addClass("loading");
        const params = {
            refId: refId,
            aadhaarCard: value,
            otp: otpValue
        };
        ajaxCall("/kyc/verifyAadhaarCard", params, 'GET', function(data) {
            if (data.error == true) {
                $("#aadhaarCardNumber").prop('disabled', false);
            }else{
                $("#stepNo").val("2");
            }
        }, function(error) {}, function(complete) {});
    } else {
        showError("Invalid OTP");
    }
}

function formatPanCard() {
    let value = $('#panCard').val();
    value = value.replace(/[^a-zA-Z 0-9]+/g, '').substring(0, 10);
    $("#panCard").val(value);
}

function verifyPanCard() {
    var regExp = /[a-zA-z]{5}\d{4}[a-zA-Z]{1}/;
    var value = $('#panCard').val();
    value = value.substring(0, 10);
    $("#panCard").val(value);
    if (value.length == 10) {
        if (value.match(regExp)) {
            $("#panCard").prop('disabled', true);
            const params = {
                panCard: value
            };
            ajaxCall("/kyc/verifyPanCard", params, 'GET', function(data) {
                if (data.error == true) {
                    $("#panCard").prop('disabled', false);
                    $("#btnPanVerify").hide();
                }else{
                    $("#stepNo").val("3");
                }
            }, function(error) {}, function(complete) {});
        } else {
            showError('Please enter 10 digits for a valid PAN number.');
        }
    } else {
        showError('Please enter 10 digits for a valid PAN number.');
    }
}

function formatGST() {
    let value = $('#GSTIN').val();
    value = value.replace(/[^a-zA-Z 0-9]+/g, '').substring(0, 15);
    $("#GSTIN").val(value);
}

function getGSTDetails() {
    var regExp = /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/;
    var value = $('#GSTIN').val();
    value = value.substring(0, 15);
    $("#GSTIN").val(value);
    if (value.length == 15) {
        if (value.match(regExp)) {
            $("#GSTIN").prop('disabled', true);
            const params = {
                gstin: value
            };
            ajaxCall("/kyc/getGSTDetails", params, 'GET', function(data) {
                if (data.error == true) {
                    $("#GSTIN").prop('disabled', false);
                }else{		
	 				$("#businessName").val(data.result.legal_name_of_business);
					var address = data.result.principal_place_address.toUpperCase().replace(data.result.legal_name_of_business.toUpperCase(), '');
					
					
					const zipRegex = /\b\d{6}\b/g; 
					const zipCode = data.result.principal_place_address.match(zipRegex)[0];
					$("#zipcode").val(zipCode);
					address = address.replace(zipCode, '');
					address = address.trim().replace(/^,|,$/g, '');
					$("#address").val(address);
					zipcodeCheck();
				}
            }, function(error) {}, function(complete) {});
        } else {
            showError('Please enter 15 digits for a valid GSTIN.');
        }
    } else {
        showError('Please enter 15 digits for a valid GSTIN.');
    }
}

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
        ajaxCall("/kyc/verifyBankDetails", params, 'GET', function(data) {
            if (data.error == true) {
                showError(data.result.message);
            } else {
                $("#accountHolderName").val(data.result.data.nameAtBank);
                $("#accountNumber").prop('disabled', true);
                $("#accountType").prop('disabled', true);
                $("#ifscCode").prop('disabled', true);
                $("#bankName").prop('disabled', true);
                showSuccess(data.result.message);
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

function zipcodeCheck() {
    var val = $("#zipcode").val()
    val = val.substring(0, 10);
    $("#zipcode").val(val);
    if (val.length == 6) {
        $("#zipcode").prop('disabled', true);
        $("#zipSpinner").show();

        const params = {
            zipcode: val
        };
        ajaxCall("/kyc/postalPincode", params, 'GET', function(data) {
            $("#state").val(data.result.State).trigger('change');

            const cityParams = {
                stateId: data.result.State
            };
            ajaxCall("/kyc/cities", cityParams, 'GET', function(cityData) {
                var html = '<option value="">Select City</option>';
                var len = cityData.result.length;
                for (var i = 0; i < len; i++) {
                    html += '<option value="' + cityData.result[i].id + '">' +
                        cityData.result[i].name + '</option>';
                }
                html += '</option>';
                $('#city').html(html);
                $("#city").val(data.result.District);

 				/*var address = $('#address').val();
				address = address.replace(zipCode, '');
				address = address.trim().replace(/^,|,$/g, '');
				$("#address").val(address);*/

                $("#zipcode").prop('disabled', false);
                $("#zipSpinner").hide();
                $("body").removeClass("loading");

            }, function(error) {}, function(complete) {
                $("#zipcode").prop('disabled', false);
                $("#zipSpinner").hide();
            });

        }, function(error) {}, function(complete) {
            $("#zipcode").prop('disabled', false);
            $("#zipSpinner").hide();
        });
    }
}