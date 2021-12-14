package simple.state.machine;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum RunnerState {
    PREPARING() {
        @Override
        protected Set<RunnerState> nextStates() {
            return EnumSet.of(PREPARING, RUNNING, LEFT_THE_RACE, STOPPED);
        }
    },
    RUNNING() {
        @Override
        protected Set<RunnerState> nextStates() {
            return EnumSet.of(RUNNING, FINISHED, LEFT_THE_RACE, STOPPED);
        }
    },
    STOPPED() {
        @Override
        protected Set<RunnerState> nextStates() {
            return EnumSet.of(STOPPED, RUNNING, LEFT_THE_RACE);
        }
    },
    FINISHED() {
        @Override
        protected Set<RunnerState> nextStates() {
            return EnumSet.of(FINISHED);
        }
    },
    LEFT_THE_RACE() {
        @Override
        protected Set<RunnerState> nextStates() {
            return EnumSet.of(LEFT_THE_RACE);
        }
    };

    boolean canTransitionTo(RunnerState newState) {
        return nextStates().contains(newState);
    }

    protected Set<RunnerState> nextStates() {
        return Collections.emptySet();
    }
}
