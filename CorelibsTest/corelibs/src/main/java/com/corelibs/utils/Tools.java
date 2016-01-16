package com.corelibs.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * 
 * @author TangWei 2013-10-24上午10:38:01
 */
public class Tools {

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 格式化价格
	 * 
	 * @param argStr
	 * @return
	 */
	public static String getFloatDotStr(String argStr) {
		float arg = Float.valueOf(argStr);
		DecimalFormat fnum = new DecimalFormat("##0.00");
		return fnum.format(arg);
	}

	public static boolean IsHaveInternet(final Context context) {
		try {
			ConnectivityManager manger = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manger.getActiveNetworkInfo();
			return (info != null && info.isConnected());
		} catch (Exception e) {
			return false;
		}
	}

	// 得到versionName
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;

	}

	public static String millisToString(long millis) {
		boolean negative = millis < 0;
		millis = Math.abs(millis);

		millis /= 1000;
		int sec = (int) (millis % 60);
		millis /= 60;
		int min = (int) (millis % 60);
		millis /= 60;
		int hours = (int) millis;

		String time;
		DecimalFormat format = (DecimalFormat) NumberFormat
				.getInstance(Locale.US);
		format.applyPattern("00");
		if (millis > 0) {
			time = (negative ? "-" : "")
					+ (hours == 0 ? 00 : hours < 10 ? "0" + hours : hours)
					+ ":" + (min == 0 ? 00 : min < 10 ? "0" + min : min) + ":"
					+ (sec == 0 ? 00 : sec < 10 ? "0" + sec : sec);
		} else {
			time = (negative ? "-" : "") + min + ":" + format.format(sec);
		}
		return time;
	}

	// 得到versionName
	public static int getVerCode(Context context) {
		int verCode = 0;
		try {
			verCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verCode;

	}

	/**
	 * 判断 多个字段的值否为空
	 * 
	 * @author Michael.Zhang 2013-08-02 13:34:43
	 * @return true为null或空; false不null或空
	 */
	public static boolean isNull(String... ss) {
		for (int i = 0; i < ss.length; i++) {
			if (null == ss[i] || ss[i].equals("")
					|| ss[i].equalsIgnoreCase("null")) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断 一个字段的值否为空
	 * 
	 * @author Michael.Zhang 2013-9-7 下午4:39:00
	 * @param s
	 * @return
	 */
	public static boolean isNull(String s) {
		if (null == s || s.equals("") || s.equalsIgnoreCase("null")) {
			return true;
		}

		return false;
	}

	/**
	 * 判断sd卡是否存在
	 * 
	 * @author Michael.Zhang 2013-07-04 11:30:54
	 * @return
	 */
	public static boolean judgeSDCard() {
		String status = Environment.getExternalStorageState();
		return status.equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 显示纯汉字的星期名称
	 * 
	 * @author TangWei 2013-10-25上午11:31:51
	 * @param i
	 *            星期：1,2,3,4,5,6,7
	 * @return
	 */
	public static String changeWeekToHanzi(int i) {
		switch (i) {
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		case 7:
			return "星期日";
		default:
			return "";
		}
	}

	/**
	 * 验证手机号码
	 * 
	 * @author TangWei
	 * @param phone
	 * @return
	 */
	public static boolean validatePhone(String phone) {
		Pattern p = Pattern
				.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
		Matcher matcher = p.matcher(phone);
		return matcher.matches();
	}

	public static boolean validateLoginPassWord(String pwd) {
		if (isNull(pwd))
			return false;
		String pattern = "[a-zA-Z0-9]{6,16}";
		return pwd.matches(pattern);

	}

	/**
	 * 检验用户名 可以输入a到z 0到9 汉字的3到8位字符
	 * 
	 * @param pwd
	 * @return
	 */
	public static boolean validateUserName(String pwd) {
		if (isNull(pwd))
			return false;
		String pattern = "[a-zA-Z0-9\u4E00-\u9FA5]{3,8}";
		return pwd.matches(pattern);

	}

	/**
	 * 检查身份证是 否合法,15位或18位(或者最后一位为X)
	 * 
	 * @param text
	 * @return
	 */
	public static boolean validateIdCard(String idCard) {
		if (isNull(idCard)) {
			return false;
		}
		return idCard.matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");
	}

	/**
	 * 简单的验证一下银行卡号
	 * 
	 * @param bankCard
	 *            信用卡是16位，其他的是13-19位
	 * @return
	 */
	public static boolean validateBankCard(String bankCard) {
		if (isNull(bankCard))
			return false;
		String pattern = "^\\d{13,19}$";
		return bankCard.matches(pattern);
	}

	/**
	 * 验证邮箱
	 * 
	 * @author TangWei 2013-12-13下午2:33:16
	 * @param email
	 * @return
	 */
	public static boolean validateEmail(String email) {
		if (isNull(email))
			return false;
		String pattern = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
		return email.matches(pattern);
	}

	/**
	 * 将100以内的阿拉伯数字转换成中文汉字（15变成十五）
	 * 
	 * @param round
	 *            最大值50
	 * @return >99的，返回“”
	 */
	public static String getHanZi1(int round) {
		if (round > 99 || round == 0) {
			return "";
		}
		int ge = round % 10;
		int shi = (round - ge) / 10;
		String value = "";
		if (shi != 0) {
			if (shi == 1) {
				value = "十";
			} else {
				value = getHanZi2(shi) + "十";
			}

		}
		value = value + getHanZi2(ge);
		return value;
	}

	/**
	 * 将0-9 转换为 汉字（ _一二三四五六七八九）
	 * 
	 * @param round
	 * @return
	 */
	public static String getHanZi2(int round) {
		String[] value = { "", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		return value[round];
	}

	/**
	 * 将服务器返回的日期转换为固定日期
	 * 
	 * @param str
	 * @return
	 */
	public static String convertoZPMTime(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String result = "";
		if (date != null) {
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy.M.dd");
			result = format2.format(date);
		}

		return result;

	}

	/**
	 * 将服务器返回的日期转换为优惠券需要显示的日期
	 * 
	 * @param str
	 * @return
	 */
	public static String convertoCouponTime(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String result = "";
		if (date != null) {
			SimpleDateFormat format2 = new SimpleDateFormat("M/dd");
			result = format2.format(date);
		}

		return result;

	}

}
