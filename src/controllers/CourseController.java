package src.controllers;

import src.models.Course;
import src.models.Professor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseController {

    private final String filePath = "../courses.txt";

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                
                List<String> subscribedStudents = new ArrayList<>();
                if (data.length > 4 && !data[4].isEmpty()) {
                    String[] students = data[4].split(";");
                    for (String studentId : students) {
                        subscribedStudents.add(studentId);
                    }
                }
                
                Professor professor = new ProfessorController().getProfessorById(Integer.parseInt(data[3]));
                
                Course course = new Course(
                    Integer.parseInt(data[0]), 
                    data[1],                  
                    Integer.parseInt(data[2]),
                    professor                 
                );
                
                course.getSubscribedStudentsIds().addAll(subscribedStudents);

                courses.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public boolean addCourse(Course course) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {            
            StringBuilder studentIds = new StringBuilder();
            for (String studentId : course.getSubscribedStudentsIds()) {
                if (studentIds.length() > 0) {
                    studentIds.append(";");
                }
                studentIds.append(studentId);
            }
            
            writer.write(course.getId() + "," +
                    course.getName() + "," +
                    course.getCourseLoad() + "," +
                    course.getProfessor().getId() + "," +
                    studentIds.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCourse(int id) {
        List<Course> courses = getAllCourses();
        boolean deleted = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Course course : courses) {
                if (course.getId() != id) {                    
                    StringBuilder studentIds = new StringBuilder();
                    for (String studentId : course.getSubscribedStudentsIds()) {
                        if (studentIds.length() > 0) {
                            studentIds.append(";");
                        }
                        studentIds.append(studentId);
                    }
                    
                    writer.write(course.getId() + "," +
                            course.getName() + "," +
                            course.getCourseLoad() + "," +
                            course.getProfessor().getId() + "," +
                            studentIds.toString());
                    writer.newLine();
                } else {
                    deleted = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    public boolean updateCourse(Course updatedCourse) {
        List<Course> courses = getAllCourses();
        boolean updated = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Course course : courses) {
                if (course.getId() == updatedCourse.getId()) {                    
                    StringBuilder studentIds = new StringBuilder();
                    for (String studentId : updatedCourse.getSubscribedStudentsIds()) {
                        if (studentIds.length() > 0) {
                            studentIds.append(";");
                        }
                        studentIds.append(studentId);
                    }

                    writer.write(updatedCourse.getId() + "," +
                            updatedCourse.getName() + "," +
                            updatedCourse.getCourseLoad() + "," +
                            updatedCourse.getProfessor().getId() + "," +
                            studentIds.toString());
                    updated = true;
                } else {                    
                    StringBuilder studentIds = new StringBuilder();
                    for (String studentId : course.getSubscribedStudentsIds()) {
                        if (studentIds.length() > 0) {
                            studentIds.append(";");
                        }
                        studentIds.append(studentId);
                    }

                    writer.write(course.getId() + "," +
                            course.getName() + "," +
                            course.getCourseLoad() + "," +
                            course.getProfessor().getId() + "," +
                            studentIds.toString());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return updated;
    }
}
