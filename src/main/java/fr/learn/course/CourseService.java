package fr.learn.course;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.learn.dao.Course;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	public List<Course> findAll(){
		 List<Course> courses = new ArrayList<>();
		 courseRepository.findAll().forEach(courses::add);
		 return courses;
	}
	
	public Course findById(long id){
		 return courseRepository.findOne(id);
	}
	
	public boolean addCourse(Course course){
		course.setId(null);
		try
		{
			courseRepository.save(course);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public void deleteCourse(long id){
		courseRepository.delete(id);
	}
	
	public boolean modifyCourse(Course course){
		try
		{
			courseRepository.save(course);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
}
