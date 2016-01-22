package net.ruready.web.ta.support;

import javax.servlet.http.HttpServletRequest;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.ta.exports.AbstractStudentTranscriptBD;
import net.ruready.web.common.support.DefaultStrutsSupport;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.ta.imports.StrutsStudentTranscriptBD;
/**
 * Test Administration-specific Struts support methods
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
* @author Jeremy Lund
* @version Nov 27, 2007
*/
public class StrutsSupport extends DefaultStrutsSupport
{	
	public final AbstractEditItemBD<Course> getCourseBD(final HttpServletRequest request)
	{
		return new StrutsEditItemBD<Course>(Course.class,getWebApplicationContext(request),getSystemUser());
	}
	
	public final AbstractStudentTranscriptBD getStudentTranscriptBD(final HttpServletRequest request)
	{
		return new StrutsStudentTranscriptBD(getWebApplicationContext(request));
	}
}
