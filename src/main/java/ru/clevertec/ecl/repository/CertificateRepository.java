package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.clevertec.ecl.entity.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

    @EntityGraph(attributePaths = {"tags"})
    Page<Certificate> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"tags"})
    Page<Certificate> findAll(Example example, Pageable pageable);

    @Query("select c from Certificate c join fetch c.tags t where upper(t.name) = upper(:tagName)")
    List<Certificate> findAllByTagName(@Param("tagName") String tagName, Pageable pageable);

    @Query("select c from Certificate c join fetch c.tags t where t.name in (:tagNames)")
    List<Certificate> findAllBySeveralTagNames(@Param("tagNames") List<String> tagNames,
                                               Pageable pageable);

    @EntityGraph(attributePaths = {"tags"})
    Optional<Certificate> findById(Integer id);

    @EntityGraph(attributePaths = {"tags"})
    Optional<Certificate> findByNameIgnoreCase(String certificateName);

    boolean existsByNameIgnoreCase(String certificateName);
}
