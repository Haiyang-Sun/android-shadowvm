public class CallStackAnalysis extends RemoteAnalysis {
    public void boundaryStart (
        final Context ctx, final int tid, final ShadowString boundaryName) {
        final SVMCallStack state = SVMCallStack.get (ctx, tid);
        state.pushBoundary (boundaryName.toString ());
    }

    public void boundaryEnd (
        final Context ctx, final int tid, final ShadowString boundaryName) {
        final SVMCallStack state = SVMCallStack.get (ctx, tid);
        state.popBoundary (boundaryName.toString ());
    }
}
