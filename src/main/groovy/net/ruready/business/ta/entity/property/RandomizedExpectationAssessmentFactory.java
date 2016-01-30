package net.ruready.business.ta.entity.property;

import java.util.Collections;
import java.util.List;

import net.ruready.business.content.catalog.entity.Expectation;
import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.ExpectationAssessmentItem;
/**
 * Default implementation of the Expectation Assessment Factory
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
public class RandomizedExpectationAssessmentFactory implements ExpectationAssessmentFactory
{

	public ExpectationAssessment createExpectationAssessment(final List<Expectation> expectations)
	{		
		final ExpectationAssessment assessment = new ExpectationAssessment();
		
		for (Expectation expectation : expectations)
		{
			final ExpectationAssessmentItem item = new ExpectationAssessmentItem(expectation);
			final Integer lastInteger = Integer.valueOf(item.getName().substring(item.getName().length() - 1));
			item.setBaseline((lastInteger % 10) * .5);
			assessment.addTestItem(item);
		}
		//Collections.shuffle(assessment.getTestItems());
		return assessment;
	}

	public ExpectationAssessment createExpectationAssessment(ExpectationAssessment assessmentTemplate)
	{
		final ExpectationAssessment assessment = new ExpectationAssessment();
		for (ExpectationAssessmentItem itemTemplate : assessmentTemplate.getTestItems())
		{
			final ExpectationAssessmentItem item = new ExpectationAssessmentItem(itemTemplate);
			assessment.addTestItem(item);
		}
		Collections.shuffle(assessment.getTestItems());
		return assessment;
	}
}
