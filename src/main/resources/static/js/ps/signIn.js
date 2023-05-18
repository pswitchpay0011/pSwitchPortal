
const usernameEl = document.querySelector('#username');
const lUsernameEl = document.querySelector('#lusername');
const lPasswordEl = document.querySelector('#lpassword');

const emailEl = document.querySelector('#email');
const passwordEl = document.querySelector('#password');
const confirmPasswordEl = document.querySelector('#confirmPassword');
const mobileNumberEl = document.querySelector('#mobileNumber');
const latLngEl = document.querySelector('#latLng');

const updatePasswordForm = document.querySelector('#updatePasswordForm');
const loginForm = document.querySelector('#loginForm');


function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            $("#latLng").val(position.coords.latitude + "," + position.coords.longitude);
        });
    }else {
        x.innerHTML = "Geolocation is not supported by this browser.";
    }
}
getLocation();
const checkLatLng = () => {
    let valid = false;
    const latLng = latLngEl.value.trim();
    if (!isRequired(latLng)) {
        showError(latLngEl, 'Please enable location');
    } else if (!isLanLngValid(latLng)) {
        showError(latLngEl, 'Location is not valid.')
    } else {
        // showSuccessText(latLngEl);
        valid = true;
    }
    return valid;
};

const checkEmail = () => {
    let valid = false;
    const email = emailEl.value.trim();
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
const checkMobileNumber = () => {
    let valid = false;
    const mobileNumber = mobileNumberEl.value.trim();
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

const checkLUsername = () => {
    let valid = false;
    const username = lUsernameEl.value.trim();

    if (!isRequired(username)) {
        showErrorText(lUsernameEl, 'username cannot be blank.');
    } else {
        showSuccessText(lUsernameEl);
        valid = true;
    }

    return valid;
};

const checkPassword = () => {
    let valid = false;
    const password = passwordEl.value.trim();

    if (!isRequired(password)) {
        showErrorText(passwordEl, 'Password cannot be blank.');
    }else if (!isPasswordSecure(password)) {
        showErrorText(passwordEl, 'Password must has at least 8 characters that include at least 1 lowercase character, 1 uppercase characters, 1 number, and 1 special character in (!@#$%^&*)');
    }  else {
        showSuccessText(passwordEl);
        valid = true;
    }

    return valid;
};
const checkLPassword = () => {
    let valid = false;
    const password = lPasswordEl.value.trim();

    if (!isRequired(password)) {
        showErrorText(lPasswordEl, 'Password cannot be blank.');
    } else {
        showSuccessText(lPasswordEl);
        valid = true;
    }

    return valid;
};

const isLanLngValid = (latLng) => {
    const regexExp = /^((\-?|\+?)?\d+(\.\d+)?),\s*((\-?|\+?)?\d+(\.\d+)?)$/gi;
    return regexExp.test(latLng);
}
const isEmailValid = (email) => {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
};

const isPasswordSecure = (password) => {
    const re = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})");
    return re.test(password);
};

const isPhoneNumberValid = (mobileNumber) => {
    var re = /^\(?(\d{3})\)?[- ]?(\d{3})[- ]?(\d{4})$/;
    return re.test(mobileNumber);
};

const isRequired = value => value === '' ? false : true;
const isBetween = (length, min, max) => length < min || length > max ? false : true;


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

updatePasswordForm.addEventListener('submit', function (e) {
    // prevent the form from submitting
    e.preventDefault();

    // validate fields
    let
        isPasswordValid = checkPassword(),
        isLatLngValid = checkLatLng(),
        isConfirmPasswordValid = checkConfirmPassword();

    let isFormValid = isPasswordValid &&
        isLatLngValid &&
        isConfirmPasswordValid;

    // submit to the server if the form is valid
    if (isFormValid) {
        $("#updatePasswordForm").attr('action', '/account/updatePassword');
        updatePasswordForm.submit();
    }
});


loginForm.addEventListener('submit', function (e) {
    // prevent the form from submitting
    e.preventDefault();
    getLocation();
    // validate fields
    let
        isPasswordValid = checkLPassword(),
        isLatLngValid = checkLatLng(),
        isUsername = checkLUsername();

    let isFormValid = isPasswordValid &&
        isLatLngValid &&
        isUsername;

    // submit to the server if the form is valid
    if (isFormValid) {
        ajaxCall("/api/authenticate", JSON.stringify({
            "password": lPasswordEl.value.trim(),
            "latLng": latLngEl.value.trim(),
            "username": lUsernameEl.value.trim()
        }), 'POST', function(data) {
            if (data.error == true) {

            }else{
                setCookie("PSwitchToken", data.result.token, 90)
                localStorage.setItem("token", data.result.token);
                $.ajaxSetup({
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader('Authorization', "Bearer "+data.token);
                    }
                });

                window.location.replace("/index");
            }
        }, function(error) {}, function(complete) {});

        // $("#loginForm").attr('action', '/authenticate');
        // loginForm.submit();
    }
});

$(".toggle-password").click(function() {

  $(this).toggleClass("fa-eye fa-eye-slash");
  var input = $($(this).attr("toggle"));
  if (input.attr("type") == "password") {
    input.attr("type", "text");
  } else {
    input.attr("type", "password");
  }
});

const checkConfirmPassword = () => {
    let valid = false;
    // check confirm password
    const confirmPassword = confirmPasswordEl.value.trim();
    const password = passwordEl.value.trim();

    if (!isRequired(confirmPassword)) {
        showErrorText(confirmPasswordEl, 'Please enter the password again');
    } else if (password !== confirmPassword) {
        showErrorText(confirmPasswordEl, 'The password does not match');
    } else {
        showSuccessText(confirmPasswordEl);
        valid = true;
    }

    return valid;
};

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

updatePasswordForm.addEventListener('input', debounce(function (e) {
    switch (e.target.id) {
        case 'password':
            checkPassword();
            break;
        case 'confirmPassword':
            checkConfirmPassword();
            break;
    }
}));

loginForm.addEventListener('input', debounce(function (e) {
    switch (e.target.id) {
        case 'password':
            checkPassword();
            break;
        case 'confirmPassword':
            checkConfirmPassword();
            break;
    }
}));