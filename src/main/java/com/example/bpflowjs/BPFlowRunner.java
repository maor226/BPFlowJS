package com.example.bpflowjs;

import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import il.ac.bgu.cs.bp.bpjs.model.ResourceBProgram;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class BPFlowRunner implements  brige{

    @Override
    public boolean send_diagram(String diagram){
        return false;
    }

    @Override
    public List<DiagramRunStep> run_diagram() {
        final BProgram bprog = new ResourceBProgram("BPFlow-Base.js");

        BProgramRunner rnr = new BProgramRunner(bprog);

        var lisener = new bpFlowLisiner();
        // Print program events to the console
        rnr.addListener( lisener );

        // go!
        var f =new FutureTask<List<DiagramRunStep>>(rnr,lisener.getRun());
        f.run();

        try {
            return f.get();
        } catch (InterruptedException | ExecutionException   e) {
            return null;
        }
    }
}


