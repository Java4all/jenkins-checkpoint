package io.jenkins.plugins.checkpoint;

import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;
import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import org.jenkinsci.Symbol;

import java.util.Set;

public class CheckpointStep extends Step {

    private final String label;

    @DataBoundConstructor
    public CheckpointStep(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public StepExecution start(StepContext context) throws Exception {
        return new CheckpointStepExecution(context, this);
    }

    @Extension
    @Symbol("checkpoint")
    public static class DescriptorImpl extends StepDescriptor {

        public DescriptorImpl() {
            super();
        }

        @Override
        public String getFunctionName() {
            return "checkpoint";
        }

        @Override
        public String getDisplayName() {
            return "Create a pipeline checkpoint";
        }

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return Set.of(Run.class, TaskListener.class);
        }
    }
}
