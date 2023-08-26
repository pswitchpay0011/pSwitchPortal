

function setMinDate() {
var today = new Date();
var minDate = new Date(today.getFullYear() - 18, today.getMonth(), today.getDate());
//return minDate.toISOString().split('T')[0];
    var dateField = document.getElementById("dob");
    dateField.setAttribute("max", minDate.toISOString().split('T')[0]);
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
            if (data.error != true){
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
            }
        }, function(error) {}, function(complete) {
            $("#zipcode").prop('disabled', false);
            $("#zipSpinner").hide();
        });
    }
}

$('#masterDistributor').change(function() {
const params = {
};
    if($(this).val()==''){
        var html = '<option value="">None</option>';
        $('#distributor').html(html);
        $("body").removeClass("loading");
    }else{

    ajaxCall("/user/distributors/"+$(this).val(), params, 'GET', function(data) {
        var html = '<option value="">None</option>';
        $.each(data, function(key, value) {
            html += '<option value="' + key + '">'
                                + value + '</option>';
        });
        html += '</option>';
        $('#distributor').html(html);

        $("body").removeClass("loading");

        }, function(error) {}, function(complete) {
            $("#zipSpinner").hide();
        });
    }
});

const firstNameEl = document.querySelector('#firstName');
const lastNameEl = document.querySelector('#lastName');
const emailEl = document.querySelector('#email');
const roleEl = document.querySelector('#role');
const dobEl = document.querySelector('#dob');
const addressEl = document.querySelector('#address');
const zipcodeEl = document.querySelector('#zipcode');
const mobileNumberEl = document.querySelector('#mobileNumber');
const stateEl = document.querySelector('#state');
const cityEl = document.querySelector('#city');

const form = document.querySelector('#associateForm');


const checkFirstName = () => {
    let valid = false;
    const min = 3,
        max = 100;

    var username = firstNameEl.value.trim();
    username = username.substring(0, 100);
    firstNameEl.value = username;

    if (!isRequired(username)) {
        showErrorText(firstNameEl, 'FirstName cannot be blank.');
    } else if (!isBetween(username.length, min, max)) {
        showErrorText(firstNameEl, `FirstName must be between ${min} and ${max} characters.`)
    }else if (!isUserNameValid(username)) {
        showErrorText(firstNameEl, `FirstName is not valid`)
    } else {
        showSuccessText(firstNameEl);
        valid = true;
    }
    return valid;
};

const checkLastName = () => {
    let valid = false;
    const min = 3,
        max = 100;

    var username = lastNameEl.value.trim();
    username = username.substring(0, 100);
    lastNameEl.value = username;

    if (!isRequired(username)) {
        showErrorText(lastNameEl, 'LastName cannot be blank.');
    } else if (!isBetween(username.length, min, max)) {
        showErrorText(lastNameEl, `LastName must be between ${min} and ${max} characters.`)
    }else if (!isUserNameValid(username)) {
        showErrorText(lastNameEl, `LastName is not valid`)
    } else {
        showSuccessText(lastNameEl);
        valid = true;
    }
    return valid;
};


const checkEmail = () => {
    let valid = false;

    var email = emailEl.value.trim();
    email = email.substring(0, 50);
    emailEl.value = email;

    if (!isRequired(email)) {
        showErrorText(emailEl, 'Email cannot be blank.');
    } else if (!isEmailValid(email)) {
        showErrorText(emailEl, 'Email is not valid.')
    } else {
        showSuccessText(emailEl);
        valid = true;
    }
    return valid;
};

const checkRole = () => {
    let valid = false;
    const role = roleEl.value.trim();
    if (!isRequired(role)) {
        showErrorText(roleEl, 'Role cannot be blank.');
    } else {
        showSuccessText(roleEl);
        valid = true;
    }
    return valid;
};

const checkState = () => {
    let valid = false;
    const state = stateEl.value.trim();
    if (!isRequired(state)) {
        showErrorText(stateEl, 'State cannot be blank.');
    } else {
        showSuccessText(stateEl);
        valid = true;
    }
    return valid;
};

const checkCity = () => {
    let valid = false;
    const city = cityEl.value.trim();
    if (!isRequired(city)) {
        showErrorText(cityEl, 'City cannot be blank.');
    } else {
        showSuccessText(cityEl);
        valid = true;
    }
    return valid;
};

const checkDob = () => {
    let valid = false;
    const dob = dobEl.value.trim();
    if (!isRequired(email)) {
        showErrorText(dobEl, 'DOB cannot be blank.');
    } else if (!isAgeGreaterThan18(dob)) {
        showErrorText(dobEl, 'Age should be greater than 18 years.')
    } else {
        showSuccessText(dobEl);
        valid = true;
    }
    return valid;
};

const checkMobileNumber = () => {
    let valid = false;

    var mobileNumber = mobileNumberEl.value.trim();
    mobileNumber = mobileNumber.replace(/\D/g, "").substring(0, 10);
    mobileNumberEl.value = mobileNumber;

    if (!isRequired(mobileNumber)) {
        showErrorText(mobileNumberEl, 'Mobile number cannot be blank.');
    } else if (!isPhoneNumberValid(mobileNumber)) {
        showErrorText(mobileNumberEl, 'Mobile number is not valid.')
    } else {
        showSuccessText(mobileNumberEl);
        valid = true;
    }
    return valid;
};


const checkZipCode = () => {
    let valid = false;

    var zipcode = zipcodeEl.value.trim();
    zipcode = zipcode.substring(0, 6);
    zipcodeEl.value = zipcode;

    if (!isRequired(zipcode)) {
        showErrorText(zipcodeEl, 'ZipCode cannot be blank.');
    } else if (!isPinCodeValid(zipcode)) {
        showErrorText(zipcodeEl, 'ZipCode is not valid.')
    } else {
        showSuccessText(zipcodeEl);
        valid = true;
    }
    return valid;
};

const checkAddress = () => {
    let valid = false;
    const min = 3,max = 100;

    var address = addressEl.value.trim();
    address = address.substring(0, 100);
    addressEl.value = address;

    if (!isRequired(address)) {
        showErrorText(addressEl, 'Address cannot be blank.');
    } else if (!isBetween(address.length, min, max)) {
        showErrorText(addressEl, 'Invalid address, Please provide valid address.')
    } else {
        showSuccessText(addressEl);
        valid = true;
    }
    return valid;
};

const isEmailValid = (email) => {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
};

const isUserNameValid = (username) => {
    const re = /^[a-zA-Z]*$/;
    return re.test(username);
};

const isPinCodeValid = (zipCode) => {
    const re = /^[1-9][0-9]{5}$/;
    return re.test(zipCode);
};

const isPhoneNumberValid = (mobileNumber) => {
    var re = /^\(?(\d{3})\)?[- ]?(\d{3})[- ]?(\d{4})$/;
    return re.test(mobileNumber);
};

const isRequired = value => value === '' ? false : true;
const isBetween = (length, min, max) => length < min || length > max ? false : true;

const isAgeGreaterThan18 = (dob) => {
  const today = new Date();
  const birthDate = new Date(dob);

  // Calculate the difference in years
  const age = today.getFullYear() - birthDate.getFullYear();

  // Check if the calculated age is greater than 18
  if (age > 18) {
    return true;
  } else if (age === 18) {
    // If age is exactly 18, check the months and days
    const todayMonth = today.getMonth();
    const birthMonth = birthDate.getMonth();

    if (birthMonth < todayMonth) {
      return true;
    } else if (birthMonth === todayMonth) {
      const todayDay = today.getDate();
      const birthDay = birthDate.getDate();

      if (birthDay <= todayDay) {
        return true;
      }
    }
  }

  return false;
};


const showErrorText = (input, message) => {
    // get the form-field element
    const formField = input.parentElement;
    // add the error class
    formField.classList.remove('success');
    formField.classList.add('error');

    // show the error message
    const error = formField.querySelector('small');
    error.textContent = message;
};

const showSuccessText = (input) => {
    // get the form-field element
    const formField = input.parentElement;

    // remove the error class
    formField.classList.remove('error');
    formField.classList.add('success');

    // hide the error message
    const error = formField.querySelector('small');
    error.textContent = '';
}

form.addEventListener('submit', function (e) {
    // prevent the form from submitting
    e.preventDefault();

    // validate fields
    let isFirstNameValid = checkFirstName(),
        isLastNameValid = checkLastName(),
        isRoleValid = checkRole(),
        isMobileNumber = checkMobileNumber(),
        isAgeValid = checkDob(),
        isEmailValid = checkEmail(),
        isAddressValid = checkAddress(),
        isZipCodeValid = checkZipCode(),
        isStateValid = checkState(),
        isCityValid = checkCity();

    let isFormValid = isFirstNameValid &&
        isLastNameValid &&
        checkRole &&
        isMobileNumber &&
        isAgeValid &&
        isEmailValid &&
        isAddressValid &&
        isZipCodeValid &&
        isStateValid &&
        isCityValid;

    // submit to the server if the form is valid
    if (isFormValid) {
        $("#addAssociate").attr('action', '/user/save');
        form.submit();
    }
});


const debounce = (fn, delay = 500) => {
    let timeoutId;
    return (...args) => {
        // cancel the previous timer
        if (timeoutId) {
            clearTimeout(timeoutId);
        }
        // setup a new timer
        timeoutId = setTimeout(() => {
            fn.apply(null, args)
        }, delay);
    };
};

form.addEventListener('input', debounce(function (e) {
    switch (e.target.id) {
        case 'firstName':
            checkFirstName();
            break;
        case 'lastName':
            checkLastName();
            break;
        case 'email':
            checkEmail();
            break;
        case 'mobileNumber':
            checkMobileNumber();
            break;
        case 'zipcode':
            checkZipCode();
            break;
    }
}));