package com.karenchidester.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.karenchidester.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query ... sort by last name
		Query<Customer> theQuery = 
				currentSession.createQuery("from Customer order by lastName", 
											Customer.class);
		
		//execute query and get result list
		List<Customer> customers = theQuery.getResultList(); 
		
		//return the results
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		//get hibernate session
		Session mySession = sessionFactory.getCurrentSession();
		
		//save/update the customer
		mySession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		// get hibernate session
		Session mySession = sessionFactory.getCurrentSession();
		
		//read from database using theId
		Customer theCustomer = mySession.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		Session mySession = sessionFactory.getCurrentSession();
		
		Query theQuery = mySession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
	}

}
