package model.services;

import DAO.DaoFactory;
import DAO.DepartmentDAO;
import model.entities.Department;

import java.util.List;

public class departmentService {
    private DepartmentDAO dao = DaoFactory.createDepartmentDao();
    public List<Department> findAll() {
        return dao.findAll();
    }
}
