package com.example.bpflowjs;

import java.util.List;
import java.util.Map;

public class DiagramRunStepImpl implements DiagramRunStep {

    private Diagram diagram;

    public DiagramRunStepImpl(Diagram diagram) {
        this.diagram = diagram;
    }

    @Override
    public String get_diagram() {
        return diagram.toString();
    }

    @Override
    public Map<String, List<String>> get_tokens(String node) {
        return null;
    }
}
