const firstNameEl = document.querySelector('#firstName');
const lastNameEl = document.querySelector('#lastName');
const emailEl = document.querySelector('#email');
const passwordEl = document.querySelector('#password');
const confirmPasswordEl = document.querySelector('#confirmPassword');
const mobileNumberEl = document.querySelector('#mobileNumber');

const form = document.querySelector('#signup');


const checkFirstName = () => {
    let valid = false;
    const min = 3,
        max = 25;

    const username = firstNameEl.value.trim();

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
        max = 25;

    const username = lastNameEl.value.trim();

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

const checkPassword = () => {
    let valid = false;
    const password = passwordEl.value.trim();

    if (!isRequired(password)) {
        showErrorText(passwordEl, 'Password cannot be blank.');
    } else if (!isPasswordSecure(password)) {
        showErrorText(passwordEl, 'Password must has at least 8 characters that include at least 1 lowercase character, 1 uppercase characters, 1 number, and 1 special character in (!@#$%^&*)');
    } else {
        showSuccessText(passwordEl);
        valid = true;
    }

    return checkConfirmPassword();
};

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

const isEmailValid = (email) => {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
};

const isUserNameValid = (username) => {
    const re = /^[a-zA-Z]*$/;
    return re.test(username);
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

$(".toggle-password").click(function() {

  $(this).toggleClass("fa-eye fa-eye-slash");
  var input = $($(this).attr("toggle"));
  if (input.attr("type") == "password") {
    input.attr("type", "text");
  } else {
    input.attr("type", "password");
  }
});

form.addEventListener('submit', function (e) {
    // prevent the form from submitting
    e.preventDefault();

    // validate fields
    let isFirstNameValid = checkFirstName(),
        isLastNameValid = checkLastName(),
        isEmailValid = checkEmail(),
        isPasswordValid = checkPassword(),
        isMobileNumber = checkMobileNumber(),
        isConfirmPasswordValid = checkConfirmPassword();

    let isFormValid = isFirstNameValid &&
        isLastNameValid &&
        isEmailValid &&
        isMobileNumber &&
        isPasswordValid &&
        isConfirmPasswordValid;

    // submit to the server if the form is valid
    if (isFormValid) {
        // ajaxCall("/api/registration", JSON.stringify({
        //     "password": lPasswordEl.value.trim(),
        //     "latLng": latLngEl.value.trim(),
        //     "username": lUsernameEl.value.trim()
        // }), 'POST', function(data) {
        //     if (data.error == true) {
        //
        //     }else{
        //         setCookie("PSwitchToken", data.result.token, 90)
        //         localStorage.setItem("token", data.result.token);
        //         $.ajaxSetup({
        //             beforeSend: function(xhr) {
        //                 xhr.setRequestHeader('Authorization', "Bearer "+data.token);
        //             }
        //         });
        //
        //         window.location.replace("/verification");
        //     }
        // }, function(error) {}, function(complete) {});
        $("#signup").attr('action', '/account/registration');
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
        case 'password':
            checkPassword();
            break;
        case 'confirmPassword':
            checkConfirmPassword();
            break;
    }
}));