package org.nepalehr.imisintegration.bed.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.nepalehr.imisintegration.bed.dao.BedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BedDaoImpl implements BedDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<String> getActiveBedAssignmentUuids() {
		List<String> uuids = new ArrayList<>();
		String queryString = "SELECT bpa.uuid FROM BedPatientAssignment bpa WHERE DATEDIFF(now(), bpa.startDatetime) > 1 AND bpa.endDatetime IS NULL";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		uuids = query.list();
		return uuids;
	}

}
