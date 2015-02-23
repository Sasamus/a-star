package se.miun.dt015a.search;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The solution to a search problem as a list of successors.
 * Note that the start state (with a null action) should be included in a
 * solution.
 * @author Christoffer Fink
 */
public class Solution<State,Action> {

  private final List<Successor<State,Action>> successors;
  private final int cost;

  public Solution(List<Successor<State,Action>> successors) {
    this.successors = Collections.unmodifiableList(successors);
    this.cost = successors.stream().map(s -> s.cost).reduce(0, (x,y) -> x+y);
  }

  public List<Successor<State,Action>> getPath() {
    return successors;
  }
  public int getCost() {
    return cost;
  }
  public List<State> getStates() {
    return successors.stream().map(s -> s.state).collect(Collectors.toList());
  }
}
