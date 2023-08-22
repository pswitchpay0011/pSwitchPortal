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

$('#masterDistributor').change(function() {

const params = {
};

ajaxCall("/user/distributors/"+$(this).val(), params, 'GET', function(data) {

    var html = '<option value="">Select Distributor</option>';
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

//    $.getJSON("/distributors/"+$(this).val(), {
//        ajax : 'true'
//    }, function(data) {
//        var html = '<option value="">Select Distributor</option>';
//        var len = data.length;
//
//        $.each(data, function(key, value) {
//            html += '<option value="' + key + '">'
//                                + value + '</option>';
//        });
//        html += '</option>';
//        $('#distributor').html(html);
//    });
});