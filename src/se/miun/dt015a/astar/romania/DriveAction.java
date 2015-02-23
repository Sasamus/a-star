package se.miun.dt015a.astar.romania;

/**
 * The action of driving from one City to the next.
 * @author Christoffer Fink
 */
public class DriveAction {

  public final City from;
  public final City to;

  public DriveAction(City from, City to) {
    this.from = from;
    this.to = to;
  }
}
