package com.greatlearning.student.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.greatlearning.student.entity.Student;

@Repository
public class StudentServiceImpl implements StudentService {

	private SessionFactory sessionFactory;

	private Session session;

	@Autowired
	StudentServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

	}

	@Transactional
	public List<Student> findAll() {
		
		Transaction tx = session.beginTransaction();

		List<Student> students = session.createQuery("from Student").list();

		tx.commit();

		return students;
	}

	@Transactional
	public Student findById(int id) {

		Student student = new Student();

		
		Transaction tx = session.beginTransaction();

		student = session.get(Student.class, id);

		tx.commit();

		return student;
	}

	@Transactional
	public void save(Student theStudent) {

		
		Transaction tx = session.beginTransaction();

		session.saveOrUpdate(theStudent);

		tx.commit();

	}

	@Transactional
	public void deleteById(int id) {

	
		Transaction tx = session.beginTransaction();

		Student student = session.get(Student.class, id);

		session.delete(student);

		tx.commit();

	}

	@Transactional
	public List<Student> searchBy(String Name, String country) {

		
		Transaction tx = session.beginTransaction();
		String query = "";
		if (Name.length() != 0 && country.length() != 0)
			query = "from Student where name like '%" + Name + "%' or country like '%" + country + "%'";
		else if (Name.length() != 0)
			query = "from Student where name like '%" + Name + "%'";
		else if (country.length() != 0)
			query = "from Student where country like '%" + country + "%'";
		else
			System.out.println("Cannot search without input data");

		List<Student> student = session.createQuery(query).list();

		tx.commit();

		return student;
	}

	@Transactional
	public void print(List<Student> student) {

		for (Student b : student) {
			System.out.println(b);
		}
	}

}