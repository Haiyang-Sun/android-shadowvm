package ch.usi.dag.demo.ipc.disl;

import ch.usi.dag.demo.ipc.analysis.IPCAnalysisStub;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.processorcontext.ArgumentProcessorContext;
import ch.usi.dag.disl.processorcontext.ArgumentProcessorMode;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;
/* for framework.jar */
public class PermissionDiSLClass {

    /* instrument the framework library for every check permission functions
     * send the permission used to the analysis server
     *
     * This takes effects on framework.jar
     */
    @Before (
			marker = BodyMarker.class,
			guard = Guard.PermissionGuard.class
			)
		public static void detectPermission (
				final MethodStaticContext msc, final ArgumentProcessorContext pc) {
            final Object [] args = pc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
			if(args[0] != null) {
			    final String permisssionUsed = args[0].toString ();
    			if(permisssionUsed!=null) {
    			    IPCAnalysisStub.permission_used (permisssionUsed);
    			}
			}
		}
}
