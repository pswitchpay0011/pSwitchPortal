package net.in.pSwitch.utility;

import net.in.pSwitch.model.LoginInfo;
import net.in.pSwitch.repository.LoginTrackerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@Service
public class LoginTracker {
    Logger logger = LoggerFactory.getLogger(LoginTracker.class);
    @Autowired
    private LoginTrackerRepository loginTracker;

    public void updateTracker(Integer userId, String userLatLng) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(userId);
        loginInfo.setUserLatLng(userLatLng);
        try {
            InetAddress address = InetAddress.getLocalHost();
            loginInfo.setUserIp(address.getHostAddress());
            System.out.println("IP address: " + address.getHostAddress());

            getClientMACAddress(address.getHostAddress());
            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
            for (; n.hasMoreElements(); ) {
                NetworkInterface e = n.nextElement();

                Enumeration<InetAddress> a = e.getInetAddresses();
                for (; a.hasMoreElements(); ) {
                    try {
                        InetAddress addr = a.nextElement();
                        if (!addr.isLinkLocalAddress()) {
                            loginInfo.setUserMac(getMacAddress(addr));
                            System.out.println("MAC address: " + getMacAddress(addr));
                        }
                    } catch (Exception e1) {
                        logger.error("Error: ", e1);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error: ", e);
        }
        loginTracker.save(loginInfo);
    }

    public String getClientMACAddress(String clientIp) {
        String str = "";
        String macAddress = "";
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -A " + clientIp);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf("MAC Address") > 1) {
                        macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        System.out.println("macAddress: " + macAddress);
        return macAddress;
    }

    private String getMacAddress(InetAddress inetAddress) throws SocketException {
        byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        return sb.toString();
    }

    public Page<LoginInfo> getAllLogin(Integer userId) {
        Sort.Order sortOrder = new Sort.Order((Sort.Direction.DESC), "modified_date");
        Sort sort = Sort.by(sortOrder);

        Pageable pageRequest = PageRequest.of(0, 10, sort);

        return loginTracker.findAllByUserId(userId, pageRequest);
    }
}
