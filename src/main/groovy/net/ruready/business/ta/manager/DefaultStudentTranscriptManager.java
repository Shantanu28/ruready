package net.ruready.business.ta.manager;

import static net.ruready.business.ta.manager.Messages.createMessage;
import static net.ruready.business.ta.manager.Messages.deleteMessage;
import static net.ruready.business.ta.manager.Messages.findAllMessage;
import static net.ruready.business.ta.manager.Messages.findByIdMessage;
import static net.ruready.business.ta.manager.Messages.nullMessage;
import static net.ruready.business.ta.manager.Messages.readMessage;
import static net.ruready.business.ta.manager.Messages.updateMessage;
import static org.apache.commons.lang.Validate.notNull;

import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.ta.entity.StudentCourseTranscript;
import net.ruready.business.ta.entity.StudentGroupTranscript;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.common.eis.dao.DAO;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.eis.ta.StudentTranscriptDAO;

public class DefaultStudentTranscriptManager extends AbstractTAManager implements
		AbstractStudentTranscriptManager
{
	private static final String ENTITY_NAME = "Student Transcript";
	private static final String COURSE_ENTITY_NAME = "Student Course Transcript";
	private static final String GROUP_ENTITY_NAME = "Student Group Transcript";
	
	private static final String ID_NOT_NULL = nullMessage("id");
	private static final String TRANSCRIPT_NOT_NULL = nullMessage("transcript");
	private static final String STUDENT_NOT_NULL = nullMessage("student");
	private static final String COURSE_NOT_NULL = nullMessage("course");
	private static final String GROUP_NOT_NULL = nullMessage("group");
	
	public DefaultStudentTranscriptManager(
			final ResourceLocator resourceLocator,
			final ApplicationContext context)
	{
		super(resourceLocator, context);		
	}

	public void reattachTranscript(final StudentTranscript transcript)
	{
		getTranscriptDAO().reattach(transcript);
	}

	public List<StudentTranscript> findAllTranscripts()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug(findAllMessage(ENTITY_NAME));
		}
		return getTranscriptDAO().findAll();
	}

	public List<StudentCourseTranscript> findAllCourseTranscripts()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug(findAllMessage(COURSE_ENTITY_NAME));
		}
		return getCourseTranscriptDAO().findAll();
	}

	public List<StudentGroupTranscript> findAllGroupTranscripts()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug(findAllMessage(GROUP_ENTITY_NAME));
		}
		return getGroupTranscriptDAO().findAll();
	}

	public StudentTranscript findById(final Long id)
	{
		notNull(id, ID_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(findByIdMessage(ENTITY_NAME, id));
		}
		return getTranscriptDAO().read(id);
	}

	public List<StudentTranscript> findTranscriptsForStudent(final StudentRole student)
	{
		notNull(student, STUDENT_NOT_NULL);
		return getTranscriptDAO().findByStudent(student);
	}

	public List<StudentCourseTranscript> findCourseTranscriptsForStudent(final StudentRole student)
	{
		notNull(student, STUDENT_NOT_NULL);
		return getTranscriptDAO().findCourseTranscriptsByStudent(student);
	}

	public List<StudentGroupTranscript> findGroupTranscriptsForStudent(final StudentRole student)
	{
		notNull(student, STUDENT_NOT_NULL);
		return getTranscriptDAO().findGroupTranscriptsByStudent(student);
	}

	public List<StudentGroupTranscript> findTranscriptsForGroup(final UserGroup group)
	{
		notNull(group, GROUP_NOT_NULL);
		return getTranscriptDAO().findByGroup(group);
	}

	public void openAllTranscriptsForGroup(final UserGroup group)
	{
		final List<StudentGroupTranscript> transcripts  = findTranscriptsForGroup(group);
		for (StudentGroupTranscript transcript : transcripts)
		{
			transcript.openTranscript();
			updateTranscript(transcript);
		}
	}

	public void closeAllTranscriptsForGroup(final UserGroup group)
	{
		final List<StudentGroupTranscript> transcripts  = findTranscriptsForGroup(group);
		for (StudentGroupTranscript transcript : transcripts)
		{
			transcript.closeTranscript();
			updateTranscript(transcript);
		}
	}

	public StudentCourseTranscript findCourseTranscript(StudentRole student, Course course)
	{
		notNull(student, STUDENT_NOT_NULL);
		notNull(course, COURSE_NOT_NULL);
		return getTranscriptDAO().findCourseTranscript(student, course);
	}

	public StudentGroupTranscript findGroupTranscript(StudentRole student, UserGroup group)
	{
		notNull(student, STUDENT_NOT_NULL);
		notNull(group, GROUP_NOT_NULL);
		return getTranscriptDAO().findGroupTranscript(student, group);
	}

	public void createTranscript(final StudentTranscript transcript)
	{
		notNull(transcript, TRANSCRIPT_NOT_NULL);
		if (logger.isInfoEnabled())
		{
			logger.info(createMessage(ENTITY_NAME));
		}
		getTranscriptDAO().create(transcript);
	}

	public StudentCourseTranscript createCourseTranscript(final StudentRole student, final Course course)
	{
		notNull(student, STUDENT_NOT_NULL);
		notNull(course, COURSE_NOT_NULL);
		if (logger.isInfoEnabled())
		{
			logger.info(createMessage(COURSE_ENTITY_NAME));
		}
		final StudentCourseTranscript transcript = new StudentCourseTranscript(student, course);
		getTranscriptDAO().create(transcript);
		return transcript;
	}

	public StudentGroupTranscript createGroupTranscript(final StudentRole student, final UserGroup group)
	{
		notNull(student, STUDENT_NOT_NULL);
		notNull(group, GROUP_NOT_NULL);
		if (logger.isInfoEnabled())
		{
			logger.info(createMessage(GROUP_ENTITY_NAME));
		}
		final StudentGroupTranscript transcript = new StudentGroupTranscript(student, group);
		logger.info(transcript);
		getTranscriptDAO().create(transcript);
		return transcript;
	}
	
	public StudentTranscript readTranscript(final Long id, final StudentTranscript transcript)
	{
		notNull(id, ID_NOT_NULL);
		notNull(transcript, TRANSCRIPT_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(readMessage(ENTITY_NAME, id));
		}
		getTranscriptDAO().readInto(id, transcript);
		return transcript;
	}

	public void updateTranscript(final StudentTranscript transcript)
	{
		notNull(transcript, TRANSCRIPT_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(updateMessage(ENTITY_NAME));
		}
		getTranscriptDAO().update(transcript);
	}

	public void updateTranscriptMerge(final StudentTranscript transcript)
	{
		notNull(transcript, TRANSCRIPT_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(updateMessage(ENTITY_NAME));
		}
		getTranscriptDAO().merge(transcript);
	}

	public void deleteTranscript(final StudentTranscript transcript)
	{
		notNull(transcript, TRANSCRIPT_NOT_NULL);
		if (logger.isInfoEnabled())
		{
			logger.info(deleteMessage(ENTITY_NAME));
		}
		getTranscriptDAO().delete(transcript);
	}
	
	private StudentTranscriptDAO getTranscriptDAO()
	{
		return (StudentTranscriptDAO) getEisManager().getDAO(StudentTranscript.class, getContext());
	}
	
	private DAO<StudentCourseTranscript, Long> getCourseTranscriptDAO()
	{
		return getEisManager().getDAO(StudentCourseTranscript.class, getContext());
	}
	
	private DAO<StudentGroupTranscript, Long> getGroupTranscriptDAO()
	{
		return getEisManager().getDAO(StudentGroupTranscript.class, getContext());
	}
}
