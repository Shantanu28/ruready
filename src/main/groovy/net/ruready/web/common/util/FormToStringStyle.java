package net.ruready.web.common.util;

import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A styler used in printing web-tier forms using reflection.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 12, 2007
 */
public class FormToStringStyle extends ToStringStyle
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(FormToStringStyle.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a form string representation styler.
	 */
	public FormToStringStyle()
	{
		super();
	}

	// ========================= IMPLEMENTATION: ToStringStyle =============

	/**
	 * @param buffer
	 * @param fieldName
	 * @param value
	 * @see org.apache.commons.lang.builder.ToStringStyle#appendDetail(java.lang.StringBuffer,
	 *      java.lang.String, java.lang.Object)
	 */
	@Override
	protected void appendDetail(StringBuffer buffer, String fieldName, Object value)
	{
		// buffer.append(fieldName);
		// buffer.append("=");
		buffer.append(value);
		buffer.append(" ");
	}

}
