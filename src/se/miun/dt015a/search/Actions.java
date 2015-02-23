package se.miun.dt015a.search;

import java.util.Set;
import java.util.function.Function;

/**
 * @author Christoffer Fink
 */
@FunctionalInterface
public interface Actions<State,Action> extends Function<State, Set<Action>> {
}
