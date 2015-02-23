package se.miun.dt015a.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * A silly example search algorithm used as a placeholder.
 * Note that it even ignores the heuristic.
 * @author Christoffer Fink
 */
public class RandomSearch<S,A> implements InformedSearch<S,A> {

  private final Random rng = new Random();

  @Override
  public Solution<S,A> search(
      SearchProblem<S,A> problem, Heuristic<S> heuristic) {

    S state = problem.getStart();
    List<Successor<S,A>> path = new LinkedList<>();
    path.add(new Successor<>(state, null, 0));

    while (!problem.isGoal(state)) {
      Successor<S,A> successor = pickRandomSuccessor(problem, state);
      path.add(successor);
      state = successor.state;
    }
    return new Solution<S,A>(path);
  }

  private Successor<S,A> pickRandomSuccessor(SearchProblem<S,A> p, S s) {
    List<Successor<S,A>> successors = new ArrayList<>(p.getSuccessors(s));
    return successors.get(rng.nextInt(successors.size()));
  }
}
