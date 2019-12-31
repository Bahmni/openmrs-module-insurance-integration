package org.nepalehr.imisintegration.bed.dao;

import java.util.List;

public interface BedDao {

	/**
	 * This method is used to return all uuids of all bed assignments
	 * 
	 * @return {@link List} of uuids of all the active bed assignemnts
	 */
	List<String> getActiveBedAssignmentUuids();
}
