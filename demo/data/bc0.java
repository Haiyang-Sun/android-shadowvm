public class DiSLClass {
    @SyntheticLocal
    public static boolean encounterBranch = false;
    @Before (marker = BranchMarker.class)
    public static void beforeBranchInstruction () {
        encounterBranch = true;
    }

    @AfterReturning (marker = IfThenBranchMarker.class)
    public static void thenBranch (CodeCoverageContext c) {
        if (encounterBranch) {
            CodeCoverageAnalysisStub.branchTaken(
					c.thisClassName (), c.thisMethodSignature  (), c.getIndex ());
            encounterBranch = false;
        }
    }

    @AfterReturning (marker = IfElseBranchMarker.class)
    public static void elseBranch (CodeCoverageContext c) {
        if (encounterBranch) {
            CodeCoverageAnalysisStub.branchTaken(
					c.thisClassName (), c.thisMethodSignature  (), c.getIndex ());
            encounterBranch = false;
        }
    }

    @Before (marker = SwitchMarker.class)
    public static void beforeSwitchInstruction (CodeCoverageContext c) {
        encounterBranch = true;
    }

    @AfterReturning (marker = SwitchCaseMarker.class)
    public static void afterBranchLabel (CodeCoverageContext c) {
        if (encounterBranch) {
            CodeCoverageAnalysisStub.branchTaken(
					c.thisClassName (), c.thisMethodSignature  (), c.getIndex ());
            encounterBranch = false;
        }
    }
}
