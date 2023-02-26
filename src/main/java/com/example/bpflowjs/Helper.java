package com.example.bpflowjs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Helper implements brige {

    private static Integer stepIndex;
    @GetMapping("/send_diagram")
    public boolean send_diagram(String diagram) {
        // TODO Auto-generated method stub
        return false;
    }

    @GetMapping("/run_step")
    public DiagramRunStep run_step() {
        List<DiagramRunStep> runStepsList =run_diagram();
        DiagramRunStep runStep=null;
        if (stepIndex==null)
            stepIndex=0;
        if (stepIndex< runStepsList.size())
            runStep= runStepsList.get(stepIndex);
            stepIndex++;
        return runStep;
    }

    @GetMapping("/run_diagram")
    public List<DiagramRunStep> run_diagram() {
        // TODO Auto-generated method stub
        List<DiagramRunStep> runStepsList=new ArrayList<DiagramRunStep>();
        return runStepsList;
    }

    @GetMapping("/update_diagram")
    public boolean update_diagram(String diagram) {
        // TODO Auto-generated method stub
        return false;
    }
}

