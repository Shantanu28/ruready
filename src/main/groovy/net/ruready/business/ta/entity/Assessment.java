package net.ruready.business.ta.entity;

import java.util.Date;
import java.util.List;

import net.ruready.common.eis.entity.PersistentEntity;

public interface Assessment<E extends AssessmentItem> extends PersistentEntity<Long>
{
	public Long getId();
	
	public Double getScore();
	
	public AssessmentStatus getStatus();
	
	public List<? extends AssessmentItem> getTestItems();
	
	public E getTestItem(final int i);
	
	public void addTestItem(final E testItem);

	public Date getLastUpdated();
	
	public void beginTest();
	
	public void completeTest();
	
	public void stopTest();	
}
