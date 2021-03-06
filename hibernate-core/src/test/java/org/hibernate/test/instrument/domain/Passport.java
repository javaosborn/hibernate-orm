/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2014, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.test.instrument.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * @author Steve Ebersole
 */
@javax.persistence.Entity
public class Passport {
	private Integer id;
	private Person person;
	private String number;
	private String issuingCountry;
	private Date issueDate;
	private Date expirationDate;

	public Passport() {
	}

	public Passport(Person person, String number, String issuingCountry) {
		this.person = person;
		this.number = number;
		this.issuingCountry = issuingCountry;

		this.issueDate = new Date();

		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime( issueDate );
		calendar.set( Calendar.YEAR, calendar.get( Calendar.YEAR ) + 10 );
		this.expirationDate = calendar.getTime();

		this.person.setPassport( this );
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne(fetch= FetchType.LAZY, cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@LazyToOne(value = LazyToOneOption.NO_PROXY)
	@JoinColumn(name="person_id")
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getIssuingCountry() {
		return issuingCountry;
	}

	public void setIssuingCountry(String issuingCountry) {
		this.issuingCountry = issuingCountry;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
}
