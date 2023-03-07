$(function() {
			// Initialize form validation on the registration form.
			// It has the name attribute "registration"
			$("form[name='updatePasswordForm']")
					.validate(
							{
								// Specify validation rules
								rules : {
									// The key name on the left side is the name attribute
									// of an input field. Validation rules are defined
									// on the right side
									/* firstname : "required",
									lastname : "required",
									email : {
										required : true,
										// Specify that email should be validated
										// by the built-in "email" rule
										email : true
									}, */
									newPassword : {
										required : true,
										minlength : 5
									},
									confirmPassword : {
										required : true,
										minlength : 5,
										equalTo : "#newPassword"
									}
								},
								// Specify validation error messages
								messages : {
									//firstname : "Please enter your firstname",
									//lastname : "Please enter your lastname",
									password : {
										required : "Please provide a password",
										minlength : "Your password must be at least 5 characters long"
									},confirmPassword : {
										required : "Please provide a password",
										minlength : "Your password must be at least 5 characters long",
										equalTo : "Password and confirm password fields do not match"
									}
									//email : "Please enter a valid email address"
								},
								// Make sure the form is submitted to the destination defined
								// in the "action" attribute of the form when valid
								submitHandler : function(form) {
									form.submit();
								}
							});
		});