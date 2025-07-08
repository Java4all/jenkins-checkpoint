package io.jenkins.plugins.checkpoint;

import org.jenkinsci.plugins.workflow.cps.CpsFlowExecution;
import org.jenkinsci.plugins.workflow.flow.FlowExecution;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.SynchronousStepExecution;

import hudson.model.TaskListener;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Execution logic for the checkpoint step.
 */
public class CheckpointStepExecution extends SynchronousStepExecution<Void> {

    private final transient CheckpointStep step;

    protected CheckpointStepExecution(StepContext context, CheckpointStep step) {
        super(context);
        this.step = step;
    }

    @Override
    protected Void run() throws Exception {
        FlowExecution fe = getContext().get(FlowExecution.class);
        if (!(fe instanceof CpsFlowExecution exec)) {
            throw new IllegalStateException("Not a CpsFlowExecution: " + fe);
        }
        File buildDir = exec.getOwner().getRootDir();
        String safeName = step.getLabel().replaceAll("[^a-zA-Z0-9._-]", "_");
        File checkpointFile = new File(buildDir, "checkpoints/" + safeName + ".txt");
        checkpointFile.getParentFile().mkdirs();
        try (FileOutputStream out = new FileOutputStream(checkpointFile)) {
            out.write(("Checkpoint reached: " + step.getLabel()).getBytes(StandardCharsets.UTF_8));
        }
        getContext().get(TaskListener.class).getLogger()
            .println("Checkpoint saved to " + checkpointFile.getAbsolutePath());
        return null;
    }
}
