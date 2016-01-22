package net.ruready.web.user.beans;

import java.io.Serializable;
import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;

public class GroupManagementFormBean implements Serializable
{
	private static final long serialVersionUID = -6618469691912472534L;
	
	private UserGroup userGroup;
	private User user;
	
	private Long courseId;
	private Long schoolId;
	
	private List<Course> courses;
	private List<School> schools;
	
	// For use in managing school autocomplete
	private SchoolSearchFormBean schoolSearch;
	
	// For use in assigning a moderator
	private SimpleUserSearchBean userSearch;

	public GroupManagementFormBean()
	{
		this.userGroup = new UserGroup();
	}
	
	public GroupManagementFormBean(final UserGroup userGroup)
	{
		this.userGroup = userGroup;
	}

	public UserGroup getUserGroup()
	{
		return userGroup;
	}

	public void setUserGroup(final UserGroup userGroup)
	{
		this.userGroup = userGroup;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(final User user)
	{
		this.user = user;
	}
	
	public Long getCourseId()
	{
		return courseId;
	}

	public void setCourseId(final Long courseId)
	{
		this.courseId = courseId;
	}

	public Long getSchoolId()
	{
		return schoolId;
	}

	public void setSchoolId(final Long schoolId)
	{
		this.schoolId = schoolId;
	}

	public List<Course> getCourses()
	{
		return courses;
	}

	public void setCourses(final List<Course> courses)
	{
		this.courses = courses;
	}
	
	public List<School> getSchools()
	{
		return schools;
	}

	public void setSchools(final List<School> schools)
	{
		this.schools = schools;
	}

	public SchoolSearchFormBean getSchoolSearch()
	{
		return schoolSearch;
	}

	public void setSchoolSearch(final SchoolSearchFormBean schoolSearch)
	{
		this.schoolSearch = schoolSearch;
	}

	public SimpleUserSearchBean getUserSearch()
	{
		return userSearch;
	}

	public void setUserSearch(final SimpleUserSearchBean userSearch)
	{
		this.userSearch = userSearch;
	}
}
