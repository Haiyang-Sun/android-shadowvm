package ch.usi.dag.demo.ipc.analysis;

import ch.usi.dag.dislre.AREDispatch;


public class IPCAnalysisStub {

    public static short PERMISSION_USED = AREDispatch.registerMethod ("ch.usi.dag.demo.ipc.analysis.IPCAnalysis.permissionUsed");

    public static void permission_used (final String permission_str) {
        AREDispatch.analysisStart (PERMISSION_USED);
        AREDispatch.sendInt (AREDispatch.getThisThreadId ());
        AREDispatch.sendObjectPlusData (permission_str);
        AREDispatch.analysisEnd ();
    }
}
