package net.ruready.web.common.util;

import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.web.common.rl.WebAppNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Convenience methods for altering special characters related to URL encoding,
 * regular expressions, and HTML tags.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 13, 2007
 */
public final class HtmlUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TextUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private HtmlUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Synonym for <tt>URLEncoder.encode(String, "UTF-8")</tt>.
	 * <p>
	 * Used to ensure that HTTP query strings are in proper form, by escaping
	 * special characters such as spaces.
	 * <p>
	 * An example use case for this method is a login scheme in which, after
	 * successful login, the user is redirected to the "original" target
	 * destination. Such a target might be passed around as a request parameter.
	 * Such a request parameter will have a URL as its value, as in
	 * "LoginTarget=Blah.jsp?this=that&blah=boo", and would need to be
	 * URL-encoded in order to escape its special characters.
	 * <p>
	 * It is important to note that if a query string appears in an
	 * <tt>HREF</tt> attribute, then there are two issues - ensuring the query
	 * string is valid HTTP (it is URL-encoded), and ensuring it is valid HTML
	 * (ensuring the ampersand is escaped).
	 * 
	 * @param aURLFragment
	 *            a URL fragment string
	 * @return URL-encoded string
	 */
	public static String urlEncode(String aURLFragment)
	{
		String result = null;
		try
		{
			result = URLEncoder.encode(aURLFragment, "UTF-8");
		}
		catch (UnsupportedEncodingException ex)
		{
			throw new RuntimeException("UTF-8 not supported", ex);
		}
		return result;
	}

	/**
	 * Copy a string without encoding it.
	 * 
	 * @param tag
	 *            String to be encoded
	 * @return tag as-is
	 */
	@Deprecated
	public static String copy(String aTagFragment)
	{
		return aTagFragment;
	}

	/**
	 * Replace characters having special meaning <em>inside</em> HTML tags
	 * with their escaped equivalents, using character entities such as
	 * <tt>'&amp;'</tt>.
	 * <p>
	 * The escaped characters are :
	 * <ul>
	 * <li> <
	 * <li> >
	 * <li> "
	 * <li> '
	 * <li> \
	 * <li> &
	 * </ul>
	 * <p>
	 * This method ensures that arbitrary text appearing inside a tag does not
	 * "confuse" the tag. For example, <tt>href='Blah.do?Page=1&Sort=ASC'</tt>
	 * does not comply with strict HTML because of the ampersand, and should be
	 * changed to <tt>href='Blah.do?Page=1&amp;Sort=ASC'</tt>. This is
	 * commonly seen in building query strings. (In JSTL, the c:url tag performs
	 * this task automatically.)
	 * 
	 * @param tag
	 *            String to be encoded
	 * @return html-encoded string.
	 */
	public static String encodeHTMLTag(String aTagFragment)
	{
		if (aTagFragment == null)
		{
			return null;
		}
		final StringBuffer result = new StringBuffer();
		final StringCharacterIterator iterator = new StringCharacterIterator(aTagFragment);
		char character = iterator.current();
		while (character != CharacterIterator.DONE)
		{
			if (character == '<')
			{
				result.append("&lt;");
			}
			else if (character == '>')
			{
				result.append("&gt;");
			}
			else if (character == '\"')
			{
				result.append("&quot;");
			}
			else if (character == '\'')
			{
				result.append("&#039;");
			}
			else if (character == '\\')
			{
				result.append("&#092;");
			}
			else if (character == '&')
			{
				result.append("&amp;");
			}
			else
			{
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	/**
	 * Build a URL from a URI and a list of parameters.
	 * 
	 * @param uri
	 *            base URI of the URL
	 * @param parameters
	 *            a map of parameter name-to-value
	 * @return html-encoded string for the entire URL
	 */
	public static String buildUrl(final String uri, Map<String, String> parameters)
	{
		// Need to add a URL separator if this is not a stand-alone query string
		// (uri is
		// non-empty) and URL does not already contain a separator
		boolean standAloneQuery = TextUtil.isEmptyTrimmedString(uri);
		boolean urlSeparatorExists = (uri.indexOf(WebAppNames.HTML.URL_SEPARATOR) >= 0);

		StringBuffer url = new StringBuffer(uri);
		// Is this the first NON-NULL parameter in the map
		boolean firstParam = true;
		// Append parameters to the URL
		for (Map.Entry<String, String> entry : parameters.entrySet())
		{
			// Skip parameters whose value is null
			if (entry.getValue() != null)
			{
				StringBuffer parameterSnippet = new StringBuffer(
						urlEncode(entry.getKey())).append(
						WebAppNames.HTML.PARAM_ASSIGNMENT).append(
						urlEncode(entry.getValue()));

				if (firstParam)
				{
					if (!standAloneQuery)
					{
						url.append(urlSeparatorExists ? WebAppNames.HTML.PARAM_SEPARATOR
								: WebAppNames.HTML.URL_SEPARATOR);
					}
				}
				else
				{
					url.append(WebAppNames.HTML.PARAM_SEPARATOR);
				}

				url.append(parameterSnippet);
				firstParam = false;
			}
		}
		return url.toString();
	}

	/**
	 * Build a URL from a URI and a single parameter.
	 * 
	 * @param uri
	 *            base URI of the URL
	 * @param paramName
	 *            parameter's name
	 * @param paramValue
	 *            parameter's value
	 * @return html-encoded string for the entire URL
	 */
	public static String buildUrl(final String uri, final String paramName,
			final String paramValue)
	{
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(paramName, paramValue);
		return HtmlUtil.buildUrl(uri, parameters);
	}

	/**
	 * @param query
	 * @return
	 */
	public static Map<String, String> getQueryMap(final String queryString)
	{
		Map<String, String> parameters = Collections
				.synchronizedMap(new TreeMap<String, String>());
		if (TextUtil.isEmptyTrimmedString(queryString))
		{
			return parameters;
		}

		String[] params = queryString.split(WebAppNames.HTML.PARAM_SEPARATOR);
		for (String param : params)
		{
			String[] parts = param.split(WebAppNames.HTML.PARAM_ASSIGNMENT, 2);
			String name = parts[0];
			String value = (parts.length > 1) ? parts[1] : CommonNames.MISC.EMPTY_STRING;
			parameters.put(name, value);
		}

		return parameters;
	}

	/**
	 * Replace characters having special meaning <em>inside</em> HTML tags
	 * with their escaped equivalents, using character entities such as
	 * <tt>'&amp;'</tt>. This method makes sure that <em>after</em> HTML
	 * reads the encoded string in a page, the displayed string will be the same
	 * as the output of <code>encodeHTMLTags(aTagFragment)</code>.
	 * <p>
	 * The escaped characters are :
	 * <ul>
	 * <li> <
	 * <li> >
	 * <li> "
	 * <li> '
	 * <li> \
	 * <li> &
	 * </ul>
	 * <p>
	 * This method ensures that arbitrary text appearing inside a tag does not
	 * "confuse" the tag. For example, <tt>href='Blah.do?Page=1&Sort=ASC'</tt>
	 * does not comply with strict HTML because of the ampersand, and should be
	 * changed to <tt>href='Blah.do?Page=1&amp;amp;Sort=ASC'</tt>. This is
	 * commonly seen in building query strings. (In JSTL, the c:url tag performs
	 * this task automatically.)
	 * 
	 * @param tag
	 *            String to be encoded
	 * @return html-encoded string.
	 */
	public static String encodeHTMLTagTwice(String aTagFragment)
	{
		if (aTagFragment == null)
		{
			return null;
		}
		final StringBuffer result = new StringBuffer();
		final StringCharacterIterator iterator = new StringCharacterIterator(aTagFragment);
		char character = iterator.current();
		while (character != CharacterIterator.DONE)
		{
			if (character == '<')
			{
				result.append("&amp;lt;");
			}
			else if (character == '>')
			{
				result.append("&amp;gt;");
			}
			else if (character == '\"')
			{
				result.append("&amp;quot;");
			}
			else if (character == '\'')
			{
				result.append("&amp;#039;");
			}
			else if (character == '\\')
			{
				result.append("&amp;#092;");
			}
			else if (character == '&')
			{
				result.append("&amp;amp;");
			}
			else
			{
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	/**
	 * Return <tt>aText</tt> with all start-of-tag and end-of-tag characters
	 * replaced by their escaped equivalents.
	 * <p>
	 * If user input may contain tags which must be disabled, then call this
	 * method, not {@link #forHTMLTag}. This method is used for text appearing
	 * <em>outside</em> of a tag, while {@link #forHTMLTag} is used for text
	 * appearing <em>inside</em> an HTML tag.
	 * <p>
	 * It is not uncommon to see text on a web page presented erroneously,
	 * because <em>all</em> special characters are escaped (as in
	 * {@link #forHTMLTag}). In particular, the ampersand character is often
	 * escaped not once but <em>twice</em> : once when the original input
	 * occurs, and then a second time when the same item is retrieved from the
	 * database. This occurs because the ampersand is the only escaped character
	 * which appears in a character entity.
	 * 
	 * @param text
	 *            HTML text
	 * @return text without html tags
	 */
	public static String disableTags(String aText)
	{
		final StringBuffer result = new StringBuffer();
		final StringCharacterIterator iterator = new StringCharacterIterator(aText);
		char character = iterator.current();
		while (character != CharacterIterator.DONE)
		{
			if (character == '<')
			{
				result.append("&lt;");
			}
			else if (character == '>')
			{
				result.append("&gt;");
			}
			else
			{
				// The char is not a special one
				// Add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	/**
	 * Replace characters having special meaning in regular expressions with
	 * their escaped equivalents.
	 * <p>
	 * The escaped characters include :
	 * <ul>
	 * <li>.
	 * <li>\
	 * <li>?, * , and +
	 * <li>&
	 * <li>:
	 * <li>{ and }
	 * <li>[ and ]
	 * <li>( and )
	 * <li>^ and $
	 * </ul>
	 * 
	 * @param aRegexFragment
	 *            a regular expression fragment string
	 * @return regular expression-encided string
	 */
	public static String forRegex(String aRegexFragment)
	{
		final StringBuffer result = new StringBuffer();
		final StringCharacterIterator iterator = new StringCharacterIterator(
				aRegexFragment);
		char character = iterator.current();
		while (character != CharacterIterator.DONE)
		{
			// All literals need to have backslashes doubled.
			if (character == '.')
			{
				result.append("\\.");
			}
			else if (character == '\\')
			{
				result.append("\\\\");
			}
			else if (character == '?')
			{
				result.append("\\?");
			}
			else if (character == '*')
			{
				result.append("\\*");
			}
			else if (character == '+')
			{
				result.append("\\+");
			}
			else if (character == '&')
			{
				result.append("\\&");
			}
			else if (character == ':')
			{
				result.append("\\:");
			}
			else if (character == '{')
			{
				result.append("\\{");
			}
			else if (character == '}')
			{
				result.append("\\}");
			}
			else if (character == '[')
			{
				result.append("\\[");
			}
			else if (character == ']')
			{
				result.append("\\]");
			}
			else if (character == '(')
			{
				result.append("\\(");
			}
			else if (character == ')')
			{
				result.append("\\)");
			}
			else if (character == '^')
			{
				result.append("\\^");
			}
			else if (character == '$')
			{
				result.append("\\$");
			}
			else
			{
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	/**
	 * Converts HTML to text converting entities such as &quot; back to " and
	 * &lt; back to &lt; Ordinary text passes unchanged.
	 * 
	 * @param text
	 *            raw text to be processed. Must not be null.
	 * @return translated text. It also handles HTML 4.0 entities such as
	 *         &hearts; &#123; and &x#123; &nbsp; -> 160. null input returns
	 *         null.
	 */
	public static String stripHTMLTags(String text)
	{
		return StripEntities.stripEntities(text);
	}

	/**
	 * Removes tags from HTML leaving just the raw text. Leaves entities as is,
	 * e.g. does not convert &amp; back to &. similar to code in Quoter. Also
	 * removes <!-- --> comments
	 * 
	 * @param html
	 *            input HTML
	 * @return raw text, with whitespaces collapsed to a single space, trimmed.
	 */
	public static String removeHTMLTags(String text)
	{
		return StripEntities.stripHTMLTags(text);
	}

	/**
	 * Convert a color to a hexadecimal number.
	 * 
	 * @param c
	 *            AWT color
	 * @return hexadecimal code of <code>c</code>
	 */
	public static String colorToHexString(Color c)
	{
		String str = Integer.toHexString(c.getRGB() & 0xFFFFFF);
		return ("#" + "000000".substring(str.length()) + str.toUpperCase());
	}

	/**
	 * Replace new line characters <code>&quot;\n&quot;</code> by spaces in a
	 * string.
	 * 
	 * @param aTagFragment
	 *            String to be stripped
	 * @return stripped string
	 * @deprecated
	 */
	@Deprecated
	public static String stripNewLines(String aTagFragment)
	{
		if (aTagFragment == null)
		{
			return null;
		}
		final StringBuffer result = new StringBuffer();
		final StringCharacterIterator iterator = new StringCharacterIterator(aTagFragment);
		char character = iterator.current();
		while (character != CharacterIterator.DONE)
		{
			if (character == '\n')
			{
				result.append(" ");
			}
			else
			{
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}
}
