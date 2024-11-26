package src.controllers;

import src.models.Professor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorController {

    private final String filePath = "../professors.txt";

    public List<Professor> getAllProfessors() {
        List<Professor> professors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Professor professor = new Professor(
                        Integer.parseInt(data[0]), // id
                        data[1],                  // name
                        Integer.parseInt(data[2]),// age
                        data[3],                  // specialty
                        data[4]                   // registration
                );
                professors.add(professor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return professors;
    }

    public Professor getProfessorById(int id) {
        return getAllProfessors().stream()
                .filter(professor -> professor.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean addProfessor(Professor professor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(professor.getId() + "," +
                    professor.getName() + "," +
                    professor.getAge() + "," +
                    professor.getSpecialty() + "," +
                    professor.getRegistration());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProfessor(Professor professor) {
        List<Professor> professors = getAllProfessors();
        boolean updated = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Professor p : professors) {
                if (p.getId() == professor.getId()) {
                    writer.write(professor.getId() + "," +
                            professor.getName() + "," +
                            professor.getAge() + "," +
                            professor.getSpecialty() + "," +
                            professor.getRegistration());
                    updated = true;
                } else {
                    writer.write(p.getId() + "," +
                            p.getName() + "," +
                            p.getAge() + "," +
                            p.getSpecialty() + "," +
                            p.getRegistration());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return updated;
    }

    public boolean deleteProfessor(int id) {
        List<Professor> professors = getAllProfessors();
        boolean deleted = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Professor professor : professors) {
                if (professor.getId() != id) {
                    writer.write(professor.getId() + "," +
                            professor.getName() + "," +
                            professor.getAge() + "," +
                            professor.getSpecialty() + "," +
                            professor.getRegistration());
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
