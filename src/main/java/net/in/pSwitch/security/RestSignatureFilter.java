package net.in.pSwitch.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.in.pSwitch.service.BinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//@Component
public class RestSignatureFilter extends OncePerRequestFilter {

	Logger logger = LoggerFactory.getLogger(RestSignatureFilter.class);
	@Autowired
	private BinderService binderService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("URL : ", request.getRequestURI());

		try {
			/*
			 * if (!SignatureHelper.validateSignature(url, signature, apiKey)) {
			 * response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
			 * "REST signature failed validation."); return; }
			 */
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"The REST Security Server experienced an internal error.");
			return;
		}
		filterChain.doFilter(request, response);
	}

}
