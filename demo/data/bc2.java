package ch.usi.dag.demo.branchcoverage.analysis;

import ch.usi.dag.dislre.AREDispatch;


public class CodeCoverageAnalysisStub {

    public static short CB = AREDispatch.registerMethod ("ch.usi.dag.demo.branchcoverage.analysis.CodeCoverageAnalysis.branchTaken");

    public static void branchTaken (
        final String classSignature, final String methodSignature, final int idx) {
        AREDispatch.analysisStart (CB);
        AREDispatch.sendObjectPlusData (classSignature);
        AREDispatch.sendObjectPlusData (methodSignature);
        AREDispatch.sendInt (idx);
        AREDispatch.analysisEnd ();
    }

}
