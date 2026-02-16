package DAO;

import model.entities.Department;

import java.util.List;

public interface DepartmentDAO {
    void insert(Department obj);
    void update (Department obj);
    void deleteById (Integer id);
    Department findById (Integer id);
    List<model.entities.Department> findAll();
}
