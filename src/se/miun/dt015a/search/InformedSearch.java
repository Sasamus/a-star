package se.miun.dt015a.search;

/**
 * @author Christoffer Fink
 */
public interface InformedSearch<State,Action> {
  public Solution<State,Action> search(SearchProblem<State,Action> problem,
      Heuristic<State> heuristic);
}
