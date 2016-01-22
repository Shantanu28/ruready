package net.ruready.business.ta.exports;

import java.util.List;

import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.manager.AbstractAssessmentManager;
import net.ruready.common.rl.ResourceLocator;

public abstract class DefaultAssessmentBD implements AbstractAssessmentBD
{
	protected final AbstractAssessmentManager manager;
	
	protected final ResourceLocator resourceLocator;
	
	protected DefaultAssessmentBD(
			final AbstractAssessmentManager manager,
			final ResourceLocator resourceLocator)
	{
		this.manager = manager;
		this.resourceLocator = resourceLocator;
	}

	public void reattachExpectationAssessment(final ExpectationAssessment assessment)
	{
		manager.reattachExpectationAssessment(assessment);
	}

	public void reattachKnowledgeAssessment(final KnowledgeAssessment assessment)
	{
		manager.reattachKnowledgeAssessment(assessment);
	}

	public List<ExpectationAssessment> findAllExpectationAssessments()
	{
		return manager.findAllExpectationAssessments();
	}

	public ExpectationAssessment findExpectationAssessmentById(final Long id)
	{
		return manager.findExpectationAssessmentById(id);
	}
	
	public void createExpectationAssessment(final ExpectationAssessment assessment)
	{
		manager.createExpectationAssessment(assessment);
	}

	public ExpectationAssessment readExpectationAssessment(
			final Long id,
			final ExpectationAssessment assessment)
	{
		return manager.readExpectationAssessment(id, assessment);
	}

	public void updateExpectationAssessment(final ExpectationAssessment assessment)
	{
		manager.updateExpectationAssessment(assessment);
	}

	public void updateExpectationAssessmentMerge(final ExpectationAssessment assessment)
	{
		manager.updateExpectationAssessmentMerge(assessment);
	}

	public void deleteExpectationAssessment(final ExpectationAssessment assessment)
	{
		manager.deleteExpectationAssessment(assessment);
	}

	public List<KnowledgeAssessment> findAllKnowledgeAssessments()
	{
		return manager.findAllKnowledgeAssessments();
	}

	public KnowledgeAssessment findKnowledgeAssessmentById(final Long id)
	{
		return manager.findKnowledgeAssessmentById(id);
	}

	public void createKnowledgeAssessment(final KnowledgeAssessment assessment)
	{
		manager.createKnowledgeAssessment(assessment);
	}

	public KnowledgeAssessment readKnowledgeAssessment(
			final Long id,
			final KnowledgeAssessment assessment)
	{
		return manager.readKnowledgeAssessment(id, assessment);
	}

	public void updateKnowledgeAssessment(final KnowledgeAssessment assessment)
	{
		manager.updateKnowledgeAssessment(assessment);
	}

	public void updateKnowledgeAssessmentMerge(final KnowledgeAssessment assessment)
	{
		manager.updateKnowledgeAssessmentMerge(assessment);
	}

	public void deleteKnowledgeAssessment(final KnowledgeAssessment assessment)
	{
		manager.deleteKnowledgeAssessment(assessment);
	}
}
