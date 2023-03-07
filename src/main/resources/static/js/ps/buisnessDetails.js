
    $('#country').change(
        function() {
            $.getJSON("/states", {
                countryId : $(this).val(),
                ajax : 'true'
            }, function(data) {
                var html = '<option value="">Select State</option>';
                var len = data.length;
                for ( var i = 0; i < len; i++) {
                    html += '<option value="' + data[i].id + '">'
                            + data[i].name + '</option>';
                }
                html += '</option>';
                $('#state').html(html);
            });
        });

 $('#state').change(
        function() {
            $.getJSON("/cities", {
                stateId : $(this).val(),
                ajax : 'true'
            }, function(data) {
                var html = '<option value="">Select City</option>';
                var len = data.length;
                for ( var i = 0; i < len; i++) {
                    html += '<option value="' + data[i].id + '">'
                            + data[i].name + '</option>';
                }
                html += '</option>';
                $('#city').html(html);
            });
        });