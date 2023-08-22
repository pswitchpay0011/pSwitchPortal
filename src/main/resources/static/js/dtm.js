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

function registerRemitter() {
    const fname = $('#fname').val();
    const lname = $('#lname').val();
    const address = $('#address').val();
    const zipcode = $('#zipcode').val();
    const city = $('#city').val();
    const state = $('#state').val();
    const remitterMobileNumber = $('#remitterMobileNumber').val();

    if (fname!=="" && lname!=="" && address!=="" && zipcode!=="" && city!=="" && state!=="" && remitterMobileNumber!="") {
        const params = JSON.stringify({
            fname: fname,
            lname: lname,
            address: address,
            zipcode: zipcode,
            city: city,
            state: state,
            remitterMobileNumber: remitterMobileNumber
        });
        ajaxCall("/retailer/dmt/createRemitter", params, 'POST', function(data) {
            if (data.error == true) {
                showError(data.message);
            } else {
                $("#fname").prop('disabled', true);
                $("#lname").prop('disabled', true);
                $("#address").prop('disabled', true);
                $("#zipcode").prop('disabled', true);
                $("#city").prop('disabled', true);
                $("#state").prop('disabled', true);
                $('#add-sender').modal('toggle');
                showSuccess(data.message);
            }
        }, function(error) {}, function(complete) {});
    }else{
        if(fname === ""){
            showError("First Name is required");
        }else if(lname === ""){
            showError("Last Name is required");
        }else if(address === ""){
            showError("Address is required");
        }else if(zipcode === ""){
            showError("Pin code is required");
        }else if(city === ""){
            showError("City is required");
        }else if(state === ""){
            showError("State is required");
        }else if(remitterMobileNumber === ""){
            showError("Remitter Mobile Number is required");
        }
    }
}

function resendOtp() {
    const params = {
    };
    ajaxCall("/retailer/dmt/resendOTP", params, 'GET', function(data) {
    if (data.error == true) {
        showError(data.message);
    } else {
        showSuccess(data.message);
    }
    }, function(error) {}, function(complete) {

    });
}

function getRemitterDetails() {
    const mobileNumber = $('#mobileNumber').val();
    const params = {
        mobileNumber: mobileNumber
    };

    ajaxCall("/retailer/dmt/getRemitterDetails", params, 'GET', function(data) {
        if (data.error == true) {
            showError(data.message);
        } else {
            const customerDetail = data.data;
            $('#remitterDetails_name').val(customerDetail.name);
            $('#remitterDetails_mobile').val(customerDetail.mobile);
            $('#remitterDetails_state').val(customerDetail.state_desc);
            $('#remitterDetails_limit').val(customerDetail.available_limit);

            $("#viewBeneficiaryList").attr("href",
                        "/retailer/dmt/beneficiary/"+customerDetail.customer_id);

            $('#remitterDetails').modal('toggle');
//            showSuccess(data.message);
        }
    }, function(error) {}, function(complete) {});
}

function searchBeneficiary() {
    const mobileNumber = $('#searchBeneficiary_mobile').val();
    const params = {
        mobileNumber: mobileNumber
    };

    ajaxCall("/retailer/dmt/getRemitterDetails", params, 'GET', function(data) {
        if (data.error == true) {
            showError(data.message);
        } else {
            const customerDetail = data.data;
            $('#remitterDetails_name').val(customerDetail.name);
            $('#remitterDetails_mobile').val(customerDetail.mobile);
            $('#remitterDetails_state').val(customerDetail.state_desc);
            $('#remitterDetails_limit').val(customerDetail.available_limit);

            $('#remitterDetails').modal('toggle');
//            showSuccess(data.message);
        }
    }, function(error) {}, function(complete) {});
}