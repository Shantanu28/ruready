package net.ruready.web.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.ActiveStatus;
import net.ruready.business.user.entity.TeacherSchoolMembership;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserGroupModerator;
import net.ruready.business.user.entity.property.AgeGroup;
import net.ruready.business.user.entity.property.Ethnicity;
import net.ruready.business.user.entity.property.Language;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.business.user.exports.AbstractUserGroupBD;
import net.ruready.common.discrete.Gender;
import net.ruready.common.search.DefaultSearchCriteria;
import net.ruready.common.search.Logic;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchCriterionFactory;
import net.ruready.common.search.SearchType;
import net.ruready.common.search.SortType;
import net.ruready.web.user.support.StrutsSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

//http://www.xtra-rant.com/gennames/
public class RandomAccountsAction extends Action
{

	private final String[] femaleFirstNames = 
		new String[] {"Alica", "Ami", "Nanci", "Merrie", "Azzie", 
			"Tanika", "Salena", "Melanie", "Nohemi", "Paola"};
	private final String[] femaleLastNames = 
		new String[] {"Focell", "Giesler", "Wilo", "Losey", "Baker", 
			"Coates", "Roadman", "Overstreet", "Joghs", "Allshouse"};
	private final String[] maleFirstNames = 
		new String[] {"Emmanuel", "Jess", "Milo", "Gaylord", "Gregorio",
			"Mitch", "Columbus", "Dante", "Ivory", "Roland"};
	private final String[] maleLastNames =
		new String[] {"Ashbaugh", "Nabholz"," Arthur", "Metzer", "Mueller", 
			"Clewett", "Wall", "Huey", "James", "Wynter"};
	
	private final String[] emailDomains = {"yahoo.com", "gmail.com", "foo.edu"};
	
	private final String[] groupNames = 
		new String[] {"Xena", "Marmaduke", "Chester", "Rowland" ,"Linnet", "Kip", "Jamey", "Vicky", "Avril", "Bevis" };

	private final String[] groupSuffixes = 
		new String[] {"Group", "Team", "Class"};
	private final StrutsSupport strutsSupport = new StrutsSupport();
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		createUsers(request, Gender.FEMALE, femaleFirstNames, femaleLastNames);
		createUsers(request, Gender.MALE, maleFirstNames, maleLastNames);
		createRoleAccounts(request);
		createGroups(request, groupNames);
		
		return mapping.findForward("success");
	}
	
	private void createRoleAccounts(final HttpServletRequest request)
	{
		final AbstractUserBD userBD = getStrutsSupport().getUserBD(request);
		// Add a default student user for ease in testing student role
		User newUser = new User("student@demo.com", Gender.MALE, AgeGroup.GT_25, Ethnicity.WHITE, Language.ENGLISH);
		newUser.addRole(RoleType.STUDENT);
		newUser.setFirstName("Ed");
		newUser.setLastName("Ucation");
		newUser.setPassword(userBD.encryptPassword("demo"));
		userBD.createUser(newUser);
	}
	
	private void createUsers(final HttpServletRequest request, final Gender gender, final String[] firstNames, final String[] lastNames)
	{
		final AbstractUserBD userBD = getStrutsSupport().getUserBD(request);
		for (int i=0; i < firstNames.length; i++)
		{
			User newUser = createUser(firstNames[i], lastNames[i], i, gender);
			newUser.setPassword(userBD.generatePassword(newUser));
			userBD.createUser(newUser);
		}		
	}
	
	private User createUser(final String firstName, final String lastName, final int index, final Gender gender)
	{
		User newUser = new User();
		newUser.setEmail(getEmail(firstName, lastName));
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setGender(gender);
		newUser.setEthnicity(Ethnicity.values()[index % Ethnicity.values().length]);
		newUser.setAgeGroup(AgeGroup.values()[index % AgeGroup.values().length]);
		newUser.setLanguage(Language.values()[index % Language.values().length]);
		newUser.addRole(RoleType.values()[index % RoleType.values().length].newInstance());
		return newUser;
	}

	private String getEmail(final String firstName, final String lastName)
	{
		final int seed = StringUtils.getLevenshteinDistance(firstName, lastName);
		return new StringBuilder()
		.append(firstName)
		.append(seed % 2==0? "." : "")
		.append(lastName)
		.append(seed)
		.append("@")
		.append(emailDomains[seed % 3])
		.toString();
	}
	
	private void createGroups(final HttpServletRequest request, final String[] someGroupNames)
	{
		final List<School> schools = getAllSchools(request);
		final List<Course> courses = getAllCourses(request);
		final List<User> teachers = getAllTeachers(request);
		final AbstractUserGroupBD groupBD = getStrutsSupport().getUserGroupBD(request);
		final AbstractUserBD userBD = getStrutsSupport().getUserBD(request);
		for (int i=0; i < someGroupNames.length; i++)
		{
			final User teacher = teachers.get(i % teachers.size());
			UserGroup userGroup = new UserGroup(
					someGroupNames[i] + " " + groupSuffixes[i % groupSuffixes.length], 
					courses.get(i % courses.size()),
					schools.get(i % schools.size()));
			groupBD.createUserGroup(userGroup);
			groupBD.addModerator(new UserGroupModerator(teacher, userGroup, true));
			final TeacherSchoolMembership membership = new TeacherSchoolMembership(
					teacher, userGroup.getSchool(), ActiveStatus.ACTIVE);
			userBD.addSchoolMembership(membership);
		}		
	}
	
	private List<School> getAllSchools(final HttpServletRequest request)
	{
		return getStrutsSupport().getWorldBD(request).findSchoolByPartialName("");
	}
	
	private List<Course> getAllCourses(final HttpServletRequest request)
	{
		return getStrutsSupport()
			.getCourseBD(request)
			.findAllNonDeleted(Course.class,ItemType.CATALOG);
	}
	
	private List<User> getAllTeachers(final HttpServletRequest request)
	{
		final SearchCriteria criteria = new DefaultSearchCriteria(Logic.AND
				.createCriterion());
		// search for all accounts that are not students (teachers and above)
		criteria.add(SearchCriterionFactory.<RoleType> createSimpleExpression(
				SearchType.NE, RoleType.class, "highestRole", RoleType.STUDENT));
		criteria.addSortCriterion(SearchCriterionFactory.createSortCriterion(User.EMAIL,
				SortType.ASCENDING));
		return getStrutsSupport().getUserBD(request).findByCriteria(criteria);
	}
	
	private StrutsSupport getStrutsSupport()
	{
		return this.strutsSupport;
	}
}
