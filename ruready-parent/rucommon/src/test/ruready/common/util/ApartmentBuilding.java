/*******************************************************************************
 * Source File: ApartmentBuilding.java
 ******************************************************************************/
package test.ruready.common.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.ruready.common.util.HashCodeUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An example class for hash code utility testing.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 
 *         2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 12, 2007
 */
public class ApartmentBuilding
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ApartmentBuilding.class);

	// ========================= FIELDS ====================================

	/**
	 * The following fields are chosen to exercise most of the different cases.
	 */
	private boolean fIsDecrepit;

	private char fRating;

	private int fNumApartments;

	private long fNumTenants;

	private double fPowerUsage;

	private float fWaterUsage;

	private byte fNumFloors;

	private String fName; // possibly null, say

	private List<?> fOptions; // never null

	private Date[] fMaintenanceChecks; // never null

	private int fHashCode;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create from fields.
	 * 
	 * @param aIsDecrepit
	 * @param aRating
	 * @param aNumApartments
	 * @param aNumTenants
	 * @param aPowerUsage
	 * @param aWaterUsage
	 * @param aNumFloors
	 * @param aName
	 * @param aOptions
	 * @param aMaintenanceChecks
	 */
	public ApartmentBuilding(boolean aIsDecrepit, char aRating, int aNumApartments,
			long aNumTenants, double aPowerUsage, float aWaterUsage, byte aNumFloors,
			String aName, List<?> aOptions, Date[] aMaintenanceChecks)
	{
		fIsDecrepit = aIsDecrepit;
		fRating = aRating;
		fNumApartments = aNumApartments;
		fNumTenants = aNumTenants;
		fPowerUsage = aPowerUsage;
		fWaterUsage = aWaterUsage;
		fNumFloors = aNumFloors;
		fName = aName;
		fOptions = aOptions;
		fMaintenanceChecks = aMaintenanceChecks;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		// Standard checks to ensure the adherence to the general contract of
		// the equals() method
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		// Cast to friendlier version
		ApartmentBuilding other = (ApartmentBuilding) obj;

		return this.hasEqualState(other);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		// this style of lazy initialization is
		// suitable only if the object is immutable
		if (fHashCode == 0) {
			int result = HashCodeUtil.SEED;
			result = HashCodeUtil.hash(result, fIsDecrepit);
			result = HashCodeUtil.hash(result, fRating);
			result = HashCodeUtil.hash(result, fNumApartments);
			result = HashCodeUtil.hash(result, fNumTenants);
			result = HashCodeUtil.hash(result, fPowerUsage);
			result = HashCodeUtil.hash(result, fWaterUsage);
			result = HashCodeUtil.hash(result, fNumFloors);
			result = HashCodeUtil.hash(result, fName);
			result = HashCodeUtil.hash(result, fOptions);
			result = HashCodeUtil.hash(result, fMaintenanceChecks);
			fHashCode = result;
		}
		return fHashCode;
	}

	// ========================= PUBLIC METHODS ============================

	// ..other methods elided

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Here, for two ApartmentBuildings to be equal, all fields must be equal.
	 */
	private boolean hasEqualState(ApartmentBuilding that)
	{
		// note the different treatment for possibly-null fields
		return (this.fName == null ? that.fName == null : this.fName.equals(that.fName))
				&& (this.fIsDecrepit == that.fIsDecrepit)
				&& (this.fRating == that.fRating)
				&& (this.fNumApartments == that.fNumApartments)
				&& (this.fNumTenants == that.fNumTenants)
				&& (this.fPowerUsage == that.fPowerUsage)
				&& (this.fWaterUsage == that.fWaterUsage)
				&& (this.fNumFloors == that.fNumFloors)
				&& (this.fOptions.equals(that.fOptions))
				&& (Arrays.equals(this.fMaintenanceChecks, that.fMaintenanceChecks));
	}
}
