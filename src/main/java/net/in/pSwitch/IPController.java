package net.in.pSwitch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class IPController {

    @GetMapping("/getIPs")
    public IPAddresses getIPs(HttpServletRequest request) throws UnknownHostException {
        String clientIp = getClientIP(request);
        String serverIp = getServerIP();
        return new IPAddresses(clientIp, serverIp);
    }

    private String getClientIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    private String getServerIP() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        return inetAddress.getHostAddress();
    }

    public static class IPAddresses {
        private String clientIP;
        private String serverIP;

        public IPAddresses(String clientIP, String serverIP) {
            this.clientIP = clientIP;
            this.serverIP = serverIP;
        }

        public String getClientIP() {
            return clientIP;
        }

        public String getServerIP() {
            return serverIP;
        }
    }
}

