package net.ruready.business.ta.entity.property;

import java.util.List;

import net.ruready.business.content.catalog.entity.Expectation;
import net.ruready.business.ta.entity.ExpectationAssessment;
/**
 * Interface for creating Expectation Assessments. Implementing classes should will implement their own strategies for creating the assessments.
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
public interface ExpectationAssessmentFactory
{
	public ExpectationAssessment createExpectationAssessment(final List<Expectation> expectations);
	
	public ExpectationAssessment createExpectationAssessment(final ExpectationAssessment assessmentTemplate);
}
