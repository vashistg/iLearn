package com.gv.iLearn.web.utility;

import javax.servlet.http.HttpServletRequest;

public class OwnerUtility {

	public static String getIP(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}
}
