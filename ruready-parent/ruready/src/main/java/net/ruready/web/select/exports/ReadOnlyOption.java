package net.ruready.web.select.exports;

import org.apache.struts.util.LabelValueBean;

/**
 * A read-only option in a drop-down selection menu. Useful for use with the
 * Struts' <html:optionsCollection> tag.
 * <p>
 * This tag Renders a set of HTML <option> elements, representing possible
 * choices for a <select> element. This tag can be used multiple times within a
 * single <html:select> element, either in conjunction with or instead of one or
 * more <html:option> or <html:options> elements.
 * <p>
 * This tag operates on a collection of beans, where each bean has a label
 * property and a value property. The actual names of these properties can be
 * configured using the label and value attributes of this tag.
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
 * @version Jul 27, 2007
 * @see LabelValueBean
 * @see Option
 */
public interface ReadOnlyOption
{
	// ========================= ABSTRACT METHODS ==========================
	/**
	 * @return
	 * @see org.apache.struts.util.Option#getLabel()
	 */
	String getLabel();

	/**
	 * @return
	 * @see org.apache.struts.util.Option#getValue()
	 */
	String getValue();
}
