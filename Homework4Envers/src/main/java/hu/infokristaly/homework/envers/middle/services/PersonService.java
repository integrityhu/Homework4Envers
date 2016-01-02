package hu.infokristaly.homework.envers.middle.services;

import hu.infokristaly.homework.envers.back.model.Address;
import hu.infokristaly.homework.envers.back.model.Person;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

@Stateless
@Named
public class PersonService {

	@Inject
	private EntityManager em;
	
	public void addAddress() {		
		Address address1 = em.find(Address.class, 1);
		Person person2 = em.find(Person.class, 1);

		// Changing the address's house number
		address1.setHouseNumber(5);
		em.merge(address1);
		// And moving Hermione to Harry
		person2.setAddress(address1);
		em.merge(person2);		
	}
	
	public void persistFirstVersion() {
		Address address1 = new Address("Grimmauld Place", 12);
		Person person2 = new Person("Zoltan","Papp");
		em.persist(address1);
		em.persist(person2);		
	}
	public void readRevisions(Person person2, Address address1) {
		AuditReader reader = AuditReaderFactory.get(em);

		Person person2_rev1 = reader.find(Person.class, person2.getId(), 1);
		//assert person2_rev1.getAddress().equals(new Address("Grimmauld Place", 12));

		Address address1_rev1 = reader.find(Address.class, address1.getId(), 1);
		//assert address1_rev1.getPersons().size() == 1;
	}
}
