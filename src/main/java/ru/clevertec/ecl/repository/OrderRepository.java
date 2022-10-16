package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
