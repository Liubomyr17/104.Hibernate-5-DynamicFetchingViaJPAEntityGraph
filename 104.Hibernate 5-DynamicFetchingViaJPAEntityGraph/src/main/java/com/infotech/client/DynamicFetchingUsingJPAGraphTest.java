package com.infotech.client;

import com.infotech.entities.Department;
import com.infotech.entities.Employee;
import com.infotech.entities.Project;
import com.infotech.util.HibernateUtil;
import org.hibernate.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

public class DynamicFetchingUsingJPAGraphTest {

    public static void main(String[] args) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Dynamic HQL fetching example
            Long employeeId = 1L;
            Employee employee = session.find(Employee.class, employeeId,
                    Collections.singletonMap("javax.persistence.fetchgraph",
                            session.getEntityGraph("employee.projects")));

            if (employee != null) {
                System.out.println("Employee details::::::");
                System.out.println(employee.getId() + "\t" + employee.getEmployeeName() + "\t" + employee.getUsername()
                        + "\t" + employee.getPassword() + "\t" + employee.getAccessLevel());

                System.out.println("Employee projects details::::::");
                List<Project> projects = employee.getProjects();
                for (Project project : projects) {
                    System.out.println(project.getId()+"\t"+project.getProjectName());
                }
                System.out.println("Employee's department details::::::");
                Department department = employee.getDepartment();
                if (department != null)
                    System.out.println(department.getId() + "\t" + department.getDeptName());
                else
                    System.out.println("Department details not found");

            } else {
                System.out.println("Employee not found with  provided credential");
            }
        }
    }
}