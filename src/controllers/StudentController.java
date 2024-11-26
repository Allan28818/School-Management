package src.controllers;

import src.models.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {

    private final String filePath = "../students.txt";

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Student student = new Student(
                        Integer.parseInt(data[0]), 
                        data[1],                  
                        Integer.parseInt(data[2]),
                        data[3]                   
                );
                students.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return students;
    }

    public Student getStudentById(int id) {
        return getAllStudents().stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean addStudent(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(student.getId() + "," +
                    student.getName() + "," +
                    student.getAge() + "," +
                    student.getRegistration());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStudent(Student student) {
        List<Student> students = getAllStudents();
        boolean updated = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Student s : students) {
                if (s.getId() == student.getId()) {
                    writer.write(student.getId() + "," +
                            student.getName() + "," +
                            student.getAge() + "," +
                            student.getRegistration());
                    updated = true;
                } else {
                    writer.write(s.getId() + "," +
                            s.getName() + "," +
                            s.getAge() + "," +
                            s.getRegistration());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return updated;
    }

    public boolean deleteStudent(int id) {
        List<Student> students = getAllStudents();
        boolean deleted = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Student student : students) {
                if (student.getId() != id) {
                    writer.write(student.getId() + "," +
                            student.getName() + "," +
                            student.getAge() + "," +
                            student.getRegistration());
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
}
