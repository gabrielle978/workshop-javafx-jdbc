package model.services;

import DAO.DaoFactory;
import DAO.SellerDAO;
import model.entities.Seller;

import java.util.List;

public class sellerService {
    private SellerDAO dao = DaoFactory.createSellerDao();
    public List<Seller> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate (Seller obj){
        if (obj.getId() == null){
            dao.insert(obj);
        }
        else{
            dao.update(obj);
        }
    }

    public void remove(Seller obj){
        dao.deleteById(obj.getId());
    }
}
