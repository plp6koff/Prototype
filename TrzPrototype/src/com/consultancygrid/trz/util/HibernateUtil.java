package com.consultancygrid.trz.util;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

	 private static EntityManagerFactory factory;
     private static EntityManager entityManager;

     public static void initiate(){
    	 
         factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
         entityManager=factory.createEntityManager();
     }

     public static EntityManager getEntityManager() {
         return entityManager;
     }

     public static void close(){
         entityManager.close();
         factory.close();
     }


}