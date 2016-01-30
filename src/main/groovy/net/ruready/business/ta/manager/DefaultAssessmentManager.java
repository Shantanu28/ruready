package net.ruready.business.ta.manager;

import static net.ruready.business.ta.manager.Messages.createMessage;
import static net.ruready.business.ta.manager.Messages.deleteMessage;
import static net.ruready.business.ta.manager.Messages.findAllMessage;
import static net.ruready.business.ta.manager.Messages.findByIdMessage;
import static net.ruready.business.ta.manager.Messages.nullMessage;
import static net.ruready.business.ta.manager.Messages.readMessage;
import static net.ruready.business.ta.manager.Messages.updateMessage;
import static org.apache.commons.lang.Validate.notNull;

import java.util.List;

import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.eis.ta.ExpectationAssessmentDAO;
import net.ruready.eis.ta.KnowledgeAssessmentDAO;
/**
 * Default implementation of test management.
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
public class DefaultAssessmentManager extends AbstractTAManager implements AbstractAssessmentManager
{
	private static final String EXPECTATION_ASSESSMENT_NAME = "Expectation Assessment";
	private static final String KNOWLEDGE_ASSESSMENT_NAME = "Knowledge Assessment";
	
	private static final String ID_NOT_NULL = nullMessage("id");
	private static final String TEST_NOT_NULL = nullMessage("test");
	
	public DefaultAssessmentManager(
			final ResourceLocator resourceLocator,
			final ApplicationContext context)
	{
		super(resourceLocator, context);		
	}

	public void reattachExpectationAssessment(final ExpectationAssessment assessment)
	{
		getExpectationAssessmentDAO().reattach(assessment);
	}

	public void reattachKnowledgeAssessment(final KnowledgeAssessment assessment)
	{
		getKnowledgeAssessmentDAO().reattach(assessment);
	}
	
	public List<ExpectationAssessment> findAllExpectationAssessments()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug(findAllMessage(EXPECTATION_ASSESSMENT_NAME));
		}
		return getExpectationAssessmentDAO().findAll();
	}

	public ExpectationAssessment findExpectationAssessmentById(final Long id)
	{
		notNull(id, ID_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(findByIdMessage(EXPECTATION_ASSESSMENT_NAME, id));
		}
		return getExpectationAssessmentDAO().read(id);
	}
	
	public void createExpectationAssessment(final ExpectationAssessment assessment)
	{
		notNull(assessment, TEST_NOT_NULL);
		if (logger.isInfoEnabled())
		{
			logger.info(createMessage(EXPECTATION_ASSESSMENT_NAME));
		}
		getExpectationAssessmentDAO().create(assessment);
	}

	public ExpectationAssessment readExpectationAssessment(final Long id, final ExpectationAssessment assessment)
	{
		notNull(id, ID_NOT_NULL);
		notNull(assessment, TEST_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(readMessage(EXPECTATION_ASSESSMENT_NAME, id));
		}
		getExpectationAssessmentDAO().readInto(id, assessment);
		return assessment;
	}

	public void updateExpectationAssessment(final ExpectationAssessment assessment)
	{
		notNull(assessment, TEST_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(updateMessage(EXPECTATION_ASSESSMENT_NAME));
		}
		getExpectationAssessmentDAO().update(assessment);
	}

	public void updateExpectationAssessmentMerge(final ExpectationAssessment assessment)
	{
		notNull(assessment, TEST_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(updateMessage(EXPECTATION_ASSESSMENT_NAME));
		}
		getExpectationAssessmentDAO().merge(assessment);
	}

	public void deleteExpectationAssessment(final ExpectationAssessment assessment)
	{
		notNull(assessment, TEST_NOT_NULL);
		if (logger.isInfoEnabled())
		{
			logger.info(deleteMessage(EXPECTATION_ASSESSMENT_NAME));
		}
		getExpectationAssessmentDAO().delete(assessment);
	}
	
	public List<KnowledgeAssessment> findAllKnowledgeAssessments()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug(findAllMessage(KNOWLEDGE_ASSESSMENT_NAME));
		}
		return getKnowledgeAssessmentDAO().findAll();
	}

	public KnowledgeAssessment findKnowledgeAssessmentById(Long id)
	{
		notNull(id, ID_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(findByIdMessage(KNOWLEDGE_ASSESSMENT_NAME, id));
		}
		return getKnowledgeAssessmentDAO().read(id);
	}

	public void createKnowledgeAssessment(KnowledgeAssessment assessment)
	{
		notNull(assessment, TEST_NOT_NULL);
		if (logger.isInfoEnabled())
		{
			logger.info(createMessage(KNOWLEDGE_ASSESSMENT_NAME));
		}
		getKnowledgeAssessmentDAO().create(assessment);
	}

	public KnowledgeAssessment readKnowledgeAssessment(Long id, KnowledgeAssessment assessment)
	{
		notNull(id, ID_NOT_NULL);
		notNull(assessment, TEST_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(readMessage(KNOWLEDGE_ASSESSMENT_NAME, id));
		}
		getKnowledgeAssessmentDAO().readInto(id, assessment);
		return assessment;
	}

	public void updateKnowledgeAssessment(KnowledgeAssessment assessment)
	{
		notNull(assessment, TEST_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(updateMessage(KNOWLEDGE_ASSESSMENT_NAME));
		}
		getKnowledgeAssessmentDAO().update(assessment);
	}

	public void updateKnowledgeAssessmentMerge(KnowledgeAssessment assessment)
	{
		notNull(assessment, TEST_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(updateMessage(KNOWLEDGE_ASSESSMENT_NAME));
		}
		getKnowledgeAssessmentDAO().merge(assessment);
	}

	public void deleteKnowledgeAssessment(KnowledgeAssessment assessment)
	{
		notNull(assessment, TEST_NOT_NULL);
		if (logger.isInfoEnabled())
		{
			logger.info(deleteMessage(KNOWLEDGE_ASSESSMENT_NAME));
		}
		getKnowledgeAssessmentDAO().delete(assessment);
	}
	
	private ExpectationAssessmentDAO getExpectationAssessmentDAO()
	{
		return (ExpectationAssessmentDAO) getEisManager().getDAO(ExpectationAssessment.class, getContext());
	}

	private KnowledgeAssessmentDAO getKnowledgeAssessmentDAO()
	{
		return (KnowledgeAssessmentDAO) getEisManager().getDAO(KnowledgeAssessment.class, getContext());
	}
}
