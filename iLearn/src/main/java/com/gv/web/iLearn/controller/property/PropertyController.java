package com.gv.web.iLearn.controller.property;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gv.iLearn.web.utility.OwnerUtility;

@Controller
public class PropertyController {
	
	@RequestMapping(value="/property/{propertyName}")	
	public @ResponseBody String  getProperties(@PathVariable("propertyName") String propertyName, HttpServletRequest request, HttpServletResponse response) throws IOException{
		/**
		 * 1)Get the IP for the server from which response received.
		 * 2)Check for the relevant property variant that has to be served for this.
		 * 3)Get the relevant container from the DB.
		 * 4)Return the container.
		 */
		String props ="port=8080\r\n"
				+ "hostname=flights.makemytrip.com\r\n"
				+ "maxThreads=300%\r\n"
				+ "timeout=40\r\n"
				+ "IP="+OwnerUtility.getIP(request);
		
		if("spice".equals(propertyName)){
			props = "timeout=9999";
		}
		return props;
	}

}
