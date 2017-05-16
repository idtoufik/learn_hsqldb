package fr.learn.exercise;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.learn.dao.Exercise;

@Service
public class ExerciseService {

	@Autowired
	private ExerciseRepository exerciseRepository;
	
	public List<Exercise> getAllExercisesOfLesson(Long lessonId)
	{
		List<Exercise> exercises = new ArrayList<Exercise>();
		exerciseRepository.findByLessonId(lessonId).forEach(exercises::add);
		return exercises;
	}
	
	public Exercise getExerciseById(Long exerciseId)
	{
		return exerciseRepository.findOne(exerciseId);
	}
	
	public boolean addExercise(Exercise exercise)
	{
		exercise.setId(null);
		try
		{
			exerciseRepository.save(exercise);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
		
		
	}
	
	public boolean updateExercise(Exercise exercise)
	{
		try
		{
			exerciseRepository.save(exercise);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public void deleteExercise(Long exerciseId)
	{
		exerciseRepository.delete(exerciseId);
	}
	
	
	
	
	
}
