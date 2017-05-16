package fr.learn.lesson;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.learn.dao.Lesson;

/**
 * 
 */
public  interface LessonRepository extends JpaRepository<Lesson, Long>{

	public List<Lesson> findByCourseId(Long courseId);
}