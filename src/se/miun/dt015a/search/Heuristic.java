package se.miun.dt015a.search;

import java.util.function.Function;

/**
 * @author Christoffer Fink
 */
@FunctionalInterface
public interface Heuristic<State> extends Function<State, Integer> {
}
