/*****************************************************************************************
 * Source File: DisplayUtil.java
 ****************************************************************************************/
package net.ruready.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Formatting and massaging utils, mostly for web display.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 2006-07
 *         Continuing Education , University of Utah . All copyrights reserved. U.S.
 *         Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 10, 2007
 */
public class DisplayUtil implements Utility
{
	// ========================= CONSTANTS =================================

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(DisplayUtil.class.getName());

	public static final String PRETTY_DATE_FORMAT = "MMM dd, yyyy hh:mm a";

	public static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("h:mm a");

	public static final SimpleDateFormat PRETTY_DATE_FORMATTER = new SimpleDateFormat(
			PRETTY_DATE_FORMAT);

	public static final SimpleDateFormat MONDDYYYY_DATE_FORMATTER = new SimpleDateFormat(
			"MMM dd, yyyy");

	public static final boolean LAST_NAME_FIRST = true;

	public static final boolean FIRST_NAME_FIRST = false;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private DisplayUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Pretty-print formatter for removal of all but the last term of a Classname. For
	 * example, change "com.foo.Bar" to just "Bar".
	 * 
	 * @param object
	 *            the Object you want to format
	 * @return a String with the just the last term
	 */
	public static String niceClassName(Object object)
	{
		if (object == null)
		{
			return CommonNames.MISC.EMPTY_STRING;
		}
		return niceClassName(object.getClass());
	}

	/**
	 * Pretty-print formatter for removal of all but the last term of a Classname. For
	 * example, change "com.foo.Bar" to just "Bar".
	 * 
	 * @param clazz
	 *            the Class you want to format
	 * @return a String with the just the last term
	 */
	public static String niceClassName(Class<?> clazz)
	{
		if (clazz == null)
		{
			return CommonNames.MISC.EMPTY_STRING;
		}

		String clazzName = clazz.getName();
		int lastDot = clazzName.lastIndexOf(".");
		if (lastDot >= 0)
		{
			return clazzName.substring(lastDot + 1);
		}
		return clazzName;
	}

	/**
	 * Convert Double to currency format
	 */
	public static String formatCurrency(Double doubleVal)
	{
		if (doubleVal == null)
		{
			return CommonNames.MISC.EMPTY_STRING;
		}
		return formatCurrency(doubleVal.doubleValue());
	}

	/**
	 * Convert BigDecimal to currency format
	 */
	public static String formatCurrency(BigDecimal bigDecimal)
	{
		if (bigDecimal == null)
		{
			return CommonNames.MISC.EMPTY_STRING;
		}
		return formatCurrency(bigDecimal.doubleValue());
	}

	/**
	 * Convert double to currency format
	 */
	public static String formatCurrency(double doubleVal)
	{
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
		String currency = currencyFormat.format(doubleVal);
		// take off the $ sign
		return currency.replaceAll("\\$", CommonNames.MISC.EMPTY_STRING);
	}

	/**
	 * Format decimal number with given pattern
	 */
	public static String formatDecimal(double doubleVal, String pattern)
	{
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(doubleVal);
	}

	/**
	 * Format decimal number with fixed pattern
	 */
	public static String formatDecimal(double doubleVal)
	{
		return formatDecimal(doubleVal, "#,##0.00");
	}

	/**
	 * Format decimal number with fixed pattern
	 */
	public static String formatDecimal(Double doubleVal)
	{
		if (doubleVal == null)
		{
			return CommonNames.MISC.EMPTY_STRING;
		}
		return formatDecimal(doubleVal.doubleValue());
	}

	/**
	 * Convert Double to percent format
	 */
	public static String formatPercent(Double doubleVal)
	{
		if (doubleVal == null)
		{
			return CommonNames.MISC.EMPTY_STRING;
		}
		return formatPercent(doubleVal.doubleValue());
	}

	/**
	 * Convert double to percent format
	 */
	public static String formatPercent(double doubleval)
	{
		NumberFormat percentFormat = NumberFormat.getPercentInstance(Locale.US);
		percentFormat.setMaximumFractionDigits(2);
		return percentFormat.format(doubleval / 100);
	}

	/**
	 * Convert Integer to percent format
	 */
	public static String formatPercent(Integer intval)
	{
		if (intval == null)
		{
			return CommonNames.MISC.EMPTY_STRING;
		}
		Double tempDouble = new Double(intval.toString());
		return formatPercent(tempDouble);
	}

	/**
	 * Convert int to percent format
	 */
	public static String formatPercent(int intval)
	{
		// NumberFormat percentFormat =
		// NumberFormat.getPercentInstance(Locale.US);
		Double tempDouble = new Double(new Integer(intval).toString());
		return formatPercent(tempDouble);
		// percentFormat.setMaximumFractionDigits(2);
		// return percentFormat.format(intval/100);
	}

	/**
	 * Convert a true/false to yes/no string
	 */
	public static String booleanToYesNo(Boolean trueFalse)
	{
		if (trueFalse == null)
		{
			return CommonNames.MISC.EMPTY_STRING;
		}
		return booleanToYesNo(trueFalse.booleanValue());
	}

	/**
	 * Convert a true/false to yes/no string
	 */
	public static String booleanToYesNo(boolean truefalse)
	{
		if (truefalse == true)
		{
			return "yes";
		}
		return "no";
	}

	/**
	 * Trim a string to length, with ellipsis, if necessary.
	 * 
	 * @param input
	 *            input string
	 * @param length
	 *            maximum length
	 * @return input, if <= length, or a substring with ellipsis
	 */
	public static String trim(String input, int length)
	{
		if (input == null)
		{
			return CommonNames.MISC.EMPTY_STRING;
		}
		if (input.length() <= length)
		{
			return input;
		}
		return input.substring(0, length - 3) + "...";
	}
}
