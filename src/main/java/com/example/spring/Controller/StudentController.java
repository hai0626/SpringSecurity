package com.example.spring.Controller;

import com.example.spring.Model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private static final List<Student> students = Arrays.asList(
            new Student(1,"james"),
            new Student(2,"curry"),
            new Student(3,"durrant")
    );

    @GetMapping()
    public ResponseEntity<List<Student>> getAllStudent(){
        return  ResponseEntity.ok().body(students);
    }

    @GetMapping(path = "/{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId){
        return students.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(()-> new IllegalStateException("Student " + studentId + " does not exist"));
    }
}
