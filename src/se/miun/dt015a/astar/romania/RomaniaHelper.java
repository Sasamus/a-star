package se.miun.dt015a.astar.romania;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import se.miun.dt015a.search.Actions;
import se.miun.dt015a.search.CostFunction;
import se.miun.dt015a.search.GoalTest;
import se.miun.dt015a.search.Model;
import se.miun.dt015a.search.Successor;
import se.miun.dt015a.search.SuccessorFunction;

/**
 * There be dragons here.
 * This is a big and messy class and should be ignored.
 *
 * @author Christoffer Fink
 */
public class RomaniaHelper {

  private static final RomaniaHelper instance = new RomaniaHelper();
  private static final Map<City,Map<City,Integer>> costMap;
  private static final Map<City,Set<DriveAction>> actionMap;
  private static final Actions<City,DriveAction> actionFunction;
  private static final Model<City,DriveAction> transitionModel;
  private static final CostFunction<DriveAction> costFunction;
  private static final Map<City,Set<Successor<City,DriveAction>>> successorMap;
  private static final SuccessorFunction<City,DriveAction> successorFunction;
  static {
    costMap = generateCostMap();
    costFunction = generateCostFunction();
    actionMap = generateActionMap();
    actionFunction = generateActionFunction();
    transitionModel = generateTransitionFunction();
    successorMap = generateSuccessorMap();
    successorFunction = generateSuccessorFunction();
  }
  
  private RomaniaHelper() {
  }
  public static RomaniaHelper getInstance() {
    return instance;
  }

  public Model<City,DriveAction> getTransitionModel() {
    return transitionModel;
  }

  public SuccessorFunction<City,DriveAction> getSuccessorFunction() {
    return successorFunction;
  }

  public CostFunction<DriveAction> getCostFunction() {
    return costFunction;
  }

  public Actions<City,DriveAction> getActionFunction() {
    return actionFunction;
  }

  public Set<DriveAction> getActions(City state) {
    return actionMap.get(state);
  }

  public Set<City> getNeighbors(City state) {
    return costMap.get(state).keySet();
  }

  public GoalTest<City> getGoalTest(City goal) {
    if (goal == null) {
      throw new IllegalArgumentException("Refusing to create goal test for null goal.");
    }
    return goal::equals;
  }

  private static Set<DriveAction> generateActions(City state) {
    return Collections.unmodifiableSet(costMap.get(state)
        .keySet()
        .stream()
        .map(c -> new DriveAction(state, c))
        .collect(Collectors.toSet()));
  }

  // Should throw exceptions.
  private static Actions<City,DriveAction> generateActionFunction() {
    return actionMap::get;
  }

  private static Map<City,Set<DriveAction>> generateActionMap() {
    Map<City,Set<DriveAction>> actionMap = new HashMap<>();
    costMap.keySet()
      .forEach(k -> actionMap.put(k, generateActions(k)));
    return Collections.unmodifiableMap(new EnumMap<>(actionMap));
  }

  private static CostFunction<DriveAction> generateCostFunction() {
    return a -> {
      if (!costMap.get(a.from).containsKey(a.to)) {
        throw new IllegalArgumentException("illegal action");
      }
      return costMap.get(a.from).get(a.to);
    };
  }

  // Should throw exceptions.
  private static SuccessorFunction<City,DriveAction> generateSuccessorFunction() {
    return successorMap::get;
  }

  private static Model<City,DriveAction> generateTransitionFunction() {
    return (c,a) -> {
      if (c != a.from) {
        throw new IllegalArgumentException("action not applicable in state");
      }
      if (!costMap.containsKey(c)) {
        throw new IllegalArgumentException("no action applicable in that state");
      }
      if (!costMap.get(c).containsKey(a.to)) {
        throw new IllegalArgumentException("illegal action");
      }
      return a.to;
    };
  }

  private static Map<City,Set<Successor<City,DriveAction>>> generateSuccessorMap() {
    Map<City,Set<Successor<City,DriveAction>>> map = new HashMap<>();
    for (City start : costMap.keySet()) {
      Set<Successor<City,DriveAction>> successors = new HashSet<>();
      for (DriveAction action : actionMap.get(start)) {
        int cost = costMap.get(start).get(action.to);
        successors.add(new Successor<>(action.to, action, cost));
      }
      map.put(start, Collections.unmodifiableSet(successors));
    }
    return Collections.unmodifiableMap(new EnumMap<>(map));
  }

  private static Map<City,Map<City,Integer>> generateCostMap() {
    Map<City,Map<City,Integer>> costMap = new HashMap<>();
    costMap.put(City.ARAD, aradCosts());
    costMap.put(City.BUCHAREST, bucharestCosts());
    costMap.put(City.CRAIOVA, craiovaCosts());
    costMap.put(City.DOBRETA, dobretaCosts());
    costMap.put(City.EFORIE, eforiCosts());
    costMap.put(City.FAGARAS, fagarasCosts());
    costMap.put(City.GIURGIU, giurgiuCosts());
    costMap.put(City.HIRSOVA, hirsovaCosts());
    costMap.put(City.IASI, isaiCosts());
    costMap.put(City.LUGOJ, lugojCosts());
    costMap.put(City.MEHADIA, mehadiaCosts());
    costMap.put(City.NEAMT, neamtCosts());
    costMap.put(City.ORADEA, oradeaCosts());
    costMap.put(City.PITESTI, pitestiCosts());
    costMap.put(City.RIMNICU_VILCEA, rimnicuVilceaCosts());
    costMap.put(City.SIBIU, sibiuCosts());
    costMap.put(City.TIMISOARA, timisoaraCosts());
    costMap.put(City.URZICENI, urziceniCosts());
    costMap.put(City.VASLUI, vasluiCosts());
    costMap.put(City.ZERIND, zerindCosts());
    assert costConsistency(costMap);
    return Collections.unmodifiableMap(new EnumMap<>(costMap));
  }
  private static Map<City,Integer> aradCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.SIBIU, 140);
    costMap.put(City.TIMISOARA, 118);
    costMap.put(City.ZERIND, 75);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> bucharestCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.FAGARAS, 211);
    costMap.put(City.GIURGIU, 90);
    costMap.put(City.PITESTI, 101);
    costMap.put(City.URZICENI, 85);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> craiovaCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.DOBRETA, 120);
    costMap.put(City.PITESTI, 138);
    costMap.put(City.RIMNICU_VILCEA, 146);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> dobretaCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.CRAIOVA, 120);
    costMap.put(City.MEHADIA, 75);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> eforiCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.HIRSOVA, 86);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> fagarasCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.BUCHAREST, 211);
    costMap.put(City.SIBIU, 99);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> giurgiuCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.BUCHAREST, 90);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> hirsovaCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.EFORIE, 86);
    costMap.put(City.URZICENI, 98);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> isaiCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.NEAMT, 87);
    costMap.put(City.VASLUI, 92);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> lugojCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.MEHADIA, 70);
    costMap.put(City.TIMISOARA, 111);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> mehadiaCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.DOBRETA, 75);
    costMap.put(City.LUGOJ, 70);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> neamtCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.IASI, 87);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> oradeaCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.SIBIU, 151);
    costMap.put(City.ZERIND, 71);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> pitestiCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.BUCHAREST, 101);
    costMap.put(City.CRAIOVA, 138);
    costMap.put(City.RIMNICU_VILCEA, 97);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> rimnicuVilceaCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.CRAIOVA, 146);
    costMap.put(City.PITESTI, 97);
    costMap.put(City.SIBIU, 80);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> sibiuCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.ARAD, 140);
    costMap.put(City.FAGARAS, 99);
    costMap.put(City.ORADEA, 151);
    costMap.put(City.RIMNICU_VILCEA, 80);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> timisoaraCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.ARAD, 118);
    costMap.put(City.LUGOJ, 111);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> urziceniCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.BUCHAREST, 85);
    costMap.put(City.HIRSOVA, 98);
    costMap.put(City.VASLUI, 142);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> vasluiCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.IASI, 92);
    costMap.put(City.URZICENI, 142);
    return new EnumMap<>(costMap);
  }
  private static Map<City,Integer> zerindCosts() {
    Map<City,Integer> costMap = new HashMap<>();
    costMap.put(City.ARAD, 75);
    costMap.put(City.ORADEA, 71);
    return new EnumMap<>(costMap);
  }

  private static boolean costConsistency(Map<City,Map<City,Integer>> costMap) {
    for (City a : costMap.keySet()) {
      Map<City, Integer> arcs = costMap.get(a);
      for (City b : arcs.keySet()) {
        assert costMap.containsKey(b) && costMap.get(b).containsKey(a) :
          "unidirectional arc: " + a + " -> " + b;
        int aToB = costMap.get(a).get(b);
        int bToA = costMap.get(b).get(a);
        assert aToB == bToA :
          String.format("cost mismatch for %s(%d) -> %s(%d)", a, aToB, b, bToA);
      }
    }
    return true;
  }
}
