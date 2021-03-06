package com.consultancygrid.trz.model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

/**
 * Custom UserType for mapping UUID columns.
 * 
 * @author Murad M. M.
 */
public class UUIDType implements UserType, Serializable {

	private static final long serialVersionUID = 1L;

	private static final String CAST_EXCEPTION_TEXT = " cannot be cast to a java.util.UUID.";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	public int[] sqlTypes() {
		return new int[] { Types.CHAR };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	public Class<?> returnedClass() {
		return UUID.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object,
	 * java.lang.Object)
	 */
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == null && y == null) {
			return true;
		} else if (x == null || y == null) {
			return false;
		}
		if (!UUID.class.isAssignableFrom(x.getClass())) {
			throw new HibernateException(x.getClass().toString()
					+ CAST_EXCEPTION_TEXT);
		} else if (!UUID.class.isAssignableFrom(y.getClass())) {
			throw new HibernateException(y.getClass().toString()
					+ CAST_EXCEPTION_TEXT);
		}

		UUID a = (UUID) x;
		UUID b = (UUID) y;

		return a.equals(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */
	public int hashCode(Object x) throws HibernateException {
		if (!UUID.class.isAssignableFrom(x.getClass())) {
			throw new HibernateException(x.getClass().toString()
					+ CAST_EXCEPTION_TEXT);
		}
		return x.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */
	public Object deepCopy(Object value) throws HibernateException {
		if (!UUID.class.isAssignableFrom(value.getClass())) {
			throw new HibernateException(value.getClass().toString()
					+ CAST_EXCEPTION_TEXT);
		}

		UUID other = (UUID) value;

		return UUID.fromString(other.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#isMutable()
	 */
	public boolean isMutable() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable,
	 * java.lang.Object)
	 */
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		if (!String.class.isAssignableFrom(cached.getClass())) {
			return null;
		}

		return UUID.fromString((String) cached);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */
	public Serializable disassemble(Object value) throws HibernateException {
		return value.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#replace(java.lang.Object,
	 * java.lang.Object, java.lang.Object)
	 */
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		if (!UUID.class.isAssignableFrom(original.getClass())) {
			throw new HibernateException(original.getClass().toString()
					+ CAST_EXCEPTION_TEXT);
		}

		return UUID.fromString(original.toString());
	}

	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		String value = rs.getString(names[0]);
		if (value == null) {
			return null;
		} else {
			return UUID.fromString(value);
		}
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.VARCHAR);
			return;
		}

		if (!UUID.class.isAssignableFrom(value.getClass())) {
			throw new HibernateException(value.getClass().toString()
					+ CAST_EXCEPTION_TEXT);
		}

		st.setString(index, value.toString());

	}

}
