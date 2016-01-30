package net.ruready.business.user.exports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserRole;
import net.ruready.business.user.entity.UserTO;

public class UserTOBuilder
{
	private static final Log logger = LogFactory.getLog(UserTOBuilder.class);
	
	public final List<UserTO> buildUserTOList(final Collection<User> users)
	{
		final List<UserTO> userTOs = new ArrayList<UserTO>();
		for (User user : users)
		{
			userTOs.add(buildUserTO(user));
		}
		return userTOs;
	}
	
	public final List<UserTO> buildUserTOListFromRole(final Collection<UserRole> usersAsRole, final Map<String, Long> linkParams)
	{
		final List<UserTO> userTOs = new ArrayList<UserTO>();
		for (UserRole userAsRole : usersAsRole)
		{
			userTOs.add(buildUserTO(userAsRole, linkParams));
		}
		return userTOs;
	}
	
	public final UserTO buildUserTO(final User user)
	{
		return doBuildUserTO(user);
	}
	
	public final UserTO buildUserTO(final User user, final Map<String, Long> linkParams)
	{
		return doBuildUserTO(user, linkParams);
	}
	
	public final UserTO buildUserTO(final UserRole userAsRole)
	{
		return doBuildUserTO(userAsRole.getUser(), getLinkParamsForId(userAsRole.getId()));
	}
	
	public final UserTO buildUserTO(final UserRole userAsRole, final Map<String, Long> linkParams)
	{
		return doBuildUserTO(userAsRole.getUser(), getLinkParamsForId(userAsRole.getId(), linkParams));
	}
	
	protected UserTO doBuildUserTO(final User user)
	{
		return new UserTO(user);
	}
	
	protected UserTO doBuildUserTO(final User user, final Map<String, Long> linkParams)
	{
		return new UserTO(user, linkParams);
	}
	
	private Map<String, Long> getLinkParamsForId(final Long id)
	{
		logger.debug("Calling getLinkParamsForId: " + id);
		return getLinkParamsForId(id, new HashMap<String, Long>());
	}
	
	private Map<String, Long> getLinkParamsForId(final Long id, final Map<String, Long> linkParams)
	{
		Validate.notNull(linkParams, "linkParams cannot be null");
		linkParams.put(UserTO.ID, id);
		return linkParams;
	}
}
