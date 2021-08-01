package com.hibernatedemo;

import com.hibernatedemo.entity.EmployeeEntity;
import com.hibernatedemo.util.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateMain {

    public static void main(String[] args) {
        EmployeeEntity employee1 = new EmployeeEntity("John","john@hibernatedemo.com");
        EmployeeEntity employee2 = new EmployeeEntity("Smith","smith@hibernatedemo.com");
        persistEmployee(employee1);
        persistEmployee(employee2);
        getAllEmployees();
        getEmployeeById(2);
        employee2.setEmail("smith.holmes@hibernatedemo.com");
        updateEmployee(employee2);
        getEmployeeById(2);
        deleteEmployee(1);
        getAllEmployees();
    }

    public static void persistEmployee(EmployeeEntity employee) {
        Transaction transaction = null;
        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            System.out.println("Employee " + employee.getEmployeeName() + " saved successfully.");
        } catch (Exception e) {
            System.out.println("Exception occurred while saving Employee " + employee.getEmployeeName());
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public static void getAllEmployees() {
        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            List<EmployeeEntity> employeeEntityList = session.createQuery("from EmployeeEntity", EmployeeEntity.class).list();
            System.out.println("\nEmployee list:");
            employeeEntityList.forEach(System.out::println);
            System.out.println();
        } catch (Exception e) {
            System.out.println("Exception occurred while fetching all employees");
            e.printStackTrace();
        }
    }

    public static void getEmployeeById(Integer id) {
        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            EmployeeEntity employee = session.get(EmployeeEntity.class, id);
            System.out.println("Fetched employee details: " + employee);
        } catch (Exception e) {
            System.out.println("Exception occurred while fetching employee with id: " + id);
            e.printStackTrace();
        }
    }

    public static void updateEmployee(EmployeeEntity employee) {
        Transaction transaction = null;
        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(employee);
            transaction.commit();
            System.out.println("Employee " + employee.getEmployeeName() + " updated successfully.");
        } catch (Exception e) {
            System.out.println("Exception occurred while updating employee " + employee.getEmployeeName());
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public static void deleteEmployee(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            EmployeeEntity employee = session.get(EmployeeEntity.class, id);
            session.remove(employee);
            transaction.commit();
            System.out.println("Employee " + employee.getEmployeeName() + " deleted successfully.");
        } catch (Exception e) {
            System.out.println("Exception occurred while deleting employee with id " + id);
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

}
