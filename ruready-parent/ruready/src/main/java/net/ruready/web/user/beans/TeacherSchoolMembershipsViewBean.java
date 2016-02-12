package net.ruready.web.user.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import net.ruready.business.user.entity.TeacherSchoolMembership;

import org.apache.commons.lang.builder.CompareToBuilder;

public class TeacherSchoolMembershipsViewBean implements Serializable
{
	private static final long serialVersionUID = 5245260673256862321L;
	
	private Collection<TeacherSchoolMembership> members;

	public Collection<TeacherSchoolMembership> getMembers()
	{
		return members;
	}

	public void setMembers(final Collection<TeacherSchoolMembership> members)
	{
		this.members = new TreeSet<TeacherSchoolMembership>(getComparator());
		this.members.addAll(members);
	}
	
	private Comparator<TeacherSchoolMembership> getComparator()
	{
		return new Comparator<TeacherSchoolMembership>() {
			public int compare(final TeacherSchoolMembership o1,  final TeacherSchoolMembership o2)
			{
				return new CompareToBuilder()
					.append(o1.getMember().getUser().getName().getLastName(), o2.getMember().getUser().getName().getLastName())
					.append(o1.getMember().getUser().getName().getFirstName(), o2.getMember().getUser().getName().getFirstName())
					.append(o1.getMember().getUser().getName().getMiddleInitial(), o2.getMember().getUser().getName().getMiddleInitial())
					.append(o1.getMember().getUser().getEmail(), o2.getMember().getUser().getEmail())
					.append(o1.getSchool().getDescription(), o2.getSchool().getDescription())
					.toComparison();
			}
		};
	}
}
