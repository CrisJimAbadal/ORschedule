package utils;



import java.sql.Time;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class SQLTimeAdapter extends XmlAdapter<String, Time> {
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm"); //TODO ask if it is correct

	@Override
	public String marshal(Time sqlTime) throws Exception {
		return sqlTime.toLocalTime().format(formatter);
	}

	@Override
	public Time unmarshal(String string) throws Exception {
		Time localTime = Time.valueOf(string); 
		return localTime;
	}
}

