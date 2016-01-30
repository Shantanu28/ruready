package net.ruready.business.ta.manager;

import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.ta.entity.StudentCourseTranscript;
import net.ruready.business.ta.entity.StudentGroupTranscript;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.UserGroup;
/**
 * Methods for managing student course transcripts.
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
* @author Jeremy Lund
* @version Nov 15, 2007
*/
public interface AbstractStudentTranscriptManager
{
	public void reattachTranscript(final StudentTranscript transcript);
	
	public void createTranscript(final StudentTranscript transcript);	
	public StudentCourseTranscript createCourseTranscript(final StudentRole student, final Course course);	
	public StudentGroupTranscript createGroupTranscript(final StudentRole student, final UserGroup group);
	
	public void updateTranscript(final StudentTranscript transcript);	
	public void updateTranscriptMerge(final StudentTranscript transcript);
	
	public StudentTranscript readTranscript(final Long id, final StudentTranscript transcript);
	
	public void deleteTranscript(final StudentTranscript transcript);
	
	public List<StudentTranscript> findAllTranscripts();	
	public List<StudentCourseTranscript> findAllCourseTranscripts();	
	public List<StudentGroupTranscript> findAllGroupTranscripts();
	
	public StudentTranscript findById(final Long id);
	
	public List<StudentTranscript> findTranscriptsForStudent(final StudentRole student);
	public List<StudentCourseTranscript> findCourseTranscriptsForStudent(final StudentRole student);
	public List<StudentGroupTranscript> findGroupTranscriptsForStudent(final StudentRole student);
	
	public List<StudentGroupTranscript> findTranscriptsForGroup(final UserGroup group);
	public void openAllTranscriptsForGroup(final UserGroup group);
	public void closeAllTranscriptsForGroup(final UserGroup group);
	
	public StudentCourseTranscript findCourseTranscript(final StudentRole student, final Course course);
	public StudentGroupTranscript findGroupTranscript(final StudentRole student, final UserGroup group);
}
