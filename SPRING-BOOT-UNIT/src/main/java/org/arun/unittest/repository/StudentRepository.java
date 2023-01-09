package org.arun.unittest.repository;

import org.arun.unittest.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student , Long> {
}
