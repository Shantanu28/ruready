package net.ruready.web.user.beans;

import java.io.Serializable;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.User;

/**
 * ...
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
 * @author Jeremy Lund <code>&lt;jeremylund@stgutah.com&gt;</code>
 * @version Oct 27, 2007
 */
public abstract class AccountFormBean implements Serializable, UserAccountFormBean
{
	private SchoolSearchFormBean schoolSearch;

	private UserDemographicOptions options;

	private User user;
	
	private School school;
	
	private ResultsPagingBean pagingBean = new ResultsPagingBean(0);

	public AccountFormBean()
	{
		this.user = new User();
	}

	public AccountFormBean(final User user)
	{
		this.user = user;
	}

	public User getUser()
	{
		return this.user;
	}
	
	public void setUser(final User user)
	{
		this.user = user;
	}

	public School getSchool()
	{
		return school;
	}

	public void setSchool(final School school)
	{
		this.school = school;
	}

	public ResultsPagingBean getPagingBean()
	{
		return pagingBean;
	}

	public void setPagingBean(final ResultsPagingBean pagingBean)
	{
		this.pagingBean = pagingBean;
	}

	public SchoolSearchFormBean getSchoolSearch()
	{
		return schoolSearch;
	}

	public void setSchoolSearch(final SchoolSearchFormBean schoolSearch)
	{
		this.schoolSearch = schoolSearch;
	}

	public UserDemographicOptions getOptions()
	{
		return options;
	}

	public void setOptions(final UserDemographicOptions options)
	{
		this.options = options;
	}
}
