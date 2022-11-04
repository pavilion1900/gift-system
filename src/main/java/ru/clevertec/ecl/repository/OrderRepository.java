package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.clevertec.ecl.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @EntityGraph(attributePaths = {"user", "certificate"})
    Page<Order> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user", "certificate"})
    Optional<Order> findById(Integer id);

    @EntityGraph(attributePaths = {"user", "certificate"})
    List<Order> findAllByUserId(Pageable pageable, Integer userId);

    @Query(value = "SELECT last_value FROM orders_id_seq", nativeQuery = true)
    Integer findLastSequenceValue();

    @Query(value = "SELECT SETVAL('orders_id_seq', :sequenceValue)", nativeQuery = true)
    Integer setSequenceValue(@Param("sequenceValue") Integer sequenceValue);
}
