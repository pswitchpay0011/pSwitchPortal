$('#role').change(function() {
const params = {
};

ajaxCall($(this).val() == 'R' ? "/fund/retailer/": "/fund/distributors/", params, 'GET', function(data) {
    var html = '<option value="">None</option>';
    $.each(data, function(key, value) {
        html += '<option value="' + key + '">'
                            + value + '</option>';
    });
    html += '</option>';
    $('#toUser').html(html);

    $("body").removeClass("loading");

    }, function(error) {}, function(complete) {
        $("#zipSpinner").hide();
    });
});

const roleEl = document.querySelector('#role');
const toUserEl = document.querySelector('#toUser');
const amountEl = document.querySelector('#amount');
const form = document.querySelector('#fundTransfer');

form.addEventListener('submit', function (e) {
    // prevent the form from submitting
    e.preventDefault();

    // validate fields
    let isRoleValid = checkRole(),
        isReceiverValid = checkToUser(),
        isAmount = checkAmount();

    let isFormValid = isRoleValid &&
        isReceiverValid &&
        isAmount;

    // submit to the server if the form is valid
    if (isFormValid) {
        $("#fundTransfer").attr('action', '/fund/transfer');
        form.submit();
    }
});

const isRequired = value => value === '' ? false : true;

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


function formatAmount() {
    var value = amountEl.value.toUpperCase();
    value = value.replace(/[^0-9.]/g, "");
    const parts = value.split(".");
    if (parts.length > 2) {
        value = parts[0] + "." + parts.slice(1).join("");
    }
    amountEl.value = value;
}

const checkToUser = () => {
    let valid = false;
    const toUser = toUserEl.value.trim();
    if (!isRequired(toUser)) {
        showErrorText(toUserEl, 'Receiver cannot be blank.');
    } else {
        showSuccessText(toUserEl);
        valid = true;
    }
    return valid;
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


const checkAmount = () => {
    let valid = false;
    const amount = amountEl.value.trim();
    if (!isRequired(amount)) {
        showErrorText(amountEl, 'Amount cannot be blank.');
    } else {
        showSuccessText(amountEl);
        valid = true;
    }
    return valid;
};