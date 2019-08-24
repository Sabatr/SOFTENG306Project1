package algorithm;

/**
 * A class which represents the statistics needed for the branches
 */
public class AlgorithmBranchDetails {
    private int branchesSeen;
    private int branchesPruned;
    private int duplicateBranches;

    public void setBranchesSeen(int value) {
        branchesSeen = value;
    }

    public void setBranchesPruned(int value) {
        branchesPruned = value;
    }

    public void setDuplicateBranches(int value) {
        duplicateBranches = value;
    }

    public int getBranchesSeen() {
        return branchesSeen;
    }

    public int getBranchesPruned() {
        return branchesPruned;
    }

    public int getDuplicateBranches() {
        return duplicateBranches;
    }
}
