/* applied on for framework.jar */
public class PermissionDiSLClass {

    /* instrument the framework library for every check permission functions
     * send the permission used to the analysis server
     *
     * This takes effects on framework.jar
     */
    @Before (
			marker = BodyMarker.class,
			//guard = Guard.PermissionGuard.class
			scope = "ActivityManager.checkComponentPermission"
			)
	public static void detectPermission (MethodStaticContext msc, ArgumentProcessorContext pc) {
        Object [] args = pc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
		if(args[0] != null) {
		    String permisssionUsed = args[0].toString ();
    		if(permisssionUsed!=null) {
    		    IPCAnalysisStub.permission_used (permisssionUsed);
    		}
		}
	}
}
