/*****************************************************************************************
 * Source File: Matches.java
 ****************************************************************************************/
package net.ruready.common.parser.core.entity;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.exception.InternationalizableErrorMessage;
import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This object holds a set (well, a <code>List</code>, really) of assemblies, this
 * method matches a parser against all of them, and returns a new object with a new set of
 * the assemblies that result from the matching process.
 * <p>
 * In addition to a list, <code>Matches</code> also holds a list of syntax errors
 * accumulated during the matching process and passed from a parser to its caller.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 Protected by
 *         U.S. Provisional Patent U-4003, February 2006
 * @version May 20, 2007
 */
public class Matches implements PubliclyCloneable
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Matches.class);

	// ========================= FIELDS ====================================

	// A list of assemblies
	private final List<Assembly> assemblies;

	// Extraneous tokens removed from the syntax tree
	private final List<InternationalizableErrorMessage> syntaxErrors;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty object holding no parser matches and no syntax errors.
	 */
	public Matches()
	{
		super();
		this.assemblies = new ArrayList<Assembly>();
		this.syntaxErrors = new ArrayList<InternationalizableErrorMessage>();
	}

	/**
	 * Create an object holding parser matches and no syntax errors.
	 * 
	 * @param assemblies
	 *            list of assemblies
	 */
	public Matches(List<Assembly> assemblies)
	{
		super();
		this.assemblies = assemblies;
		this.syntaxErrors = new ArrayList<InternationalizableErrorMessage>();
	}

	/**
	 * Create an object holding parser matches.
	 * 
	 * @param assemblies
	 *            list of assemblies
	 * @param syntaxErrors
	 *            list of syntax errors
	 */
	public Matches(List<Assembly> assemblies, List<InternationalizableErrorMessage> syntaxErrors)
	{
		super();
		this.assemblies = assemblies;
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
		s.append(assemblies);
		if (this.hasErrors())
		{
			s.append(" errors: ");
			s.append(syntaxErrors);
		}

		return s.toString();
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * Return a deep copy of this object. Must be implemented for this object to serve as
	 * a target (or part of a target) of an assembly.
	 * 
	 * @return a deep copy of this object.
	 */
	@Override
	public Matches clone()
	{
		// try {
		// Copy root node data
		List<Assembly> copyAssemblies = Matches.elementClone(assemblies);
		Matches copy = new Matches(copyAssemblies, new ArrayList<InternationalizableErrorMessage>(
				syntaxErrors));
		return copy;
		// }
		//
		// catch (CloneNotSupportedException e) {
		// // this shouldn't happen,
		// // because we are Cloneable
		// throw new InternalError("clone() failed: " + e.getMessage());
		// }
	}

	// ========================= METHODS ===================================

	/**
	 * Add a syntax error to the list of errors in this target.
	 * 
	 * @param o
	 *            new syntax error
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean addSyntaxError(InternationalizableErrorMessage o)
	{
		return syntaxErrors.add(o);
	}

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
	 * @return
	 * @see java.util.List#size()
	 */
	public int numSyntaxErrors()
	{
		return syntaxErrors.size();
	}

	/**
	 * Add an assembly to the list of assemblies.
	 * 
	 * @param e
	 *            assembly to add
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean addAssembly(Assembly e)
	{
		return assemblies.add(e);
	}

	/**
	 * Add a list of matches to this object's lists. Also append the list of syntax errors
	 * from <code>c</code> to this object's syntax error list.
	 * 
	 * @param c
	 *            list of matches to add
	 * @return
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Matches c)
	{
		for (InternationalizableErrorMessage e : c.getSyntaxErrors())
		{
			this.addSyntaxError(e);
		}
		return assemblies.addAll(c.getAssemblies());
	}

	/**
	 * @return
	 * @see java.util.List#size()
	 */
	public int numAssemblies()
	{
		return assemblies.size();
	}

	/**
	 * @return
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty()
	{
		return assemblies.isEmpty();
	}

	// ========================= STATIC METHODS ============================

	/**
	 * Create a copy of a vector, cloning each element of the vector.
	 * 
	 * @param in
	 *            the vector to copy
	 * @return a copy of the input vector, cloning each element of the vector
	 */
	private static List<Assembly> elementClone(List<Assembly> v)
	{
		List<Assembly> copy = new ArrayList<Assembly>();
		for (Assembly a : v)
		{
			copy.add(a.clone());
		}
		return copy;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the assemblies
	 */
	public List<Assembly> getAssemblies()
	{
		return assemblies;
	}

	/**
	 * @return the syntaxErrors
	 */
	public List<InternationalizableErrorMessage> getSyntaxErrors()
	{
		return syntaxErrors;
	}

}
