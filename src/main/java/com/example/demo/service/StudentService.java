package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getStudent(){
        return studentRepository.findAll();
    }

    public Student createStudent(@RequestBody Student student) {
        Optional<Student> studentisThere = studentRepository.findByEmail(student.getEmail());
        if (studentisThere.isPresent()){
            throw new IllegalStateException("Student doesn't exist");
        }
        return studentRepository.save(student);
    }

    public String deleteStudent(Long id) {
        Student foundStudent = getStudents(id);
        studentRepository.delete(foundStudent);
        return "Student Deleted";
    }

    public Student getStudents(long id){
        return studentRepository.findById(id).orElseThrow(()
                -> new IllegalStateException("Not Found"));
    }

    public Student updateStudent(long id, Student student) {
        Student foundStudent = getStudents(id);
        foundStudent.setName(student.getName());
        foundStudent.setCity(student.getCity());
        foundStudent.setAge(student.getAge());
        foundStudent.setEmail(student.getEmail());

       return studentRepository.save(foundStudent);

    }
}
