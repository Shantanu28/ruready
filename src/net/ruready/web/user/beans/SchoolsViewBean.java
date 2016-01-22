package net.ruready.web.user.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import net.ruready.business.content.world.entity.School;

import org.apache.commons.lang.builder.CompareToBuilder;

public class SchoolsViewBean implements Serializable
{
	private static final long serialVersionUID = -7370271593306288779L;
	
	private Collection<School> schools;

	public Collection<School> getSchools()
	{
		return schools;
	}

	public void setSchools(final Collection<School> schools)
	{
		this.schools = new TreeSet<School>(getComparator());
		this.schools.addAll(schools);
	}
	
	private Comparator<School> getComparator()
	{
		return new Comparator<School>() {
			public int compare(final School o1,  final School o2)
			{
				return new CompareToBuilder()
					.append(o1.getDescription(), o2.getDescription())
					.toComparison();
			}
		};
	}
}
