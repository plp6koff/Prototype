package com.consultancygrid.trz.model;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * Custom UUID Generator.
 * Hibernate generates UUID in form of 8-8-4-8-4, but
 * the standard is 8-4-4-4-12.
 * 
 * @author Murad M. M.
 */
public class CustomUUIDGenerator implements IdentifierGenerator {

    public Serializable generate(SessionImplementor session, Object object)
	    throws HibernateException {
        return UUID.randomUUID();
    }

}
