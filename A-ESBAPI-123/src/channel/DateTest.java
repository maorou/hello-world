package channel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
	public static void main(String[] args) throws Exception {
	Date date =new Date();
	DateFormat format=new SimpleDateFormat("yyyyMMdd,HHmmss");
	String time=format.format(date);
	String[] dt = time.split(",");
	//dt[1];
	}
}
