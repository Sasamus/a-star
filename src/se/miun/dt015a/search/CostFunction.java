package se.miun.dt015a.search;

import java.util.function.Function;

/**
 * @author Christoffer Fink
 */
@FunctionalInterface
public interface CostFunction<Action> extends Function<Action,Integer> {
}
