package se.miun.dt015a.astar.romania;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import se.miun.dt015a.search.Heuristic;
import se.miun.dt015a.search.InformedSearch;
import se.miun.dt015a.search.SearchProblem;
import se.miun.dt015a.search.Solution;
import se.miun.dt015a.search.Successor;

/**
 * A search algorithm for solving the "touring in Romania problem". You should
 * replace the search method with your implementation of the A* search
 * algorithm.
 *
 * Note that the course book uses the trip from Arad to Bucharest as an example,
 * but keep in mind that your algorithm may be asked to solve other pairs.
 *
 * @author Christoffer Fink
 */
public class TouringRomania implements InformedSearch<City, DriveAction> {

	// Do not add any constructors or instance variables.
	// You are allowed to create auxiliary inner classes if necessary.

	@Override
	public Solution<City, DriveAction> search(
			SearchProblem<City, DriveAction> problem, Heuristic<City> heuristic) {

		// /* ----- You should remove this ----- */
		// System.out.println("I'm just going to drive around randomly until I reach my goal...");
		// InformedSearch<City,DriveAction> randomSearch = new RandomSearch<>();
		// return randomSearch.search(problem, heuristic);
		// /* ----- You should remove this ----- */
		
		// A Comparator class
	    final Comparator<City> comparator = new Comparator<City>(){
	         
	        @Override
	        public int compare(City c1, City c2) {
	            return (int) (heuristic.apply(c1) - heuristic.apply(c2));
	        }
	    };
		
		
		
		City node = problem.getStart();
		PriorityQueue<City> frontier = new PriorityQueue<City>(comparator);
		HashSet<City> explored = new HashSet<City>();

		
		
		

	}

	public static void main(String[] args) {
		City start = City.LUGOJ;
		City goal = City.ARAD;
		TouringRomaniaProblem problem = TouringRomaniaProblem.getInstance(
				start, goal);

		City origin = problem.getStart();
		System.out.println("Starting in the city of " + origin);
		Set<Successor<City, DriveAction>> successors = problem
				.getSuccessors(origin);
		System.out.println("From there I can get to:");
		for (Successor<City, DriveAction> successor : successors) {
			System.out.println("  " + successor.state);
		}

		TouringRomania solver = new TouringRomania();
		System.out.println("Starting search...");
		Solution<City, DriveAction> solution = solver.search(problem, null);
		System.out.println("Found a solution with total cost "
				+ solution.getCost());
		System.out.println("Solution visits these cities: "
				+ solution.getStates());
	}
}
