package net.in.pSwitch.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PSwitchErrorController implements ErrorController {

	private static Logger logger = LoggerFactory.getLogger(PSwitchErrorController.class);

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		try {
			Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

			if (status != null) {
				Integer statusCode = Integer.valueOf(status.toString());

				if (statusCode == HttpStatus.NOT_FOUND.value()) {

					try {
						if(request.getRequestURI().contains("logout")){
							logger.info("logout redirected....");
							return "redirect:/login";
						}
					}catch (Exception e){

					}

					return "error/error_404";
				} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
					return "error/error_500";
				} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
					return "redirect:/login";
				} else if (statusCode == HttpStatus.BAD_GATEWAY.value()) {
					return "error/error_503";
				} else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
					return "error/error_503";
				}
			}
		} catch (Exception e) {
			logger.error("Error:", e);
		}
		return "redirect:/index";
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String exception(final Throwable throwable, final Model model) {
		try {
			logger.error("Exception during execution of SpringSecurity application", throwable);
			String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
			model.addAttribute("errorMessage", errorMessage);
			return "error/error_500";
		} catch (Exception e) {
			logger.error("Error:", e);
		}

		return "redirect:/index";
	}

	@Override
	public String getErrorPath() {
		return null;
	}
}
