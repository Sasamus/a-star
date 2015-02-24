package se.miun.dt015a.astar.romania;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

		// A Comparator
		final Comparator<Successor<City, DriveAction>> comparator = new Comparator<Successor<City, DriveAction>>() {

			@Override
			public int compare(Successor<City, DriveAction> c1,
					Successor<City, DriveAction> c2) {

				// TODO: Add g(n) to comparator

				return (heuristic.apply(c1.state) - heuristic.apply(c2.state));
			}
		};

		// A node to hold the current state, currently at the start state
		Successor<City, DriveAction> node = new Successor<City, DriveAction>(
				problem.getStart(), null, 0);

		// Hold the nodes int he frontier
		PriorityQueue<Successor<City, DriveAction>> frontier = new PriorityQueue<Successor<City, DriveAction>>(
				comparator);

		// A HashSet to hold the explored nodes
		HashSet<City> explored = new HashSet<City>();

		// Holds the successors
		Set<Successor<City, DriveAction>> successors;

		// Holds the path to the goal
		ArrayList<Successor<City, DriveAction>> path = new ArrayList<Successor<City, DriveAction>>();

		// Holds a map of children and their parents
		HashMap<Successor<City, DriveAction>, Successor<City, DriveAction>> parentMap = new HashMap<Successor<City, DriveAction>, Successor<City, DriveAction>>();

		// Add node to frontier
		frontier.add(node);

		// Loop forever
		while (true) {

			// Check if frontier is empty
			if (frontier.isEmpty()) {

				// Return null
				return null;
			}

			// Get the next node from frontier
			node = frontier.poll();

			// Check if node equals the goal state
			if (problem.isGoal(node.state)) {

				// Add node to path
				path.add(node);

				// A Successor to hold the parent node
				Successor<City, DriveAction> parent;

				// While node is a key in parentMap
				while (parentMap.containsKey(node)) {

					// Get parent of node from parentMap
					parent = parentMap.get(node);

					// Add parent to path
					path.add(parent);

					// Remove tmpNodes parent from parentMap
					parentMap.remove(node);

					// Set tmpNode to parent
					node = parent;
				}

				// Reverse path so it*s ordered from start to goal
				Collections.reverse(path);

				// Return a Solution made with path
				return new Solution<City, DriveAction>(path);
			}

			// Add node to explored
			explored.add(node.state);

			// Get Successors to node
			successors = problem.getSuccessors(node.state);

			// Holds the Node to be removed
			Successor<City, DriveAction> replaceNode = null;

			// Iterate through the successors in successors
			for (Successor<City, DriveAction> successor : successors) {

				// Check if successors state exists in explored or frontier
				if (!explored.contains(successor.state)
						&& !frontier.contains(successor.state)) {

					// Map successor to node, it's parent
					parentMap.put(successor, node);

					// If it doesn't, insert successors state in frontier
					frontier.add(successor);

				} else {

					// If it does
					// Iterate through frontier
					for (Successor<City, DriveAction> tmpNode : frontier) {

						// If tmpNode.state equals successor.state
						if (tmpNode.state.equals(successor.state)) {

							// Check if successor is better than tmpNode
							// according to
							// comparator
							if (0 > comparator.compare(tmpNode, successor)) {

								// If it is, set replaceNode to tmpNode
								replaceNode = tmpNode;
							}
						}
					}
				}

				// Check if replaceNode have been set
				if (replaceNode != null) {

					// Remove replaceNode from frontier
					frontier.remove(replaceNode);

					// Add successor.state to frontier
					frontier.add(successor);
				}
			}
		}
	}

	public static void main(String[] args) {
		City start = City.ARAD;
		City goal = City.BUCHAREST;
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
		Solution<City, DriveAction> solution = solver.search(problem,
				TouringRomaniaProblem.MANHATTAN_DISTANCE_HEURISTIC);
		System.out.println("Found a solution with total cost "
				+ solution.getCost());
		System.out.println("Solution visits these cities: "
				+ solution.getStates());
	}
}
