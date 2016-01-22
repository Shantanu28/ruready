package test.ruready.port.json;

import net.ruready.common.pointer.PubliclyCloneable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A simple record in a result set for JSON experiments.
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
 * @version Nov 22, 2007
 */
public class SimpleRecord implements PubliclyCloneable
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SimpleRecord.class);

	// ========================= FIELDS ====================================

	/**
	 * Record unique identifier.
	 */
	private Long id;

	/**
	 * Some text attached to this record.
	 */
	private String context;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * 
	 */
	public SimpleRecord()
	{
		super();
	}

	/**
	 * @param id
	 * @param context
	 */
	public SimpleRecord(Long id, String context)
	{
		this.id = id;
		this.context = context;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print an interval.
	 */
	@Override
	public String toString()
	{
		return "id:" + id + " " + "context: \"" + context + "\"";
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SimpleRecord other = (SimpleRecord) obj;
		if (context == null)
		{
			if (other.context != null)
				return false;
		}
		else if (!context.equals(other.context))
			return false;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * Return a deep copy of this object. Must be implemented for this object to
	 * serve as a target (or part of a target) of an assembly. Copies all fields
	 * except the identifier.
	 * 
	 * @return a deep copy of this object.
	 */
	@Override
	public SimpleRecord clone()
	{
		try
		{
			SimpleRecord copy = (SimpleRecord) super.clone();
			return copy;
		}

		catch (CloneNotSupportedException e)
		{
			// this shouldn't happen, because we are Cloneable
			throw new InternalError("clone() failed: " + e.getMessage());
		}
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Return the id property.
	 * 
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Set a new value for the id property.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Return the context property.
	 * 
	 * @return the context
	 */
	public String getContext()
	{
		return context;
	}

	/**
	 * Set a new value for the context property.
	 * 
	 * @param context
	 *            the context to set
	 */
	public void setContext(String context)
	{
		this.context = context;
	}

}
