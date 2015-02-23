package se.miun.dt015a.search;

/**
 * A successor to a state.
 * Contains a possible action, the resulting state, and the action cost.
 * @author Christoffer Fink
 */
public class Successor<State, Action> {
  public final State state;
  public final Action action;
  public final int cost;

  public Successor(State state, Action action, int cost) {
    this.state = state;
    this.action = action;
    this.cost = cost;
  }
}
