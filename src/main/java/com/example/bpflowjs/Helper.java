package com.example.bpflowjs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Helper implements brige {

    private List<DiagramRunStep> stepList =null;

    @GetMapping("/send_diagram")
    public boolean send_diagram(String diagram) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @return the next step in the diagram run or null is the diagram finish running
     */
    @GetMapping("/run_step")
    public DiagramRunStep run_step() {
        DiagramRunStep runStep=null;
        if (stepList==null)
            stepList =run_diagram();
        if (!stepList.isEmpty())
            runStep = stepList.remove(0);
        else
            stepList=null;
        return runStep;
    }

    @GetMapping("/run_diagram")
    public List<DiagramRunStep> run_diagram() {
        //TODO implement the run_diagram
        List<DiagramRunStep> runStepsList=new ArrayList<DiagramRunStep>();
        return runStepsList;
    }

    @GetMapping("/update_diagram")
    public boolean update_diagram(String diagram) {
        // TODO Auto-generated method stub
        return false;
    }
}

