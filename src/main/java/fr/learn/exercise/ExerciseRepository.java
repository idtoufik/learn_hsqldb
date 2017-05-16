package fr.learn.exercise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.learn.dao.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long>{

	public List<Exercise> findByLessonId(Long lessonId);
}
