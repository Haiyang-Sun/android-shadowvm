/*
 * for generating runtime stack
 * Take effects on app code
 */
public class MethodEntryDiSLClass {
    /* every time entering a method */
    @Before (
        marker = BodyMarker.class,
        order = 1000)
    public static void before_enter (final CallContext msc) {
        CallStackAnalysisStub.boundary_start (msc.thisMethodFullName ());
    }
    /* every time leaving a method */
    @After (
        marker = BodyMarker.class,
        order = 1000)
    public static void after_enter (final CallContext msc) {
        CallStackAnalysisStub.boundary_end (msc.thisMethodFullName());
    }
}
