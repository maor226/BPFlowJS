package com.example.bpflowjs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Helper implements brige {

    @GetMapping("/send_diagram")
    public boolean send_diagram(String diagram) {
        // TODO Auto-generated method stub
        return false;
    }

    @GetMapping("/run_step")
    public DiagramRunStep run_step() {
        // TODO Auto-generated method stub
        return null;
    }

    @GetMapping("/run_diagram")
    public List<DiagramRunStep> run_diagram() {
        // TODO Auto-generated method stub
        return null;
    }

    @GetMapping("/update_diagram")
    public boolean update_diagram(String diagram) {
        // TODO Auto-generated method stub
        return false;
    }
}

