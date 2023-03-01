package com.example.bpflowjs;

import jdk.jshell.spi.ExecutionControl;

import java.util.List;
import java.util.Map;

public class Diagram {

    private String diagram;

    public Diagram(String diagram) {
        this.diagram = diagram;
    }

    public String getDiagram() {
        //todo implement this
        throw new RuntimeException("Not implemented yet");
    }

    public void setDiagram(String diagram) {
        this.diagram = diagram;
    }

    public List<DiagramNodes> getNodes(){
        //todo implement this
        throw new RuntimeException("Not implemented yet");
    }

    public Map<String,String> getTokens(DiagramNodes node){
        //todo implement this
        throw new RuntimeException("Not implemented yet");
    }
}

