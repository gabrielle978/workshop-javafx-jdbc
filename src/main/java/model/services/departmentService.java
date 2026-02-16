package model.services;

import model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class departmentService {
    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        list.add(new Department(1, "eletronics"));
        list.add(new Department(2, "books"));
        list.add(new Department(3, "wines"));
        return list;
    }
}
