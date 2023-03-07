function ajaxCall(url, params, type, ajxSuccessFn, ajxFailFn, ajxCompleteFn) {

    // timeOut = timeOut === "" ||
    // timeOut === "undefined" ||
    // timeOut === null ? 6000 : timeOut;
    type = type.toUpperCase() != "GET" &&
    type.toUpperCase() != "POST" &&
    type.toUpperCase() != "PUT" &&
    type.toUpperCase() != "DELETE" ? "POST" : type;

    $.ajax({
        type: type.toUpperCase(),
        cache: false,
        contentType: "application/json; charset=utf-8",
        url: url,
        data: params,
        beforeSend: function() {
            $("body").addClass("loading");
            $("#errorAlert").hide();
            $("#successAlert").hide();
        },
        success: function(result) {
            console.log("#.1 ------ start of ajax success -----");

            if (result.message != "" && result.message!=null) {
                if (result.error === true) {

                    showError(result.message);
                } else {
                    showSuccess(result.message);
                }
            }

            // if (arguments.length > 4) {
                var successFn = partial(ajxSuccessFn, result);
                successFn();
            // }

            // console.log("#.2 ------ end of ajax success -----");
        },
        error: function(xhr, ajaxOptions, thrownError) {

            // if (arguments.length > 5) {
                var errorFn = partial(ajxFailFn, xhr, thrownError);
                errorFn();
            // }

            // console.log("\n\t*** ajax call failure! : (readyState: " +
            //     xhr.readyState + " - status: " + xhr.status +" "+ xhr.statusText +
            //     ") Error Message: " + thrownError.message + " ***");
        },
        complete :  function() {
            $("body").removeClass("loading");
            console.log("ajax call completed");
            var completeFn = partial(ajxCompleteFn);
            completeFn();
        }
        // timeout: timeOut
    });
}
//*************** Partial Definition ************/

function showSuccess(message){
    $("#errorAlert").hide();
    $("#successAlert").show();
    $('#successMessage').text(message);
}
function showError(message){
    $("#errorAlert").show();
    $("#successAlert").hide();
    $('#errorMessage').text(message);
}

function setCookie(cName, cValue, timeInMinutes) {
    const d = new Date();
    d.setTime(d.getTime() + (timeInMinutes * 60 * 1000));
    let expires = "expires="+d.toUTCString();
    document.cookie = cName + "=" + cValue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    let name = cname + "=";
    let ca = document.cookie.split(';');
    for(let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function partial(fn) {
    var args = Array.prototype.slice.call(arguments);
    args.shift();
    return function() {
        var new_args = Array.prototype.slice.call(arguments);
        args = args.concat(new_args);
        return fn.apply(window, args);
    };
}
$p = partial;