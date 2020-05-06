//package com.herokuapp.voteforlunch.repository;
//
//import com.herokuapp.voteforlunch.model.Dish;
//import com.herokuapp.voteforlunch.model.Menu;
//import com.herokuapp.voteforlunch.model.Restaurant;
//import com.herokuapp.voteforlunch.model.User;
//import org.springframework.data.jpa.repository.EntityGraph;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Transactional(readOnly = true)
//public interface CrudMenuRepository extends JpaRepository<Menu, Long> {
//
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM Menu m WHERE m.id=:id")
//    int delete(@Param("id") long id);
//
//    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query("SELECT m FROM Menu m WHERE m.id=?1")
//    Menu getWithDishes(long id);
//}
