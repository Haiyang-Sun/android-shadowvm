package ch.usi.dag.demo.branchcoverage.disl;

import ch.usi.dag.demo.branchcoverage.analysis.CodeCoverageAnalysisProxy;
import ch.usi.dag.disl.annotation.AfterReturning;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.annotation.SyntheticLocal;

public class DiSLClass {

    @SyntheticLocal
    public static boolean encounterBranch = false;

    @Before (marker = BranchMarker.class)
    public static void beforeBranchInstruction () {
        encounterBranch = true;
    }

    @AfterReturning (marker = IfThenBranchMarker.class)
    public static void thenBranch (final CodeCoverageContext c) {
        if (encounterBranch) {
            CodeCoverageAnalysisProxy.branchTaken(c.thisClassName (), c.thisMethodSignature  (), c.getIndex ());
            encounterBranch = false;
        }
    }

    @AfterReturning (marker = IfElseBranchMarker.class)
    public static void elseBranch (final CodeCoverageContext c) {
        if (encounterBranch) {
            CodeCoverageAnalysisProxy.branchTaken(c.thisClassName (), c.thisMethodSignature  (), c.getIndex ());
            encounterBranch = false;
        }
    }

    @Before (marker = SwitchMarker.class)
    public static void beforeSwitchInstruction (final CodeCoverageContext c) {
        encounterBranch = true;
    }

    @AfterReturning (marker = SwitchCaseMarker.class)
    public static void afterBranchLabel (final CodeCoverageContext c) {
        if (encounterBranch) {
            CodeCoverageAnalysisProxy.branchTaken(c.thisClassName (), c.thisMethodSignature  (), c.getIndex ());
            encounterBranch = false;
        }
    }
}
