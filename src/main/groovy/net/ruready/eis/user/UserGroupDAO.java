package net.ruready.eis.user;

import java.util.List;

import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserRole;
import net.ruready.common.eis.dao.DAO;

public interface UserGroupDAO extends DAO<UserGroup,Long>
{
	public List<UserGroup> findActive();
	
	public List<UserGroup> findByPartialName(final String searchString);
	
	public List<UserGroup> findActiveByPartialName(final String searchString);
	
	public List<UserGroup> findByMatchingProperties(final UserGroup userGroup);
	
	public List<UserGroup> getModeratedGroupsByUser(final User user);
	
	public List<UserGroup> getModeratedGroupByTeacher(final TeacherRole teacher);
	
	public List<UserGroup> getMembershipsByUser(final User user);
	
	public List<UserGroup> getMembershipsByStudent(final StudentRole student);
	
	public List<UserRole> getMembershipsByUserGroup(final UserGroup userGroup);
	
	public List<UserRole> getModeratorsByUserGroup(final UserGroup userGroup);

}
