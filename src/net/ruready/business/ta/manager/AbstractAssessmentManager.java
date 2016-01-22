package net.ruready.business.ta.manager;

import java.util.List;

import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.common.rl.BusinessManager;
/**
 * Methods for managing student tests.
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
* @version Nov 15, 2007
*/
public interface AbstractAssessmentManager extends BusinessManager
{	
	public void reattachExpectationAssessment(final ExpectationAssessment assessment);
	public void reattachKnowledgeAssessment(final KnowledgeAssessment assessment);
	
	public List<ExpectationAssessment> findAllExpectationAssessments();
	
	public ExpectationAssessment findExpectationAssessmentById(final Long id);
	
	public void createExpectationAssessment(final ExpectationAssessment assessment);
	
	public ExpectationAssessment readExpectationAssessment(final Long id, final ExpectationAssessment assessment);
	
	public void updateExpectationAssessment(final ExpectationAssessment assessment);
	
	public void updateExpectationAssessmentMerge(final ExpectationAssessment assessment);
	
	public void deleteExpectationAssessment(final ExpectationAssessment assessment);
	
	
	public List<KnowledgeAssessment> findAllKnowledgeAssessments();
	
	public KnowledgeAssessment findKnowledgeAssessmentById(final Long id);
	
	public void createKnowledgeAssessment(final KnowledgeAssessment assessment);
	
	public KnowledgeAssessment readKnowledgeAssessment(final Long id, final KnowledgeAssessment assessment);
	
	public void updateKnowledgeAssessment(final KnowledgeAssessment assessment);
	
	public void updateKnowledgeAssessmentMerge(final KnowledgeAssessment assessment);
	
	public void deleteKnowledgeAssessment(final KnowledgeAssessment assessment);
}
