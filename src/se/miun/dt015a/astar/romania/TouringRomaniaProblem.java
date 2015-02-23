package se.miun.dt015a.astar.romania;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import se.miun.dt015a.search.GoalTest;
import se.miun.dt015a.search.Heuristic;
import se.miun.dt015a.search.SearchProblem;
import se.miun.dt015a.search.SuccessorFunction;

/**
 * The "touring in Romania" search problem.
 * In this problem, states are cities, and the actions are driving from one
 * city to an adjacent one.
 *
 * This class contains all the information about this particular search problem.
 * That is, creating an instance only requires a starting City and a goal City.
 * All the cost and successor functions are automatically setup.
 *
 * The class also contains three different heuristics that can be passed to
 * an informed search algorithm.
 *
 * @author Christoffer Fink
 */
public class TouringRomaniaProblem extends SearchProblem<City,DriveAction> {

  public static final Heuristic<City> MANHATTAN_DISTANCE_HEURISTIC;
  public static final Heuristic<City> MIN_NODES_TRAVERSED_HEURISTIC;
  public static final Heuristic<City> STRAIGHT_LINE_DISTANCE_HEURISTIC;
  static {
    MANHATTAN_DISTANCE_HEURISTIC = getManhattanDistanceHeuristic();
    MIN_NODES_TRAVERSED_HEURISTIC = getMinNodesTraversedHeuristic();
    STRAIGHT_LINE_DISTANCE_HEURISTIC = getStraightLineDistanceHeuristic();
  }
  private static final RomaniaHelper helper = RomaniaHelper.getInstance();

  private TouringRomaniaProblem(City start, GoalTest<City> goalTest,
      SuccessorFunction<City,DriveAction> successorFunction) {
    super(start, goalTest, successorFunction);
  }

  public static TouringRomaniaProblem getInstance(City start, City goal) {
    GoalTest<City> goalTest = helper.getGoalTest(goal);
    SuccessorFunction<City,DriveAction> succF = helper.getSuccessorFunction();
    return new TouringRomaniaProblem(start, goalTest, succF);
  }

  private static Heuristic<City> getManhattanDistanceHeuristic() {
    Map<City,Integer> heuristic = new HashMap<>();
    heuristic.put(City.ARAD, 720+250);
    heuristic.put(City.BUCHAREST, 0);
    heuristic.put(City.CRAIOVA, 305+170);
    heuristic.put(City.DOBRETA, 570+150);
    heuristic.put(City.EFORIE, 360+90);
    heuristic.put(City.FAGARAS, 190+200);
    heuristic.put(City.GIURGIU, 30+150);
    heuristic.put(City.HIRSOVA, 280+10);
    heuristic.put(City.IASI, 160+290);
    heuristic.put(City.LUGOJ, 560+30);
    heuristic.put(City.MEHADIA, 560+60);
    heuristic.put(City.NEAMT, 0+340);
    heuristic.put(City.ORADEA, 600+410);
    heuristic.put(City.PITESTI, 175+60);
    heuristic.put(City.RIMNICU_VILCEA, 335+120);
    heuristic.put(City.SIBIU, 400+210);
    heuristic.put(City.TIMISOARA, 720+90);
    heuristic.put(City.URZICENI, 130+10);
    heuristic.put(City.VASLUI, 225+170);
    heuristic.put(City.ZERIND, 690+350);
    return new EnumMap<>(heuristic)::get;
  }

  private static Heuristic<City> getMinNodesTraversedHeuristic() {
    Map<City,Integer> heuristic = new HashMap<>();
    heuristic.put(City.ARAD, 3);
    heuristic.put(City.BUCHAREST, 0);
    heuristic.put(City.CRAIOVA, 2);
    heuristic.put(City.DOBRETA, 3);
    heuristic.put(City.EFORIE, 3);
    heuristic.put(City.FAGARAS, 1);
    heuristic.put(City.GIURGIU, 1);
    heuristic.put(City.HIRSOVA, 2);
    heuristic.put(City.IASI, 3);
    heuristic.put(City.LUGOJ, 5);
    heuristic.put(City.MEHADIA, 4);
    heuristic.put(City.NEAMT, 4);
    heuristic.put(City.ORADEA, 3);
    heuristic.put(City.PITESTI, 1);
    heuristic.put(City.RIMNICU_VILCEA, 2);
    heuristic.put(City.SIBIU, 2);
    heuristic.put(City.TIMISOARA, 4);
    heuristic.put(City.URZICENI, 1);
    heuristic.put(City.VASLUI, 2);
    heuristic.put(City.ZERIND, 4);
    return new EnumMap<>(heuristic)::get;
  }

  private static Heuristic<City> getStraightLineDistanceHeuristic() {
    Map<City,Integer> heuristic = new HashMap<>();
    heuristic.put(City.ARAD, 366);
    heuristic.put(City.BUCHAREST, 0);
    heuristic.put(City.CRAIOVA, 160);
    heuristic.put(City.DOBRETA, 242);
    heuristic.put(City.EFORIE, 161);
    heuristic.put(City.FAGARAS, 176);
    heuristic.put(City.GIURGIU, 77);
    heuristic.put(City.HIRSOVA, 151);
    heuristic.put(City.IASI, 226);
    heuristic.put(City.LUGOJ, 244);
    heuristic.put(City.MEHADIA, 241);
    heuristic.put(City.NEAMT, 234);
    heuristic.put(City.ORADEA, 380);
    heuristic.put(City.PITESTI, 100);
    heuristic.put(City.RIMNICU_VILCEA, 193);
    heuristic.put(City.SIBIU, 253);
    heuristic.put(City.TIMISOARA, 329);
    heuristic.put(City.URZICENI, 80);
    heuristic.put(City.VASLUI, 199);
    heuristic.put(City.ZERIND, 374);
    return new EnumMap<>(heuristic)::get;
  }
}
