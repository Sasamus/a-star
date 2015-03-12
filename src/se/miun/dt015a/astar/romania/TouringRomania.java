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

		// A class to represent nodes and their g value
		class Node {

			// Hold the Successor of the node
			Successor<City, DriveAction> successor;

			// Holds it's g value
			int g;

			/**
			 * @return the successor
			 */
			public Successor<City, DriveAction> getSuccessor() {
				return successor;
			}

			/**
			 * @param successor
			 *            the successor to set
			 */
			public void setSuccessor(Successor<City, DriveAction> successor) {
				this.successor = successor;
			}

			/**
			 * @return the g
			 */
			public int getG() {
				return g;
			}

			/**
			 * @param g
			 *            the g to set
			 */
			public void setG(int g) {
				this.g = g;
			}
		}
		;

		// A Comparator
		final Comparator<Node> comparator = new Comparator<Node>() {

			@Override
			public int compare(Node n1, Node n2) {

				// Return 1 if n2 is best, -1 if n1 is and 0 if they are equal
				if ((heuristic.apply(n1.getSuccessor().state) + n1.getG()) > (heuristic
						.apply(n2.getSuccessor().state) + n2.getG())) {
					return 1;
				} else if ((heuristic.apply(n1.getSuccessor().state) + n1
						.getG()) < (heuristic.apply(n2.getSuccessor().state) + n2
						.getG())) {
					return -1;
				}
				return 0;
			}
		};

		// Holds a map of children and their parents
		HashMap<Node, Node> parentMap = new HashMap<Node, Node>();

		// A Successor to hold the current state, currently at the start state
		Successor<City, DriveAction> successor = new Successor<City, DriveAction>(
				problem.getStart(), null, 0);

		// Create an Node
		Node node = new Node();

		// Set Successor with successor
		node.setSuccessor(successor);

		// Set G to 0
		node.setG(0);

		// A HashSet to hold the explored cities
		HashSet<City> explored = new HashSet<City>();

		// Holds the successors
		Set<Successor<City, DriveAction>> successors;

		// Hold the nodes in the frontier
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(comparator);

		// Holds the path to the goal
		ArrayList<Successor<City, DriveAction>> path = new ArrayList<Successor<City, DriveAction>>();

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

			// Check if nodes state equals the goal state
			if (problem.isGoal(node.getSuccessor().state)) {

				// Add node to path
				path.add(node.getSuccessor());

				// A Node to hold a parent node
				Node parent = null;

				// While node is a key in parentMap
				while (parentMap.containsKey(node)) {

					// Get parent of node from parentMap
					parent = parentMap.get(node);

					// Add parent to path
					path.add(parent.getSuccessor());

					// Remove nodes parent from parentMap
					parentMap.remove(node);

					// Set node to parent
					node = parent;
				}

				// Reverse path so it's ordered from start to goal
				Collections.reverse(path);

				// Return a Solution made with path
				return new Solution<City, DriveAction>(path);
			}

			// Add nodes state to explored
			explored.add(node.getSuccessor().state);

			// Get Successors to node
			successors = problem.getSuccessors(node.getSuccessor().state);

			// Holds the Node to be removed
			Node replaceNode = null;

			// Iterate through the successors in successors
			for (Successor<City, DriveAction> tmpSuccessor : successors) {

				// Check if successors state is in frontier
				boolean cityInFrontier = false;
				for (Node tmpNode : frontier) {
					if (tmpNode.getSuccessor().state.equals(tmpSuccessor.state)) {
						cityInFrontier = true;
						break;
					}
				}

				// Create a Node
				Node newNode = new Node();

				// Set Successor
				newNode.setSuccessor(tmpSuccessor);

				// Set G
				newNode.setG(node.getG() + tmpSuccessor.cost);

				// Map newNode to node, it's parent
				parentMap.put(newNode, node);

				// Check if newNodess state exists in explored or frontier
				if (!explored.contains(newNode.getSuccessor().state)
						&& !cityInFrontier) {

					// If it doesn't

					// Insert newNode into frontier
					frontier.add(newNode);

				} else {

					// If it does

					// Iterate through frontier
					for (Node tmpNode : frontier) {

						// If tmpNode.state equals successor.state
						if (tmpNode.getSuccessor().state.equals(newNode
								.getSuccessor().state)) {

							// Check if successor is better than tmpNode
							// according to
							// comparator
							if (0 < comparator.compare(tmpNode, newNode)) {

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

					// Add newNode to frontier
					frontier.add(newNode);
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

		Solution<City, DriveAction> solution = null;

		System.out.println("Starting search...");

		TouringRomania solver = new TouringRomania();

		solution = solver.search(problem,
				TouringRomaniaProblem.MIN_NODES_TRAVERSED_HEURISTIC);

		System.out.println("Found a solution with total cost "
				+ solution.getCost());
		System.out.println("Solution visits these cities: "
				+ solution.getStates());

	}
}
