package net.in.pSwitch.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class TestController {

	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getDataForDatatable() {
		return "Hello ";
	}

}
