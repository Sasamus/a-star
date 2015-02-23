package se.miun.dt015a.search;

import java.util.Set;

/**
 * A generic search problem.
 * @author Christoffer Fink
 */
public class SearchProblem<State,Action> {

  private final State start;
  private final GoalTest<State> goal;
  private final SuccessorFunction<State,Action> successorFunction;

  public SearchProblem(State startState, GoalTest<State> goalTest,
      SuccessorFunction<State,Action> successorFunction) {
    this.start = startState;
    this.goal = goalTest;
    this.successorFunction = successorFunction;
  }

  /** Get the starting state, or initial position, for this search problem. */
  public State getStart() {
    return start;
  }
  /** Get the set of successors for the given state. */
  public Set<Successor<State,Action>> getSuccessors(State state) {
    return successorFunction.apply(state);
  }
  /** Check whether the given state satisfies the goal test. */
  public boolean isGoal(State state) {
    return goal.test(state);
  }
}
