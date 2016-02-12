package net.ruready.business.user.manager;

import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.UserRole;
import net.ruready.common.rl.BusinessManager;

public interface AbstractUserRoleManager extends BusinessManager
{
	public UserRole findUserRoleById(final Long id);
	public TeacherRole findTeacherRoleById(final Long id);
}
