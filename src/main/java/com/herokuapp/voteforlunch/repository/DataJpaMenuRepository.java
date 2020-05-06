//package com.herokuapp.voteforlunch.repository;
//
//import com.herokuapp.voteforlunch.model.Menu;
//import com.herokuapp.voteforlunch.model.Restaurant;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//@Repository
//public class DataJpaMenuRepository implements MenuRepository {
//
//    private final CrudMenuRepository crudMenuRepository;
//
//    public DataJpaMenuRepository(CrudMenuRepository crudMenuRepository) {
//        this.crudMenuRepository = crudMenuRepository;
//    }
//
//    @Override
//    @Transactional
//    public Menu save(Menu menu) {
//        return crudMenuRepository.save(menu);
//    }
//
//    @Override
//    public boolean delete(long id) {
//        return crudMenuRepository.delete(id) != 0;
//    }
//
//    @Override
//    public Menu get(long id) {
//        return crudMenuRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public Menu getWithDishes(long id) {
//        return crudMenuRepository.getWithDishes(id);
//    }
//}
