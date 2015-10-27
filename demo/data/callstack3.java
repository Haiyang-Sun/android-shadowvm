package ch.usi.dag.demo.callstack.analysis;

import ch.usi.dag.dislre.AREDispatch;

public class CallStackAnalysisStub {

    public static short BOUNDARY_START = AREDispatch.registerMethod ("ch.usi.dag.demo.callstack.analysis.CallStackAnalysis.boundaryStart");

    public static void boundary_start (final String boundaryName) {
        //AREDispatch.NativeLog ("boundary start");
        AREDispatch.analysisStart (BOUNDARY_START);
        AREDispatch.sendInt (AREDispatch.getThisThreadId ());
        AREDispatch.sendObjectPlusData (boundaryName);
        AREDispatch.analysisEnd ();
    }
    public static short BOUNDARY_END = AREDispatch.registerMethod ("ch.usi.dag.demo.callstack.analysis.CallStackAnalysis.boundaryEnd");

    public static void boundary_end (final String boundaryName) {
        //AREDispatch.NativeLog ("boundary end");
        AREDispatch.analysisStart (BOUNDARY_END);
        AREDispatch.sendInt (AREDispatch.getThisThreadId ());
        AREDispatch.sendObjectPlusData (boundaryName);
        AREDispatch.analysisEnd ();
    }
}