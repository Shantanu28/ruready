/*****************************************************************************************
 * Source File: CharacterAssembly.java
 ****************************************************************************************/
package net.ruready.common.parser.core.chars;

import net.ruready.common.parser.core.entity.Assembly;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;

/**
 * A CharacterAssembly is an Assembly whose elements are characters. Copyright (c) 1999
 * Steven J. Metsker. All Rights Reserved. Steve Metsker makes no representations or
 * warranties about the fitness of this software for any particular purpose, including the
 * implied warranty of merchantability.
 * 
 * @author Steven J. Metsker
 * @version 1.0
 * @see Assembly
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 Protected by
 *         U.S. Provisional Patent U-4003, February 2006
 * @version May 20, 2007
 */
public class CharacterAssembly extends Assembly
{
	/**
	 * the string to consume
	 */
	protected String string;

	/**
	 * Constructs a CharacterAssembly from the given String.
	 * 
	 * @param String
	 *            the String to consume
	 * @return a CharacterAssembly that will consume the supplied String
	 */
	public CharacterAssembly(String string)
	{
		this.string = string;
	}

	/**
	 * Returns a textual representation of the amount of this characterAssembly that has
	 * been consumed.
	 * 
	 * @param delimiter
	 *            the mark to show between consumed elements
	 * @return a textual description of the amount of this assembly that has been consumed
	 */
	@Override
	public String consumed(String delimiter)
	{
		if (TextUtil.isEmptyString(delimiter))
		{
			return string.substring(0, elementsConsumed());
		}
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < elementsConsumed(); i++)
		{
			if (i > 0)
			{
				buf.append(delimiter);
			}
			buf.append(string.charAt(i));
		}
		return buf.toString();
	}

	/**
	 * Returns the default string to show between elements consumed or remaining.
	 * 
	 * @return the default string to show between elements consumed or remaining
	 */
	@Override
	public String defaultDelimiter()
	{
		return CommonNames.MISC.EMPTY_STRING;
	}

	/**
	 * Returns the number of elements in this assembly.
	 * 
	 * @return the number of elements in this assembly
	 */
	@Override
	public int length()
	{
		return string.length();
	}

	/**
	 * Returns the next character.
	 * 
	 * @return the next character from the associated token string
	 * @exception ArrayIndexOutOfBoundsException
	 *                if there are no more characters in this assembly's string
	 */
	public Object nextElement()
	{
		return new Character(string.charAt(index++));
	}

	/**
	 * Shows the next object in the assembly, without removing it
	 * 
	 * @return the next object
	 */
	@Override
	public Object peek()
	{
		if (index < length())
		{
			return new Character(string.charAt(index));
		}
		else
		{
			return null;
		}
	}

	/**
	 * Returns a textual representation of the amount of this characterAssembly that
	 * remains to be consumed.
	 * 
	 * @param delimiter
	 *            the mark to show between consumed elements
	 * @return a textual description of the amount of this assembly that remains to be
	 *         consumed
	 */
	@Override
	public String remainder(String delimiter)
	{
		if (TextUtil.isEmptyString(delimiter))
		{
			return string.substring(elementsConsumed());
		}
		StringBuffer buf = new StringBuffer();
		for (int i = elementsConsumed(); i < string.length(); i++)
		{

			if (i > elementsConsumed())
			{
				buf.append(delimiter);
			}
			buf.append(string.charAt(i));
		}
		return buf.toString();
	}
}
