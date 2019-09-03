import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间日期帮助类
 * @author Administrator
 *
 */
public class ClientTimeUtils {
	public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyyMMddHHmmssS = "yyyyMMddHHmmssS";

	public static String getNowTimeyyyyMMddHHmmssS() {
		return formatDate(new Date(), ClientTimeUtils.yyyyMMddHHmmssS);
	}

	/**
	 * date转string
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String formatDate(Date date, String formatStr) {
		return (new SimpleDateFormat((formatStr == null ? DEFAULT_FORMAT_DATE
				: formatStr))).format(date);
	}


}
