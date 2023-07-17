package com.example.spring.Controller;

import com.example.spring.Model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("admin/api/v1/students")
public class AdminStudentController {
    private static final List<Student> students = Arrays.asList(
            new Student(1,"james"),
            new Student(2,"curry"),
            new Student(3,"durrant")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public ResponseEntity<List<Student>> getAllStudent(){
        return  ResponseEntity.ok().body(students);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public void createStudent(@RequestBody Student student){
        System.out.printf("avac");
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('user:write')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){

    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('user:write')")
    public void updateStudent(@PathVariable("studentId")Integer studentId,@RequestBody Student student){

    }
}
