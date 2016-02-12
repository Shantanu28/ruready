/*****************************************************************************************
 * Source File: ArrayUtil.java
 ****************************************************************************************/
package net.ruready.common.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;

/**
 * Utilities regarding arrays (Object[]) and lists ({@link List}).
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 16, 2007
 */
public class ArrayUtil implements Utility
{
	// ========================= CONSTANTS =================================

	public static final String[] EMPTY_STRING_ARRAY =
	{};

	public static final int[] EMPTY_INT_ARRAY =
	{};

	public static final boolean[] EMPTY_BOOLEAN_ARRAY =
	{};

	public static final Class<?>[] EMPTY_CLASS_ARRAY =
	{};

	public static final Object[] EMPTY_OBJECT_ARRAY =
	{};

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private ArrayUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Are both objects both <code>null</code> or both non-<code>null</code>
	 * 
	 * @param one
	 *            one object
	 * @param two
	 *            another object
	 * @return true if and only if both objects are <code>null</code> or both
	 *         are non-<code>null</code>
	 */
	public static boolean isBothNullOrBothNonNull(Object one, Object two)
	{
		return (!((one == null) ^ (two == null)));
	}

	/**
	 * Are two arrays equal.
	 * 
	 * @param expected
	 *            array of expected elements
	 * @param actual
	 *            array of actual elements
	 * @return result of equality: if both are null, true; if one is null,
	 *         false; otherwise, true if and only if the lengths equal and each
	 *         element equals the corresponding element (using the
	 *         <code>equals</code> method).
	 */
	public static boolean arrayEquals(Object[] expected, Object[] actual)
	{
		if (expected.length != actual.length)
		{
			return false;
		}
		for (int i = 0; i < expected.length; i++)
		{
			if (!expected[i].equals(actual[i]))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Are two lists equal.
	 * 
	 * @param expected
	 *            list of expected elements
	 * @param actual
	 *            list of actual elements
	 * @return result of equality: if both are null, true; if one is null,
	 *         false; otherwise, true if and only if the lengths equal and each
	 *         element.toString() equals the corresponding element.toString()
	 *         (using the <code>equals</code> method).
	 */
	public static boolean listStringEquals(List<?> expected, List<?> actual)
	{
		if (expected.size() != actual.size())
		{
			return false;
		}
		for (int i = 0; i < expected.size(); i++)
		{
			Object expectedElement = expected.get(i).toString();
			Object actualElement = actual.get(i).toString();
			if (!ArrayUtil.isBothNullOrBothNonNull(expectedElement, actualElement))
			{
				// One is null and the other is not
				return false;
			}
			if ((expectedElement != null)
					&& (!expectedElement.toString().equals(actualElement.toString())))
			{
				// Both are non-null but not equal
				return false;
			}
		}
		return true;
	}

	/**
	 * Print an array.
	 * 
	 * @param array
	 *            array to print
	 * @return array String representation (elements' string represntations,
	 *         comma delimited)
	 */
	public static String arrayToString(Object[] array)
	{
		if (array == null)
		{
			return CommonNames.MISC.NULL_TO_STRING;
		}
		StringBuffer s = new StringBuffer("[");
		for (int i = 0; i < array.length; i++)
		{
			s = s.append(array[i].toString());
			if (i < array.length - 1)
			{
				s.append(",");
			}
		}
		s.append("]");
		return s.toString();
	}

	/**
	 * Print a byte array.
	 * 
	 * @param array
	 *            array to print
	 * @return array String representation (elements' string represntations,
	 *         comma delimited)
	 */
	public static String arrayToString(byte[] array)
	{
		if (array == null)
		{
			return CommonNames.MISC.NULL_TO_STRING;
		}
		StringBuffer s = new StringBuffer("[");
		for (int i = 0; i < array.length; i++)
		{
			s = s.append(array[i]);
			if (i < array.length - 1)
			{
				s.append(",");
			}
		}
		s.append("]");
		return s.toString();
	}

	/**
	 * Print an integer array.
	 * 
	 * @param array
	 *            array to print
	 * @return array String representation (elements' string represntations,
	 *         comma delimited)
	 */
	public static String arrayToString(int[] array)
	{
		if (array == null)
		{
			return CommonNames.MISC.NULL_TO_STRING;
		}
		StringBuffer s = new StringBuffer("[");
		for (int i = 0; i < array.length; i++)
		{
			s = s.append(array[i]);
			if (i < array.length - 1)
			{
				s.append(",");
			}
		}
		s.append("]");
		return s.toString();
	}

	/**
	 * Does array contain an element.
	 * 
	 * @param array
	 *            array to print
	 * @param element
	 *            element to look for. If the element is null, this method
	 *            returns <code>-1</code>.
	 * @return index of first occurance of element in the array, or
	 *         <code>-1</code> if not found.
	 */
	public static int indexOf(Object[] array, Object element)
	{
		if ((array == null) || (element == null))
		{
			return CommonNames.MISC.INVALID_VALUE_INTEGER;
		}
		for (int i = 0; i < array.length; i++)
		{
			if (element.equals(array[i]))
			{
				return i;
			}
		}
		return CommonNames.MISC.INVALID_VALUE_INTEGER;
	}

	/**
	 * Print an 2D double array (nxn).
	 * 
	 * @param array
	 *            2D array to print
	 * @param startIndex
	 *            start printing at this row+column index
	 * @param endIndex
	 *            stop printing at this row+column index
	 * @return 2D array String representation (elements' string represntations,
	 *         comma delimited)
	 */
	public static String array2DToString(double[][] array, int startIndex, int endIndex)
	{
		// TODO: move separators references from Names.MUNKRES to Names.ARRAY
		StringBuffer output = new StringBuffer();
		int start = Math.max(0, startIndex);
		int end = Math.min(array.length - 1, endIndex);
		for (int i = start; i <= end; i++)
		{
			for (int j = start; j <= end; j++)
			{
				output.append(CommonNames.MUNKRES.SEPARATOR).append(array[i][j]);
			}
			output.append(CommonNames.MISC.NEW_LINE_CHAR);
		}
		return output.toString();
	} // toString()

	/**
	 * Print an 2D double array (mxn or even non-rectangular).
	 * 
	 * @param array
	 *            2D array to print
	 * @return 2D array String representation (elements' string represntations,
	 *         comma delimited)
	 */
	public static String array2DToString(double[][] array)
	{
		// TODO: move separators references from Names.MUNKRES to Names.ARRAY
		StringBuffer output = new StringBuffer();
		for (int i = 0; i < array.length; i++)
		{
			for (int j = 0; j < array[i].length; j++)
			{
				output.append(CommonNames.MUNKRES.SEPARATOR).append(array[i][j]);
			}
			output.append(CommonNames.MISC.NEW_LINE_CHAR);
		}
		return output.toString();
	} // toString()

	/**
	 * Clone an interger array.
	 * 
	 * @param array
	 *            integer array
	 * @return clone of the array
	 */
	public static int[] clone(int[] array)
	{
		int numDims = array.length;
		int[] copy = new int[numDims];
		for (int i = 0; i < numDims; i++)
		{
			copy[i] = array[i];
		}
		return copy;
	}

	/**
	 * Return the the minimum element in the 0-based array "item". Used in the
	 * tree edit distance algorithm.
	 * <p>
	 * 
	 * @param item
	 *            Array of integer.
	 * @param count
	 *            Number of elements in the array.
	 * @return Potion of the minimum elements.
	 */
	public static int min(int item[])
	{
		int m = item[0];
		for (int i = 1; i < item.length; i++)
		{
			if (m > item[i])
			{
				m = item[i];
			}
		}
		return m;
	}

	/**
	 * Give the position of the minimum elements in array "item". Used in the
	 * tree edit distance algorithm. Side Effects: None.
	 * 
	 * @param item
	 *            Array of integer.
	 * @param count
	 *            Number of elements in the array.
	 * @return Potion of the minimum elements.
	 */
	public static int min_pos(double item[])
	{
		double m = item[0];
		int pos = 0;
		for (int i = 0; i < item.length; i++)
		{
			if (m > item[i])
			{
				pos = i;
				m = item[i];
			}
		}
		return pos;
	}

	/**
	 * Sort an array of doubles (quicksort).
	 * 
	 * @param x
	 *            array of doubles.
	 * @return array, sorted in ascending order.
	 */
	public static double[] sort(double[] x)
	{
		int n = x.length, incr = n / 2;
		while (incr >= 1)
		{
			for (int i = incr; i < n; i++)
			{
				double temp = x[i];
				int j = i;
				while (j >= incr && temp < x[j - incr])
				{
					x[j] = x[j - incr];
					j -= incr;
				}
				x[j] = temp;
			}
			incr /= 2;
		}
		return (x);
	}

	/**
	 * Reallocates an array with a new size, and copies the contents of the old
	 * array to the new array.
	 * 
	 * @param oldArray
	 *            the old array, to be reallocated.
	 * @param newSize
	 *            the new array size.
	 * @return A new array with the same contents.
	 */
	public static Object resizeArray(Object oldArray, int newSize)
	{
		int oldSize = java.lang.reflect.Array.getLength(oldArray);
		Class<?> elementType = oldArray.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
		int preserveLength = Math.min(oldSize, newSize);
		if (preserveLength > 0)
		{
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		}
		return newArray;
	}

	/**
	 * Convert a list to a string array. Useful utility for the web layer forms
	 * (multi-box/drop-down menu selection).
	 * 
	 * @param list
	 *            input list
	 * @return String array with string representation of the list's elements
	 */
	public static String[] listToStringArray(List<?> list)
	{
		if (list == null)
		{
			return null;
		}

		if (list.size() == 0)
		{
			return new String[]
			{};
		}

		String[] strings = new String[list.size()];
		for (int i = 0; i < strings.length; i++)
		{
			Object element = list.get(i);
			strings[i] = (element == null) ? null : element.toString();
		}
		return strings;
	}

	/**
	 * Returns a delimited string composed of the elements of a collection.
	 * 
	 * @param collection
	 *            the collection. Must be non-<code>null</code> but could be
	 *            empty
	 * @param delimiter
	 *            delimiter character
	 * @return delimited string composed of the elements of the collection
	 */
	public static String toDelimitedString(final Collection<?> collection,
			final String delimiter)
	{
		StringBuffer s = TextUtil.emptyStringBuffer();
		Iterator<?> iterator = collection.iterator();
		while (iterator.hasNext())
		{
			Object element = iterator.next();
			s.append(element);
			if (iterator.hasNext())
			{
				s.append(delimiter);
			}
		}
		return s.toString();
	}
}
