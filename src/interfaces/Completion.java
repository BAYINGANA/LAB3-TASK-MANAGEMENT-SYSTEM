package interfaces;

public interface Completion {
    public void markCompleted();
    boolean isCompleted();
    public void markInProgress();
    boolean isInProgress();
    public void markNotStarted();
    boolean isNotStarted();
    public void getCompletionStatus();
    public void getTaskByStatus();
}

