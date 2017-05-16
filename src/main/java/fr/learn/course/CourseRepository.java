package fr.learn.course;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.learn.dao.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
