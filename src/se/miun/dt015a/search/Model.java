package se.miun.dt015a.search;

import java.util.function.BiFunction;

/**
 * @author Christoffer Fink
 */
@FunctionalInterface
public interface Model<State, Action> extends BiFunction<State, Action, State> {
}
