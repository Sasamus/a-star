package se.miun.dt015a.search;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Maps states to successors.
 * @author Christoffer Fink
 */
public interface SuccessorFunction<State,Action>
    extends Function<State, Set<Successor<State,Action>>> {

  /** Generates a SuccessorFunction from Cost, Model, and Actions functions. */
  static <S,A> SuccessorFunction <S,A> getFrom(CostFunction<A> f,
      Model<S,A> model, Actions<S,A> actions) {
    return state -> {
      Set<Successor<S,A>> successors = new HashSet<>();
      for (A action : actions.apply(state)) {
        S result = model.apply(state, action);
        int cost = f.apply(action);
        successors.add(new Successor<>(result, action, cost));
      }
      return successors;
    };
  }
}
