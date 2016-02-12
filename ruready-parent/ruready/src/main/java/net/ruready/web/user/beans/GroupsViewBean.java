package net.ruready.web.user.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.ruready.business.user.entity.UserGroup;

import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * 
* Contains a set of groups for viewing on a web page, along with
* a flag as to whether the user can change anything with regards to the set of groups.
* <p>
* For a student, this means whether they can subscribe to groups
* For a teacher, this means whether they can create new groups
* 
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
* @version Nov 7, 2007
*/
public class GroupsViewBean implements Serializable
{	
	private static final long serialVersionUID = -541103359525322912L;
	
	private List<UserGroup> userGroups;
	private Boolean canManage;

	/**
	 * Returns where the user viewing the groups can manage their groups
	 * 
	 * @return true if they can manage their groups, false otherwise
	 */
	public Boolean getCanManage()
	{
		return canManage;
	}

	public void setCanManage(final Boolean canManage)
	{
		this.canManage = canManage;
	}

	public GroupsViewBean() {}
	
	public List<UserGroup> getUserGroups()
	{
		return userGroups;
	}

	public void setUserGroups(final List<UserGroup> userGroups)
	{
		this.userGroups = userGroups;
		Collections.sort(this.userGroups, getComparator());
	}
	
	private Comparator<UserGroup> getComparator()
	{
		return new Comparator<UserGroup>() {
			public int compare(final UserGroup o1,  final UserGroup o2)
			{				
				return new CompareToBuilder()
					.append(o1.getName(), o2.getName())
					.append(o1.getCourse().getDescription(), o2.getCourse().getDescription())
					.append(o1.getSchool().getDescription(), o2.getSchool().getDescription())
					.toComparison();
			}
		};
	}
}
