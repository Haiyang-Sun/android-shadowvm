public class CodeCoverageAnalysis extends RemoteAnalysis  {
    static class ClassProfile extends ConcurrentHashMap <String, int []> {
        public ClassProfile (ClassNode classNode) {
            for (MethodNode methodNode : classNode.methods) {
                String key = methodNode.name
                    + Constants.STATIC_CONTEXT_METHOD_DELIM + methodNode.desc;
                int counter = 0;
                for (AbstractInsnNode instr : methodNode.instructions.toArray ()) {
                    counter += CodeCoverageUtil.getBranchCount (instr);
                }
                put (key, new int [counter]);
            }
        }
    }

    static class ProcessProfile extends ConcurrentHashMap <String, ClassProfile> {}

    public void branchTaken (ShadowString classSignature,
        ShadowString methodSignature, int index,
        Context context) {
        ProcessProfile processProfile = context.getState ("bc", ProcessProfile.class);
        if (processProfile == null) {
            ProcessProfile temp = new ProcessProfile ();
            processProfile = (ProcessProfile) context.setStateIfAbsent ("bc", temp);
            if(processProfile == null) {
                processProfile = temp;
            }
        }
        String outerKey = classSignature.toString ();
        String innerKey = methodSignature.toString ();
        ClassProfile classProfile = processProfile.get (outerKey);

        if (classProfile == null) {
            ClassProfile temp = new ClassProfile (
                context.getClassNodeFor (outerKey));
            classProfile = processProfile.putIfAbsent (outerKey, temp);
        }
        int times = ++classProfile.get (innerKey) [index];
        WebLogger.branchTaken (context.getProcessID (), context.getPname (),
            classSignature.toString (), methodSignature.toString (), index,
            classProfile.get (innerKey).length, times);
    }

    public static boolean printResult(Context context){
        ProcessProfile processProfile = context.getState ("bc", ProcessProfile.class);
        if(processProfile == null) {
            return false;
        }
        for (String classSignature : processProfile.keySet ()) {
            ClassProfile classProfile = processProfile.get (classSignature);
            for (String methodSignature : classProfile.keySet ()) {
                int [] coverage = classProfile.get (methodSignature);
                int total = coverage.length;
                int covered = 0;
                for (int count : coverage) {
                    if (count > 0) {
                        covered++;
                    }
                }
                if(covered!=0) {
                    WebLogger.reportBranchCoverage (context.getProcessID (), context.getPname (),
                        classSignature, methodSignature, covered, total);
                }
            }
        }
        return true;
    }

	//dump the coverage report every 5 secs
    static class OutputThread extends Thread {
        @Override
        public void run(){
            while(true) {
                try {
                    for(int i =0; i < ShadowAddressSpace.getContexts ().size ();i++)
                        CodeCoverageAnalysis.printResult (ShadowAddressSpace.getContexts ().get (i));
                    Thread.sleep(5000);
                }catch(Exception e)
                {
                    break;
                }
            }
        }
      }

    static {
        Thread thd = new OutputThread();
        thd.start ();
    }
}
