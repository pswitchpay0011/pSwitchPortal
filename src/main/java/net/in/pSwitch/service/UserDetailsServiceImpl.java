package net.in.pSwitch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.in.pSwitch.model.UserInfo;
import net.in.pSwitch.repository.UserInfoRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UtilServiceImpl utilService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserInfo userInfo = userInfoRepository.findUserForLogin(username);
            if (userInfo == null) {
                throw new UsernameNotFoundException("User not found!");
            }
//            userInfo.setPwd(utilService.decodedData(userInfo.getPwd()));
            return new UserDetailsImpl(userInfo);
        }catch (Exception e){
            throw new UsernameNotFoundException("Internal Error! Please try again later");
        }
    }
}
