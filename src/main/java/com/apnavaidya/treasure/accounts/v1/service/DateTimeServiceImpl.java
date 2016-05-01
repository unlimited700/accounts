package com.apnavaidya.treasure.accounts.v1.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

@Service
public class DateTimeServiceImpl implements DateTimeService {

	@Override
	public String getCurrentTime(String timeZone) {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date date = new Date();
		int zone = 33;
		if (timeZone.toUpperCase().equalsIgnoreCase("IST"))
			zone = 0;
		else if (timeZone.toUpperCase().equalsIgnoreCase("GMT"))
			zone = 1;

		switch (zone) {
		case 0:
			dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
			break;
		case 1:
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			break;
		default:
			dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
		}
		return dateFormat.format(date);
	}

	@Override
	public String getDate(String date) {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date1 = null;

		try {
			date1 = dateFormat.parse(date);
			return dateFormat.format(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	@Override
	public String getTimeInAmPmFormat(Date date) {
		String time = null;
		DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
		time = dateFormat.format(date).toString();
		return time;
	}

}