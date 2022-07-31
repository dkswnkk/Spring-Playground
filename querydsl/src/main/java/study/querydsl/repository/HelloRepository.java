package study.querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.querydsl.entity.Hello;

public interface HelloRepository extends JpaRepository<Hello, Long> {
}
