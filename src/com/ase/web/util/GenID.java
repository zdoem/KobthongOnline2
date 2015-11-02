package com.ase.web.util;
import java.util.Random;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.security.SecureRandom;

/**
 * Classname : GenID
 * 
 * Description : Generate unique id (lengths of number is 16)
 * 
 * 
 * 
 * Example usage: GenID hGenid = GenID.getInstance(); String strUid =
 * hGenid.getId();
 * 
 * 
 * @version 1.0
 */
public class GenID {

	private static GenID Myself;
	private static Random MyRand;
	private static SecureRandom MySecureRand;
	private String strGen_id;

	static {
		MySecureRand = new SecureRandom();
		long nSecureInitializer = MySecureRand.nextLong();
		MyRand = new Random(nSecureInitializer);
	}

	/**
	 * get date and month
	 */
	private static String getDateMonth() {
		Calendar calendar = new GregorianCalendar();
		return (Integer.toString(calendar.get(Calendar.DATE)) + Integer
				.toString(calendar.get(Calendar.MONTH)));
	}

	/**
	 * get random long
	 */
	private static String getRandLong() {
		long nRrand = MySecureRand.nextLong();
		if (nRrand < 0) {
			nRrand = nRrand * (-1);
		}
		return (Long.toString(nRrand));
	}

	/**
	 * get current time
	 */
	private static String getCurrentTime() {
		long nTime = System.currentTimeMillis();
		return (Long.toString(nTime));
	}

	/**
	 * scamble 1. random long (2 to 7) and reverse 2. current time (4 to 9) and
	 * reverse 3. date time (0 to 1)
	 */
	private static String JScamble() {
		StringBuffer strScam_rand = new StringBuffer(getRandLong().substring(2,
				8));
		StringBuffer strScam_ctime = new StringBuffer(getCurrentTime()
				.substring(4, 10));
		StringBuffer strScam_dmy = new StringBuffer(getDateMonth().substring(0,
				2));
		return (strScam_dmy.toString() + strScam_ctime.reverse().toString() + strScam_rand
				.reverse().toString());
	}

	private GenID() {
		strGen_id = null;
	}

	/**
	 * Generate unique id
	 * 
	 * @return String (lengths=14)
	 * @see java.lang.String
	 */
	public String getId() {
		strGen_id = JScamble();
		return strGen_id;
	}

	/**
	 * The default method used to retrieve an instance of GenID,
	 */
	public static GenID getInstance() {
		if (Myself == null) {
			synchronized (GenID.class) {
				Myself = new GenID();
			}
		}
		return Myself;
	}
}
