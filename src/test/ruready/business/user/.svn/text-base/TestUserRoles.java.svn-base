package test.ruready.business.user;

import java.util.EnumSet;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.user.entity.AdministratorRole;
import net.ruready.business.user.entity.EditorRole;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserRole;
import net.ruready.business.user.entity.property.RoleType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.business.UserUtil;
import test.ruready.rl.TestEnvTestBase;

/**
 * Unit test the management of roles.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Jeremy Lund
 * @version Sep 20, 2007
 */
public class TestUserRoles extends TestEnvTestBase
{
	protected final Log logger = LogFactory.getLog(getClass());

	private UserUtil util;

	private User user;

	@Override
	protected void initialize()
	{
		util = new UserUtil(environment);

		user = util.getUser("testuser");
	}

	@Override
	protected void cleanUp()
	{
		util.deleteUser(user);
	}

	/**
	 * Tests creating a new user with a student role
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddStudentRole() throws Exception
	{
		user.addRole(new StudentRole());
		Assert.assertEquals(1, user.getRoles().size());
		Assert.assertTrue(user.hasRole(RoleType.STUDENT));
		util.saveUser(user);

		final User loadedUser = util.getUserFromDB(user);
		assertRoleEquality(user, loadedUser);
	}

	/**
	 * Test creating a new user with a student and teacher role by adding each
	 * role explicitly
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddTeacherRole1() throws Exception
	{
		user.addRole(new StudentRole());
		user.addRole(new TeacherRole());
		Assert.assertEquals(2, user.getRoles().size());
		util.saveUser(user);

		final User loadedUser = util.getUserFromDB(user);
		assertRoleEquality(user, loadedUser);
	}

	/**
	 * Test adding a new user with a student and teacher role by simply adding
	 * the teacher role
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddTeacherRole2() throws Exception
	{
		user.addRole(new TeacherRole());
		Assert.assertEquals(2, user.getRoles().size());
		Assert.assertTrue(user.hasRole(RoleType.STUDENT));
		util.saveUser(user);

		logger.debug(user);
		final User loadedUser = util.getUserFromDB(user);
		assertRoleEquality(user, loadedUser);
	}

	/**
	 * Test adding a new user as an editor.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddEditorRole() throws Exception
	{
		user.addRole(new EditorRole());
		Assert.assertEquals(3, user.getRoles().size());
		Assert.assertTrue(user.hasRole(RoleType.STUDENT));
		Assert.assertTrue(user.hasRole(RoleType.TEACHER));
		util.saveUser(user);

		final User loadedUser = util.getUserFromDB(user);
		assertRoleEquality(user, loadedUser);
	}

	/**
	 * Test adding a new user as an administrator
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddAdministratorRole1() throws Exception
	{
		user.addRole(new AdministratorRole());
		Assert.assertEquals(4, user.getRoles().size());
		Assert.assertTrue(user.hasRole(RoleType.STUDENT));
		Assert.assertTrue(user.hasRole(RoleType.TEACHER));
		Assert.assertTrue(user.hasRole(RoleType.ADMIN));
		util.saveUser(user);

		logger.debug(user);
		final User loadedUser = util.getUserFromDB(user);
		assertRoleEquality(user, loadedUser);
	}

	/**
	 * Test adding a new user as an administrator
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddAdministratorRole2() throws Exception
	{
		// Add a lesser role to verify that it will add the additional
		// roles correctly in the next call
		user.addRole(new StudentRole());
		user.addRole(new AdministratorRole());
		Assert.assertEquals(4, user.getRoles().size());
		Assert.assertTrue(user.hasRole(RoleType.STUDENT));
		Assert.assertTrue(user.hasRole(RoleType.TEACHER));
		Assert.assertTrue(user.hasRole(RoleType.ADMIN));
		util.saveUser(user);

		logger.debug(user);
		final User loadedUser = util.getUserFromDB(user);
		assertRoleEquality(user, loadedUser);
		Assert.assertTrue(user.hasRole(RoleType.ADMIN));
	}

	@Test
	public void testHasRoles() throws Exception
	{
		user.addRole(new TeacherRole());
		Assert.assertTrue(user.hasRole(RoleType.STUDENT));
		Assert.assertFalse(user.hasRole(RoleType.ADMIN));
		Assert
				.assertTrue(user.hasAnyRole(EnumSet
						.of(RoleType.STUDENT, RoleType.TEACHER)));
		Assert.assertFalse(user.hasAnyRole(EnumSet.of(RoleType.EDITOR, RoleType.ADMIN)));
		Assert.assertFalse(user.hasAnyRole(EnumSet.noneOf(RoleType.class)));
		Assert.assertTrue(user.hasAnyRole(EnumSet.allOf(RoleType.class)));
	}

	@Test
	public void testSetToRole1() throws Exception
	{
		user.addRole(new TeacherRole());
		util.saveUser(user);
		Assert.assertTrue(user.hasRole(RoleType.TEACHER));
		util.updateUserAndRole(user, RoleType.STUDENT);
		Assert.assertFalse(user.hasRole(RoleType.TEACHER));
		Assert.assertTrue(user.hasRole(RoleType.STUDENT));
	}

	@Test
	public void testSetToRole2() throws Exception
	{
		user.addRole(RoleType.ADMIN);
		util.saveUser(user);
		util.updateUserAndRole(user, RoleType.TEACHER);
		Assert.assertTrue(user.hasRole(RoleType.TEACHER));
		Assert.assertFalse(user.hasAnyRole(EnumSet.of(RoleType.EDITOR, RoleType.ADMIN)));
	}

	@Test
	public void testSetToRole3() throws Exception
	{
		user.addRole(RoleType.EDITOR);
		util.saveUser(user);
		Assert.assertTrue(user.hasRole(RoleType.EDITOR));
		util.updateUserAndRole(user, RoleType.EDITOR);
		Assert.assertTrue(user.hasRole(RoleType.EDITOR));
		Assert.assertFalse(user.hasRole(RoleType.ADMIN));
	}
	
	@Test
	public void testSetToRole4() throws Exception
	{
		user.addRole(RoleType.STUDENT);
		util.saveUser(user);
		Assert.assertTrue(user.hasRole(RoleType.STUDENT));
		util.updateUserAndRole(user, RoleType.TEACHER);
		Assert.assertTrue(user.hasRole(RoleType.TEACHER));
		Assert.assertTrue(user.hasRole(RoleType.STUDENT));		
	}
	
	@Test
	public void testSetToRole5() throws Exception
	{
		user.addRole(RoleType.STUDENT);
		util.saveUser(user);
		Assert.assertTrue(user.hasRole(RoleType.STUDENT));
		util.updateUserAndRole(user, RoleType.EDITOR);
		Assert.assertTrue(user.hasRole(RoleType.EDITOR));
		Assert.assertTrue(user.hasRole(RoleType.STUDENT));		
	}

	/**
	 * Test removing a single role explicitly.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRemoveRole1() throws Exception
	{
		user.addRole(new StudentRole());
		util.saveUser(user);

		final User loadedUser = util.getUserFromDB(user);
		final UserRole userRole = loadedUser.getRole(RoleType.STUDENT);
		util.removeUserRole(loadedUser, userRole);
		Assert.assertEquals(0, loadedUser.getRoles().size());

		final User reloadedUser = util.getUserFromDB(user);
		Assert.assertEquals(0, loadedUser.getRoles().size());
		assertRoleEquality(loadedUser, reloadedUser);
	}

	/**
	 * Test removing a single role implicitly.
	 * 
	 * It does this by removing a role (editor) that is lower than the user's
	 * highest role (administrator) and then verifying that both roles are gone.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRemoveRole2() throws Exception
	{
		user.addRole(new AdministratorRole());
		Assert.assertEquals(4, user.getRoles().size());
		user.removeRole(RoleType.EDITOR);
		Assert.assertEquals(2, user.getRoles().size());
		Assert.assertFalse(user.hasRole(RoleType.EDITOR));
		Assert.assertFalse(user.hasRole(RoleType.ADMIN));
	}

	/**
	 * Test removing a single role implicitly.
	 * 
	 * It does this by removing a role (editor) that is lower than the user's
	 * highest role (administrator) and then verifying that both roles are gone.
	 * 
	 * This test does this addition and removal over multiple database
	 * operations.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRemoveRole3() throws Exception
	{
		user.addRole(new AdministratorRole());
		Assert.assertEquals(4, user.getRoles().size());

		util.saveUser(user);

		final User loadedUser = util.getUserFromDB(user);
		util.removeUserRole(loadedUser, loadedUser.getRole(RoleType.EDITOR));
		Assert.assertEquals(2, loadedUser.getRoles().size());
		Assert.assertFalse(loadedUser.hasRole(RoleType.EDITOR));
		Assert.assertFalse(loadedUser.hasRole(RoleType.ADMIN));
	}

	/**
	 * Test adding a duplicate student role to a user.
	 * 
	 * This test explicitly tries the add the same role twice.
	 * 
	 * @throws Exception
	 */
	@Test(expected = BusinessRuleException.class)
	public void testAddDuplicateStudentRole1() throws Exception
	{
		user.addRole(new StudentRole());
		user.addRole(new StudentRole());
	}

	/**
	 * Test adding a duplicate student role to a user.
	 * 
	 * This test adds the duplicate implicitly by first adding a teacher role
	 * (which also implicitly adds a student role) and then tries to add the
	 * student role.
	 * 
	 * @throws Exception
	 */
	@Test(expected = BusinessRuleException.class)
	public void testAddDuplicateStudentRole2() throws Exception
	{
		user.addRole(new TeacherRole());
		user.addRole(new StudentRole());
	}

	private void assertRoleEquality(final User aUser, final User loadedUser)
	{
		Assert.assertEquals(aUser.getRoles().size(), loadedUser.getRoles().size());
		for (UserRole role : aUser.getRoles())
		{
			Assert.assertTrue(loadedUser.hasRole(role.getRoleType()));
		}
	}
}
