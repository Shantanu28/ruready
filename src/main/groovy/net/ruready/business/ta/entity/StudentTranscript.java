package net.ruready.business.ta.entity;

import static org.apache.commons.lang.Validate.notNull;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.eis.entity.PersistentEntity;

/**
 * Represents a single student transcript.
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
 * @version Nov 19, 2007
 */
@Entity
@Table(name = "STUDENT_TRANSCRIPT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TRANSCRIPT_TYPE", discriminatorType = DiscriminatorType.STRING, length = 10)
public abstract class StudentTranscript implements PersistentEntity<Long>
{
	private static final Double expectationWeight = 0.20d;
	private static final Double knowledgeWeight = 0.80d;
	
	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STUDENT_ID", updatable = false)
	private StudentRole student;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "ACTIVE_STATUS", nullable = false)
	private TranscriptActiveStatus activeStatus = TranscriptActiveStatus.OPEN;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "PROGRESS_STATUS", nullable = false)
	private TranscriptProgressStatus progressStatus = TranscriptProgressStatus.NOT_STARTED;

	@Column(name = "RECOMMENDED_LEVEL", nullable = false)
	private Integer recommendedLevel = 0;
	
	@OneToMany(cascade = CascadeType.ALL)
	@IndexColumn(name="ASSESSMENT_INDEX")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<ExpectationAssessment> expectationAssessments = new ArrayList<ExpectationAssessment>();

	@OneToMany(cascade = CascadeType.ALL)
	@IndexColumn(name="ASSESSMENT_INDEX")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<KnowledgeAssessment> knowledgeAssessments = new ArrayList<KnowledgeAssessment>();

	@OneToMany(cascade = CascadeType.ALL)
	@IndexColumn(name="ASSESSMENT_INDEX")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<KnowledgeAssessment> level1Practices = new ArrayList<KnowledgeAssessment>();

	@OneToMany(cascade = CascadeType.ALL)
	@IndexColumn(name="ASSESSMENT_INDEX")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<KnowledgeAssessment> level2Practices = new ArrayList<KnowledgeAssessment>();

	@OneToMany(cascade = CascadeType.ALL)
	@IndexColumn(name="ASSESSMENT_INDEX")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<KnowledgeAssessment> level3Practices = new ArrayList<KnowledgeAssessment>();

	@OneToMany(cascade = CascadeType.ALL)
	@IndexColumn(name="ASSESSMENT_INDEX")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<KnowledgeAssessment> level4Practices = new ArrayList<KnowledgeAssessment>();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", nullable = false, updatable = false)
	private Date created = new Date();

	protected StudentTranscript()
	{
	}

	protected StudentTranscript(final User user)
	{
		assertUserIsAStudent(user);
		createStudentTranscript((StudentRole) user.getRole(RoleType.STUDENT));
	}

	protected StudentTranscript(final StudentRole student)
	{
		createStudentTranscript(student);
	}

	private void createStudentTranscript(final StudentRole aStudent)
	{
		notNull(aStudent, "student cannot be null.");
		this.student = aStudent;
	}

	public Long getId()
	{
		return this.id;
	}

	public abstract String getTranscriptType();

	public StudentRole getStudent()
	{
		return this.student;
	}

	public TranscriptActiveStatus getActiveStatus()
	{
		return activeStatus;
	}
	
	public abstract Course getCourse();

	public Integer getRecommendedLevel()
	{
		return recommendedLevel;
	}

	public void setRecommendedLevel(final Integer recommendedLevel)
	{
		notNull(recommendedLevel, "recommendedLevel cannot be null.");
		if (recommendedLevel < 1 || recommendedLevel > 5)
		{
			throw new IllegalArgumentException("recommendedLevel must be between 1 and 5");
		}
		this.recommendedLevel = recommendedLevel;
	}

	public Boolean isCurrentAssessmentAPair()
	{
		return (getExpectationAssessments().size() == getKnowledgeAssessments().size());
	}
	
	public Double getMostRecentAssessmentScore()
	{
		final Double currentExpScore = getExpectationAssessments().isEmpty() ? 0d : getCurrentExpectationAssessment().getScore();
		final Double currentKnowScore = getKnowledgeAssessments().isEmpty() ? 0d : getCurrentKnowledgeAssessment().getScore();
		return calculateScore(currentExpScore, currentKnowScore);
	}
	
	/**
	 * Returns the student's best score on the course
	 * 
	 * The score is a combination of the expectation assessment (20%) and knowledge assessment (80%)
	 * 
	 * @return the student's best score on the course
	 */
	public Double getBestAssessmentScore()
	{
		// if both collections are empty, show no score
		if (getExpectationAssessments().isEmpty() && getKnowledgeAssessments().isEmpty())
		{
			return 0d;
		}
		Double bestScore = 0d;
		// iterate through the common pairs of scores, looking for the best score combination
		final Integer maxIndex = Math.min(getExpectationAssessments().size(), getKnowledgeAssessments().size());
		for (int i=0; i < maxIndex; i++)
		{
			bestScore = max(
					bestScore, 
					calculateScore(
							getExpectationAssessments().get(i).getScore(), 
							getKnowledgeAssessments().get(i).getScore()));
		}
		// if either the list of assessments is larger than the other,
		// compare that last score on its to see if it's better than the previous pairs
		if (getExpectationAssessments().size() > getKnowledgeAssessments().size())
		{
			bestScore = max(
					bestScore, 
					calculateScore(
							getCurrentExpectationAssessment().getScore(), 
							0d));
		}
		else if (getExpectationAssessments().size() < getKnowledgeAssessments().size())
		{
			bestScore = max(
					bestScore, 
					calculateScore(
							0d, 
							getCurrentKnowledgeAssessment().getScore()));
		}
		return bestScore;
	}
	
	/**
	 * Calculates the combined expectation assessment and knowledge assessment score
	 * @param expScore the expectation assessment score
	 * @param knowScore the knowledge assessment score
	 * @return the combined expectation assessment and knowledge assessment score
	 */
	private Double calculateScore(final Double expScore, final Double knowScore)
	{
		return (expScore * expectationWeight) + (knowScore * knowledgeWeight);
	}
	
	public void openTranscript()
	{
		setTranscriptStatus(TranscriptActiveStatus.OPEN);
	}

	public void closeTranscript()
	{
		setTranscriptStatus(TranscriptActiveStatus.CLOSED);
	}

	public TranscriptProgressStatus getProgressStatus()
	{
		return progressStatus;
	}

	public void startCourseIfNotStarted()
	{
		if (isProgressStatusEqualTo(TranscriptProgressStatus.NOT_STARTED))
		{
			startCourse();
		}
	}
	
	public void startCourse()
	{
		assertTranscriptOpen();
		assertProgressStatusEqualTo(TranscriptProgressStatus.NOT_STARTED);
		setProgressStatus(TranscriptProgressStatus.IN_PROCESS);
	}

	public void passCourse()
	{
		assertTranscriptOpen();
		assertProgressStatusEqualTo(TranscriptProgressStatus.IN_PROCESS);
		setProgressStatus(TranscriptProgressStatus.PASSED);
	}	

	/**
	 * Determines whether the expectation assessment is available to take
	 * 
	 * NOTE: this method should be used to ensure that expectation assessments
	 * and knowledge assessments occur in pairs
	 * 
	 * @return true if the expectation assessment is available, otherwise false
	 */
	public Boolean canTakeExpectationAssessment()
	{
		return (getExpectationAssessments().size() <= getKnowledgeAssessments().size());
	}
	
	/**
	 * Determines whether the knowledge assessment is available to take
	 * 
	 * NOTE: this method should be used to ensure that expectation assessments
	 * and knowledge assessments occur in pairs
	 * 
	 * @return true if the knowledge assessment is available, otherwise false
	 */
	public Boolean canTakeKnowledgeAssessment()
	{
		return (getKnowledgeAssessments().size() <= getExpectationAssessments().size());
	}
	
	/**
	 * Determines whether the practice assessment is available to take
	 * 
	 * @param level the practice test level that the user would like to take
	 * 
	 * @return true if the practice test is available, otherwise false
	 */
	public Boolean canTakePracticeAssessment(final Integer level)
	{
		notNull(level, "level cannot be null.");
		if (level < 1 || level > 4)
		{
			throw new IllegalArgumentException("level must be between 1 and 4");
		}
		// if level is higher than the recommended level then student cannot take the assessment  
		if (level > getRecommendedLevel())
		{
			return false;
		}
		return (getKnowledgeAssessments().size() == getExpectationAssessments().size());
	}

	public Boolean hasExpectationAssessment()
	{
		return (!expectationAssessments.isEmpty());
	}

	public List<ExpectationAssessment> getExpectationAssessments()
	{
		return expectationAssessments;
	}

	public ExpectationAssessment getCurrentExpectationAssessment()
	{
		return getLastExpectationAssessment(expectationAssessments);
	}

	public ExpectationAssessment getExpectationAssessment(final Integer index)
	{
		return expectationAssessments.get(index);
	}

	public void addExpectationAssessment(final ExpectationAssessment assessment)
	{
		addExpectationAssessment(expectationAssessments, assessment);
	}

	public Boolean hasKnowledgeAssessment()
	{
		return (!knowledgeAssessments.isEmpty());
	}

	public List<KnowledgeAssessment> getKnowledgeAssessments()
	{
		return knowledgeAssessments;
	}

	public KnowledgeAssessment getCurrentKnowledgeAssessment()
	{
		return getLastKnowledgeAssessment(getKnowledgeAssessments());
	}

	public KnowledgeAssessment getKnowledgeAssessment(final Integer index)
	{
		return knowledgeAssessments.get(index);
	}

	public void addKnowledgeAssessment(final KnowledgeAssessment assessment)
	{
		addKnowledgeAssessment(knowledgeAssessments, assessment);
	}

	public Boolean hasLevel1Practice()
	{
		return (!level1Practices.isEmpty());
	}

	public List<KnowledgeAssessment> getLevel1Practices()
	{
		return level1Practices;
	}

	public KnowledgeAssessment getCurrentLevel1Practice()
	{
		return getLastKnowledgeAssessment(getLevel1Practices());
	}

	public KnowledgeAssessment getLevel1Practice(final Integer index)
	{
		return level1Practices.get(index);
	}

	public void addLevel1Practice(final KnowledgeAssessment test)
	{
		addKnowledgeAssessment(level1Practices, test);
	}

	public Boolean hasLevel2Practice()
	{
		return (!level2Practices.isEmpty());
	}

	public List<KnowledgeAssessment> getLevel2Practices()
	{
		return level2Practices;
	}

	public KnowledgeAssessment getCurrentLevel2Practice()
	{
		return getLastKnowledgeAssessment(getLevel2Practices());
	}

	public KnowledgeAssessment getLevel2Practice(final Integer index)
	{
		return level2Practices.get(index);
	}

	public void addLevel2Practice(final KnowledgeAssessment test)
	{
		addKnowledgeAssessment(level2Practices, test);
	}

	public Boolean hasLevel3Practice()
	{
		return (!level3Practices.isEmpty());
	}

	public List<KnowledgeAssessment> getLevel3Practices()
	{
		return level3Practices;
	}

	public KnowledgeAssessment getCurrentLevel3Practice()
	{
		return getLastKnowledgeAssessment(getLevel3Practices());
	}

	public KnowledgeAssessment getLevel3Practice(final Integer index)
	{
		return level3Practices.get(index);
	}

	public void addLevel3Practice(final KnowledgeAssessment test)
	{
		addKnowledgeAssessment(level3Practices, test);
	}

	public Boolean hasLevel4Practice()
	{
		return (!level4Practices.isEmpty());
	}

	public List<KnowledgeAssessment> getLevel4Practices()
	{
		return level4Practices;
	}

	public KnowledgeAssessment getCurrentLevel4Practice()
	{
		return getLastKnowledgeAssessment(getLevel4Practices());
	}

	public KnowledgeAssessment getLevel4Practice(final Integer index)
	{
		return level4Practices.get(index);
	}

	public void addLevel4Practice(final KnowledgeAssessment test)
	{
		addKnowledgeAssessment(level4Practices, test);
	}

	public Date getCreated()
	{
		return created;
	}

	private void assertUserIsAStudent(final User user)
	{
		if (!user.hasRole(RoleType.STUDENT))
		{
			throw new BusinessRuleException(
					"Cannot add this student transcript - user is not a student.");
		}
	}

	private void assertTranscriptOpen()
	{
		if (getActiveStatus() == TranscriptActiveStatus.CLOSED)
		{
			throw new BusinessRuleException(
					"Cannot modify this transcript. It is currently closed.");
		}
	}

	private void assertProgressStatusEqualTo(final TranscriptProgressStatus aProgressStatus)
	{
		if (!isProgressStatusEqualTo(aProgressStatus))
		{
			throw new BusinessRuleException(
					"Invalid progress transition: cannot change the progress status from "
							+ getProgressStatus() + " to " + aProgressStatus);
		}
	}
	
	private Boolean isProgressStatusEqualTo(final TranscriptProgressStatus aProgressStatus)
	{
		return (getProgressStatus() == aProgressStatus);
	}

	private void assertLastExpectationAssessmentCompletedOrClosed(
			final List<ExpectationAssessment> assessments)
	{
		final ExpectationAssessment assessment = getLastExpectationAssessment(assessments);
		if (assessment != null && assessment.getStatus() == AssessmentStatus.INCOMPLETE)
		{
			throw new BusinessRuleException(
					"Cannot add an expectation assessment to this transcript until the previous assessment is either completed or closed.");
		}
	}

	private void assertLastKnowledgeAssessmentCompletedOrClosed(
			final List<KnowledgeAssessment> assessments)
	{
		final KnowledgeAssessment assessment = getLastKnowledgeAssessment(assessments);
		if (assessment != null && assessment.getStatus() == AssessmentStatus.INCOMPLETE)
		{
			throw new BusinessRuleException(
					"Cannot add an knowledge assessment to this transcript until the previous assessment is either completed or closed.");
		}
	}

	private void addExpectationAssessment(final List<ExpectationAssessment> assessments,
			final ExpectationAssessment assessment)
	{
		assertTranscriptOpen();
		assertLastExpectationAssessmentCompletedOrClosed(assessments);
		assessments.add(assessment);
	}

	private void addKnowledgeAssessment(final List<KnowledgeAssessment> assessments,
			final KnowledgeAssessment assessment)
	{
		assertTranscriptOpen();
		assertLastKnowledgeAssessmentCompletedOrClosed(assessments);
		assessments.add(assessment);
	}

	private void setTranscriptStatus(final TranscriptActiveStatus transcriptStatus)
	{
		this.activeStatus = transcriptStatus;
	}

	private void setProgressStatus(final TranscriptProgressStatus progressStatus)
	{
		this.progressStatus = progressStatus;
	}

	private ExpectationAssessment getLastExpectationAssessment(
			final List<ExpectationAssessment> assessments)
	{
		if (assessments.isEmpty())
			return null;
		return assessments.get(assessments.size() - 1);
	}

	private KnowledgeAssessment getLastKnowledgeAssessment(
			final List<KnowledgeAssessment> assessments)
	{
		if (assessments.isEmpty())
			return null;
		return assessments.get(assessments.size() - 1);
	}
}
