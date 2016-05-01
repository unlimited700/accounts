package com.apnavaidya.treasure.accounts.v1.service;

import java.util.Date;

public interface DateTimeService {

	public String getCurrentTime(String timeZone);

	public String getTimeInAmPmFormat(Date date);

	public String getDate(String date);

}