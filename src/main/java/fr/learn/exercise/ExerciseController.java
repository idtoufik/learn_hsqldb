package fr.learn.exercise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.learn.dao.Exercise;
import fr.learn.dao.Lesson;
import fr.learn.dao.Member;
import fr.learn.lesson.LessonService;
import fr.learn.member.MemberService;

@RestController
public class ExerciseController {
	
	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private LessonService lessonService;
	
	
	@RequestMapping(value="/resources/courses/{idCourse}/lessons/{idLesson}/exercises",
			method=RequestMethod.GET)
	public List<Exercise> getAllExercisesOfLesson(@PathVariable Long idLesson)
	{
		List<Exercise> exercises =  exerciseService.getAllExercisesOfLesson(idLesson);
		for(Exercise exo: exercises){
			exo.getLesson().setCourse(null);
			exo.getLesson().setExercises(null);
		}
		return exercises;
	}
	
	@RequestMapping(value="/resources/courses/{idCourse}/lessons/{idLesson}/exercises/{idExercise}",
			method=RequestMethod.GET)
	public Exercise getExerciseById(@PathVariable Long idExercise)
	{
		return exerciseService.getExerciseById(idExercise);
	}
	
	
	@RequestMapping(value="/resources/courses/{idCourse}/lessons/{idLesson}/exercises",
			method=RequestMethod.POST)
	public boolean addExercise(@PathVariable Long idLesson, @RequestBody Exercise exercise)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member loggedIn = memberService.getMemberFromAuthentification(auth);
		
		if(loggedIn == null)
			return false;
		Lesson lesson = lessonService.getLesson(idLesson);
		if(lesson == null)
			return false;
		
		if(lesson.getCourse().getMember().getId().equals(loggedIn.getId()))
		{
			exercise.setLesson(lesson);
			return exerciseService.addExercise(exercise);
		}
		return false;
	}
	
	@RequestMapping(value="/resources/courses/{idCourse}/lessons/{idLesson}/exercises/{idExercise}",
			method=RequestMethod.PUT)
	public boolean updateExercise(@PathVariable Long idExercise, @RequestBody Exercise exercise)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member loggedIn = memberService.getMemberFromAuthentification(auth);
		
		if(loggedIn == null)
			return false;
		Exercise oldExercise = exerciseService.getExerciseById(idExercise);
		
		if(oldExercise == null)
			return false;
		if(loggedIn.getId().equals(oldExercise.getLesson().getCourse().getMember().getId()))
		{
			exercise.setId(idExercise);
			exercise.setLesson(oldExercise.getLesson());
			return exerciseService.updateExercise(exercise);
		}
		
		return false;
		
	}
	
	@RequestMapping(value="/resources/courses/{idCourse}/lessons/{idLesson}/exercises/{idExercise}",
			method=RequestMethod.DELETE)
	public boolean deleteExercise(@PathVariable Long idExercise)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member loggedIn = memberService.getMemberFromAuthentification(auth);
		
		if(loggedIn == null)
			return false;
		Exercise exercise = exerciseService.getExerciseById(idExercise);
		
		if(exercise == null)
			return false;
		if(loggedIn.getId().equals(exercise.getLesson().getCourse().getMember().getId()))
		{
			exerciseService.deleteExercise(idExercise);
			return true;
		}
		
		return false;
	}

}
