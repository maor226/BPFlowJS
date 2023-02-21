package com.example.bpflowjs;

import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;
import il.ac.bgu.cs.bp.bpjs.execution.listeners.BProgramRunnerListener;
import il.ac.bgu.cs.bp.bpjs.model.*;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

class main {


    public static void main(String[] args) throws InterruptedException {




        // This will load the program file  <Project>/src/main/resources/HelloBPjsWorld.js
        final BProgram bProg = new  ResourceBProgram("js/BPFlow-Base.js", "js/HelloWorld.js");

        BProgramRunner rnr = new BProgramRunner(bProg);

        // Print program events to the console
        rnr.addListener(new bpFlowLisiner());

        // go!
        rnr.run();
    }
}
class bpFlowLisiner implements BProgramRunnerListener{

        @Override
        public void starting(BProgram bProgram) {
            System.out.println("------------starting-----------");

        }

        @Override
        public void started(BProgram bProgram) {
            System.out.println("-------------start------------");
        }

        @Override
        public void superstepDone(BProgram bProgram) {
            System.out.println("------------------------------");
        }

        @Override
        public void ended(BProgram bProgram) {
            System.out.println("------------ended------------");

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
            System.out.println("------------halted------------");
        }
}