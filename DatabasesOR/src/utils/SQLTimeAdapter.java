package utils;



import java.sql.Time;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class SQLTimeAdapter extends XmlAdapter<String, Time> {
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //TODO CHANGE TO TIME

	@Override//TODO ASK RODRIGO :)
	public String marshal(Time sqlTime) throws Exception {
		return sqlTime.toLocalTime().format(formatter);
	}

	@Override
	public Time unmarshal(String string) throws Exception {
		Time localTime = Time.valueOf(string); //TODO correct time adapter
		return localTime;
	}
}

