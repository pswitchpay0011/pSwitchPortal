package net.in.pSwitch.api;

import net.in.pSwitch.model.response.MenuResponse;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.service.AuthService;
import net.in.pSwitch.service.EmailService;
import net.in.pSwitch.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserApiController {

	@Autowired
	private EmailService emailService;

	@Autowired
	AuthService authService;
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private MenuService menuService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping("/menu/role/{role}")
	@ResponseBody
	public ResponseEntity<List<MenuResponse>> getAllMenu(@PathVariable("role") String role){
		return ResponseEntity.status(HttpStatus.OK).body(menuService.getMenuByRole(role).stream().map(post -> modelMapper.map(post, MenuResponse.class))
				.collect(Collectors.toList()));
	}

}
