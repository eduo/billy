/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy core.
 * 
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test.services.builders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockCustomerEntity;

public class TestCustomerBuilder extends AbstractTest {

	private static final String CUSTOMER_YML = "src/test/resources/Customer.yml";

	@Test
	public void doTest() {
		MockCustomerEntity mockCustomer = createMockEntity(
				MockCustomerEntity.class, CUSTOMER_YML);

		Mockito.when(getInstance(DAOCustomer.class).getEntityInstance())
				.thenReturn(new MockCustomerEntity());

		Mockito.when(getInstance(DAOContact.class).get(Matchers.any(UID.class)))
				.thenReturn((ContactEntity) mockCustomer.getMainContact());

		Customer.Builder builder = getInstance(Customer.Builder.class);

		Address.Builder mockMainAddressBuilder = this
				.getMock(Address.Builder.class);
		Mockito.when(mockMainAddressBuilder.build()).thenReturn(
				mockCustomer.getMainAddress());

		Address.Builder mockBillingAddressBuilder = this
				.getMock(Address.Builder.class);
		Mockito.when(mockBillingAddressBuilder.build()).thenReturn(
				mockCustomer.getBillingAddress());

		Address.Builder mockShippingAddressBuilder = this
				.getMock(Address.Builder.class);
		Mockito.when(mockShippingAddressBuilder.build()).thenReturn(
				mockCustomer.getShippingAddress());

		BankAccount.Builder mockBankAccountBuilder1 = this
				.getMock(BankAccount.Builder.class);
		Mockito.when(mockBankAccountBuilder1.build()).thenReturn(
				mockCustomer.getBankAccounts().get(0));

		BankAccount.Builder mockBankAccountBuilder2 = this
				.getMock(BankAccount.Builder.class);
		Mockito.when(mockBankAccountBuilder2.build()).thenReturn(
				mockCustomer.getBankAccounts().get(1));

		Contact.Builder mockMainContactBuilder = this
				.getMock(Contact.Builder.class);
		Mockito.when(mockMainContactBuilder.build()).thenReturn(
				mockCustomer.getMainContact());

		Contact.Builder mockContactBuilder1 = this
				.getMock(Contact.Builder.class);
		Mockito.when(mockContactBuilder1.build()).thenReturn(
				mockCustomer.getContacts().get(0));

		Contact.Builder mockContactBuilder2 = this
				.getMock(Contact.Builder.class);
		Mockito.when(mockContactBuilder2.build()).thenReturn(
				mockCustomer.getContacts().get(1));

		builder.addBankAccount(mockBankAccountBuilder1)
				.addAddress(mockMainAddressBuilder, true)
				.addContact(mockMainContactBuilder)
				.addContact(mockContactBuilder1)
				.addContact(mockContactBuilder2)
				.setBillingAddress(mockBillingAddressBuilder)
				.setHasSelfBillingAgreement(
						mockCustomer.hasSelfBillingAgreement())
				.setName(mockCustomer.getName())
				.setShippingAddress(mockShippingAddressBuilder)
				.setTaxRegistrationNumber(
						mockCustomer.getTaxRegistrationNumber())
				.setMainContactUID(mockCustomer.getMainContact().getUID());

		Customer customer = builder.build();

		assertTrue(customer != null);

		assertEquals(mockCustomer.getName(), customer.getName());
		assertEquals(mockCustomer.getTaxRegistrationNumber(),
				customer.getTaxRegistrationNumber());
		assertEquals(mockCustomer.getMainAddress(), customer.getMainAddress());
		assertEquals(mockCustomer.getShippingAddress(),
				customer.getShippingAddress());
		assertEquals(mockCustomer.hasSelfBillingAgreement(),
				customer.hasSelfBillingAgreement());
	}
}
