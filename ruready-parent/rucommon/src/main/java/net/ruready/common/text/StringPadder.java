/*******************************************************
 * Source File: StringPadder.java
 *******************************************************/
package net.ruready.common.text;

import java.text.*;

import net.ruready.common.misc.Auxiliary;

/**
 * Provides left pad and right pad functionality for Strings.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 10, 2007
 */
public class StringPadder implements Auxiliary
{
	// ========================= CONSTANTS =================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private StringPadder()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * A method to left pad a string with a given string to a given size. This
	 * method will repeat the padder string as many times as is necessary until
	 * the exact specified size is reached. If the specified size is less than
	 * the size of the original string then the original string is returned
	 * unchanged. Example1 - original string "cat", padder string "white", size
	 * 8 gives "whitecat". Example2 - original string "cat", padder string
	 * "white", size 15 gives "whitewhitewhcat". Example3 - original string
	 * "cat", padder string "white", size 2 gives "cat".
	 * 
	 * @param stringToPad
	 *            The original string
	 * @param padder
	 *            The string to pad onto the original string
	 * @param size
	 *            The required size of the new string
	 * @return String the newly padded string
	 */
	public static String leftPad(String stringToPad, String padder, int size)
	{
		if (padder.length() == 0) {
			return stringToPad;
		}
		StringBuffer strb = new StringBuffer(size);
		StringCharacterIterator sci = new StringCharacterIterator(padder);

		while (strb.length() < (size - stringToPad.length())) {
			for (char ch = sci.first(); ch != CharacterIterator.DONE; ch = sci.next()) {
				if (strb.length() < size - stringToPad.length()) {
					strb.insert(strb.length(), String.valueOf(ch));
				}
			}
		}
		return strb.append(stringToPad).toString();
	}

	/**
	 * method to right pad a string with a given string to a given size. This
	 * method will repeat the padder string as many times as is necessary until
	 * the exact specified size is reached. If the specified size is less than
	 * the size of the original string then the original string is returned
	 * unchanged. Example1 - original string "cat", padder string "white", size
	 * 8 gives "catwhite". Example2 - original string "cat", padder string
	 * "white", size 15 gives "catwhitewhitewh". Example3 - original string
	 * "cat", padder string "white", size 2 gives "cat".
	 * 
	 * @param stringToPad
	 *            The original string
	 * @param padder
	 *            The string to pad onto the original string
	 * @param size
	 *            The required size of the new string
	 * @return String the newly padded string
	 */
	public static String rightPad(String stringToPad, String padder, int size)
	{
		if (padder.length() == 0) {
			return stringToPad;
		}
		StringBuffer strb = new StringBuffer(stringToPad);
		StringCharacterIterator sci = new StringCharacterIterator(padder);

		while (strb.length() < size) {
			for (char ch = sci.first(); ch != CharacterIterator.DONE; ch = sci.next()) {
				if (strb.length() < size) {
					strb.append(String.valueOf(ch));
				}
			}
		}
		return strb.toString();
	}
}
