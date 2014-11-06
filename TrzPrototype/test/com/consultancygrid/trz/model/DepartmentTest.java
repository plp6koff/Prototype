package com.consultancygrid.trz.model;


import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

public class DepartmentTest {

	  private static final String PERSISTENCE_UNIT_NAME = "trzUnit";
	  private EntityManagerFactory factory;

	  @Before
	  public void setUp() throws Exception {
		  
	    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();


	  }
	  
	 // @Test
	  public void selectMoreThanOneTable(){
		  
		  EntityManager em = factory.createEntityManager();
		  // read the existing entries
		  Query q = em.createQuery(" from Department");
		  assertTrue(q.getResultList().size() >0);
		  for(Department d : (List<Department>)q.getResultList()){
			System.out.println(d.getId().toString()); 
			
		  }
		  em.close();
	  }
	  

	  @Test
	  public void selectDepartmentByEmployePeriods() {
	    EntityManager em = factory.createEntityManager();
	    Department dep  = new Department();
	    
	    Query q1 = em.createQuery("select emplDeptP.department from EmplDeptPeriod emplDeptP  where  emplDeptP.period.id = :periodId");
	    q1.setParameter("periodId",UUID.fromString("c247eee4-cc34-4a22-a0a6-e83782b63aee"));
	    
	    List<Department> departments  = (List<Department>) q1.getResultList();
	    // We should have one family with 40 persons
	    assertFalse(departments.isEmpty());
	    em.close();
	  }


} 