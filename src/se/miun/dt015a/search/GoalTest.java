package se.miun.dt015a.search;

import java.util.function.Predicate;

/**
 * @author Christoffer Fink
 */
@FunctionalInterface
public interface GoalTest<State> extends Predicate<State> {
}
