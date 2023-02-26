package com.example.bpflowjs;

import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;
import il.ac.bgu.cs.bp.bpjs.execution.listeners.BProgramRunnerListener;
import il.ac.bgu.cs.bp.bpjs.model.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

class main {


    public static void main(String[] args) throws InterruptedException {




        // This will load the program file  <Project>/src/main/resources/HelloBPjsWorld.js
        final BProgram bProg = new  ResourceBProgram("js/BPFlow-Base.js");

        BProgramRunner rnr = new BProgramRunner(bProg);

        // Print program events to the console
        rnr.addListener(new bpFlowLisiner());

        // go!
        rnr.run();
    }
}


class bpFlowLisiner implements BProgramRunnerListener{

    static final String diagram = "diagram";

        List<DiagramRunStep> run =new LinkedList<>();
        public bpFlowLisiner(){
        }

        @Override
        public void starting(BProgram bProgram) {

        }

        @Override
        public void started(BProgram bProgram) {

        }

        @Override
        public void superstepDone(BProgram bProgram) {
            bProgram.getFromGlobalScope("diagram", String.class).ifPresent(s -> run.add(new DiagramRunStepImpl(s)));

        }

        @Override
        public void ended(BProgram bProgram) {
        }

        @Override
        public void assertionFailed(BProgram bProgram, SafetyViolationTag safetyViolationTag) {

        }

        @Override
        public void bthreadAdded(BProgram bProgram, BThreadSyncSnapshot bThreadSyncSnapshot) {

        }

        @Override
        public void bthreadRemoved(BProgram bProgram, BThreadSyncSnapshot bThreadSyncSnapshot) {

        }

        @Override
        public void bthreadDone(BProgram bProgram, BThreadSyncSnapshot bThreadSyncSnapshot) {

        }

        @Override
        public void eventSelected(BProgram bProgram, BEvent bEvent) {
            System.out.println(bEvent.toString());
        }

        @Override
        public void halted(BProgram bProgram) {
            Thread.currentThread().interrupt();
        }

    public List<DiagramRunStep> getRun() {
        return run;
    }
}