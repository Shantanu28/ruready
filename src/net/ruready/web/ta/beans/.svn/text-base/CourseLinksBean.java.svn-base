package net.ruready.web.ta.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.ta.entity.TranscriptActiveStatus;
import net.ruready.business.ta.entity.TranscriptProgressStatus;

import org.apache.commons.lang.builder.CompareToBuilder;

public class CourseLinksBean implements Serializable
{
	private static final long serialVersionUID = -6831165721144970489L;
	
	private List<Course> allCourses;
	private List<StudentTranscript> transcripts;
	
	public CourseLinksBean() { }

	public List<Course> getAllCourses()
	{
		return allCourses;
	}

	public void setAllCourses(final List<Course> allCourses)
	{
		this.allCourses = allCourses;
	}
	
	public List<Course> getAvailableCourses()
	{
		final List<Course> availableCourses = new ArrayList<Course>(getAllCourses());
		for (StudentTranscript transcript : getTranscripts())
		{
			if ("COURSE".equals(transcript.getTranscriptType()) && availableCourses.contains(transcript.getCourse()))
			{
				availableCourses.remove(transcript.getCourse());
			}
		}
		Collections.sort(availableCourses, getCourseComparator());
		return availableCourses;
	}

	public List<StudentTranscript> getTranscripts()
	{
		return transcripts;
	}

	public void setTranscripts(final List<StudentTranscript> transcripts)
	{
		this.transcripts = transcripts;
	}
	
	public List<StudentTranscript> getNotStartedTranscripts()
	{
		final List<StudentTranscript> notStartedTranscripts = new ArrayList<StudentTranscript>(getTranscripts().size());
		for (StudentTranscript transcript : getTranscripts())
		{
			if (transcript.getActiveStatus() == TranscriptActiveStatus.OPEN && 
					transcript.getProgressStatus() == TranscriptProgressStatus.NOT_STARTED)
			{
				notStartedTranscripts.add(transcript);
			}
		}
		return notStartedTranscripts;
	}
	
	public List<StudentTranscript> getInProcessTranscripts()
	{
		final List<StudentTranscript> inProcessTranscripts = new ArrayList<StudentTranscript>(getTranscripts().size());
		for (StudentTranscript transcript : getTranscripts())
		{
			if (transcript.getActiveStatus() == TranscriptActiveStatus.OPEN && 
					(transcript.getProgressStatus() == TranscriptProgressStatus.IN_PROCESS || 
							transcript.getProgressStatus() == TranscriptProgressStatus.PASSED))
			{
				inProcessTranscripts.add(transcript);
			}
		}
		return inProcessTranscripts;
	}
	
	public List<StudentTranscript> getClosedTranscripts()
	{
		final List<StudentTranscript> closedTranscripts = new ArrayList<StudentTranscript>(getTranscripts().size());
		for (StudentTranscript transcript : getTranscripts())
		{
			if (transcript.getActiveStatus() == TranscriptActiveStatus.CLOSED &&
					transcript.getProgressStatus() != TranscriptProgressStatus.NOT_STARTED)
			{
				closedTranscripts.add(transcript);
			}
		}
		return closedTranscripts;
	}
	
	private Comparator<Course> getCourseComparator()
	{
		return new Comparator<Course>() {
			public int compare(final Course o1,  final Course o2)
			{				
				return new CompareToBuilder()
					.append(o1.getDescription(), o2.getDescription())
					.toComparison();
			}
		};
	}
}
