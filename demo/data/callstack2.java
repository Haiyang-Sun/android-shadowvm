package ch.usi.dag.demo.callstack.disl;

import java.util.List;
import ch.usi.dag.demo.ipc.analysis.lib.ThreadState;
import ch.usi.dag.disldroidreserver.shadow.Context;
import ch.usi.dag.disldroidreserver.shadow.ShadowString;


public class CallStackAnalysis {

    public void boundaryStart (
        final Context ctx, final int tid, final ShadowString boundaryName) {
        final ThreadState state = ThreadState.get (ctx, tid);
        state.pushBoundary (boundaryName.toString ());
    }

    public void boundaryEnd (
        final Context ctx, final int tid, final ShadowString boundaryName) {
        final ThreadState state = ThreadState.get (ctx, tid);
        state.popBoundary (boundaryName.toString ());
    }
}
