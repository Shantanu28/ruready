/*****************************************************************************************
 * Source File: Match.java
 ****************************************************************************************/
package net.ruready.common.parser.core.entity;

import java.util.List;

import net.ruready.common.exception.InternationalizableErrorMessage;
import net.ruready.common.misc.Immutable;
import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This object holds a single assembly and a list of syntax errors. it represents a single
 * parser match.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @immutable
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 8, 2007
 */
public class Match implements Immutable
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Match.class);

	// ========================= FIELDS ====================================

	// An assembly
	private final Assembly assembly;

	// Extraneous tokens removed from the syntax tree
	private final List<InternationalizableErrorMessage> syntaxErrors;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an object holding parser matches.
	 * 
	 * @param assemby
	 *            An assembly
	 * @param syntaxErrors
	 *            list of syntax errors
	 */
	public Match(Assembly assembly, List<InternationalizableErrorMessage> syntaxErrors)
	{
		super();
		this.assembly = assembly;
		this.syntaxErrors = syntaxErrors;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuffer s = TextUtil.emptyStringBuffer();
		s.append(assembly);
		if (this.hasErrors())
		{
			s.append(" errors: ");
			s.append(syntaxErrors);
		}

		return s.toString();
	}

	// ========================= METHODS ===================================

	/**
	 * Returns true iff there syntax errors were detected during matching.
	 * 
	 * @return <code>true</code> iff the syntax error list is non-empty
	 */
	public boolean hasErrors()
	{
		return !syntaxErrors.isEmpty();
	}

	/**
	 * Return the first syntax error in the error list. Assumes the list is non-empty.
	 * 
	 * @return the first syntax error in the error list. Assumes the list is non-empty.
	 */
	public InternationalizableErrorMessage getFirstSyntaxErrorMessage()
	{
		return syntaxErrors.get(0);
	}

	/**
	 * @see java.util.List#clear()
	 */
	public void clearSyntaxErrors()
	{
		syntaxErrors.clear();
	}

	/**
	 * @return
	 * @see net.ruready.common.parser.core.entity.Assembly#hasMoreElements()
	 */
	public boolean hasMoreElements()
	{
		return assembly.hasMoreElements();
	}

	/**
	 * @param delimiter
	 * @return
	 * @see net.ruready.common.parser.core.entity.Assembly#consumed(java.lang.String)
	 */
	public String consumed(String delimiter)
	{
		return assembly.consumed(delimiter);
	}

	/**
	 * @return
	 * @see net.ruready.common.parser.core.entity.Assembly#peek()
	 */
	public Object peek()
	{
		return assembly.peek();
	}

	/**
	 * @return
	 * @see net.ruready.common.parser.core.entity.Assembly#getTarget()
	 */
	public PubliclyCloneable getTarget()
	{
		return (assembly == null) ? null : assembly.getTarget();
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the assembly
	 */
	public Assembly getAssembly()
	{
		return assembly;
	}

	/**
	 * @return the syntaxErrors
	 */
	public List<InternationalizableErrorMessage> getSyntaxErrors()
	{
		return syntaxErrors;
	}

}
