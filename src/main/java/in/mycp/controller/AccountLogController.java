/*
 mycloudportal - Self Service Portal for the cloud.
 Copyright (C) 2012-2013 Mycloudportal Technologies Pvt Ltd

 This file is part of mycloudportal.

 mycloudportal is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 mycloudportal is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with mycloudportal.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.mycp.controller;

import in.mycp.domain.AccountLog;
import in.mycp.domain.AccountLogTypeDTO;
import in.mycp.domain.Asset;
import in.mycp.domain.Company;
import in.mycp.domain.Department;
import in.mycp.domain.Project;
import in.mycp.domain.User;
import in.mycp.remote.AccountLogService;
import in.mycp.remote.ReportService;
import in.mycp.utils.Commons;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Charudath Doddanakatte
 * @author cgowdas@gmail.com
 *
 */

@RequestMapping("/log")
@Controller
public class AccountLogController {

	private static final Logger log = Logger.getLogger(AccountLogController.class.getName());

	@Autowired
	AccountLogService accountLogService;

	@RequestMapping(value = "/account", produces = "text/html")
	public String session(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("sessionLogList", accountLogService.getLast24HoursLog());
		return "log/accountLog";
	}
	
	@RequestMapping(value = "/filterLog", produces = "text/html")
	public String getFilterLog(HttpServletRequest req, HttpServletResponse resp) {
		String s = req.getParameter("filterName");
		//System.out.println(" s= "+s);
		if(StringUtils.isNotBlank(s) && s.equals("last 7 days")){
		
			req.setAttribute("sessionLogList", accountLogService.getLast7DaysLog());
		}else if(StringUtils.isNotBlank(s) && s.equals("last 24 Hours")){
			req.setAttribute("sessionLogList", accountLogService.getLast24HoursLog());
		}else if(StringUtils.isNotBlank(s)){
			req.setAttribute("sessionLogList", accountLogService.getLog4Month(s));
		}else {
			req.setAttribute("sessionLogList", accountLogService.getLast24HoursLog());
		}
		req.setAttribute("filter", req.getParameter("accountLogType"));
		return "log/accountLog";
	}

}//end of class
