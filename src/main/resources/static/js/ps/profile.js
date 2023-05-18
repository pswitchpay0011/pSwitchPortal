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
                    window.location.replace("/profile");

	 				/*$("#businessName").val(data.result.legal_name_of_business);
					var address = data.result.principal_place_address.toUpperCase().replace(data.result.legal_name_of_business.toUpperCase(), '');

					const zipRegex = /\b\d{6}\b/g; 
					const zipCode = data.result.principal_place_address.match(zipRegex)[0];
//					$("#zipcode").val(zipCode);
					address = address.replace(zipCode, '');
					address = address.trim().replace(/^,|,$/g, '');
//					$("#address").val(address);
//					zipcodeCheck();*/
				}
            }, function(error) {}, function(complete) {});
        } else {
            showError('Please enter 15 digits for a valid GSTIN.');
        }
    } else {
        showError('Please enter 15 digits for a valid GSTIN.');
    }
}

/*function verifyBankInfo() {
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
}*/

/*
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

 				*/
/*var address = $('#address').val();
				address = address.replace(zipCode, '');
				address = address.trim().replace(/^,|,$/g, '');
				$("#address").val(address);*//*


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
}*/
