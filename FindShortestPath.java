import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class FindShortestPath {
    public static void main(String args[]) {
        try {
            if (args.length < 1) {
                // Throws IO exception
                throw new IOException();
            }
            File newFile = new File(args[0]);
            // Throws file not found exception if file was not opended properly or not in
            // the correct location
            if (newFile.exists() == false) {
                throw new FileNotFoundException();
            }
            String dungeonFileName = args[0];
            Dungeon newDungeon = new Dungeon(dungeonFileName);
            // Creates new Dungeon

            DLPriorityQueue<Hexagon> dungeonLayout = new DLPriorityQueue<Hexagon>();
            // Stores dungeon layout in a priority queue
            Hexagon startingChamber = newDungeon.getStart();
            // Gets starting chamber
            startingChamber.markEnqueued();
            // Mark starting chamber as enqueded
            dungeonLayout.add(startingChamber, 0);
            // Add starting chamber with a priority of 0

            boolean exitFound = false;
            int distance = 0;

            // Iterate through the while loop while dungeon layout is not empty and exit is
            // not found
            while (!dungeonLayout.isEmpty() && !exitFound) {
                Hexagon currentChamber = dungeonLayout.removeMin();
                // Get the current chamber & mark as dequeued
                currentChamber.markDequeued();
                if (currentChamber.isExit()) {
                    // If current chamber is exit then trace our path back to find the distance
                    exitFound = true;
                    while (currentChamber != newDungeon.getStart()) {
                        distance++;
                        currentChamber = currentChamber.getPredecessor();
                        if (currentChamber == newDungeon.getStart()) {
                            distance++;
                        }
                    }
                    exitFound = true;
                    break;
                } else if (currentChamber.isDragon()) {
                    // If current chamber is a dragon chamber skip this iteration
                    continue;
                }
                boolean neighboursHasDragon = false;
                for (int i = 0; i < 6; i++) {
                    // If any of current chambers neighbours are dragons skip this iteration
                    if (currentChamber.getNeighbour(i) != null) {
                        if (currentChamber.getNeighbour(i).isDragon()) {
                            neighboursHasDragon = true;
                            continue;
                        }
                    }
                }
                if(neighboursHasDragon){
                    continue;
                }
                for (int i = 0; i < 6; i++) {
                    // Loop through all the neighbours of current chamber as those are all the
                    // chambers we can choose to go to
                    Hexagon neighbours = currentChamber.getNeighbour(i);
                    // Get distance from start
                    int currentDistance = 1 + currentChamber.getDistanceToStart();
                    if (neighbours != null) {
                        // Check if neighbour is a valid chamber
                        if (!neighbours.isWall() && !neighbours.isMarkedDequeued()) {
                            // If neighbours chamber is a wall than we cannot traverse through it
                            // If neighbours chamber is marked then we have already visited it so no need to
                            // visit again

                            int neighbourDistance = neighbours.getDistanceToStart();
                            if (neighbourDistance > currentDistance) {
                                neighbours.setDistanceToStart(currentDistance);
                                neighbours.setPredecessor(currentChamber);
                            }
                            // If neighbours is mark enqueded and getDistanceToStart is currentSistance then
                            // update the priority
                            if (neighbours.isMarkedEnqueued() && neighbours.getDistanceToStart() == currentDistance) {
                                dungeonLayout.updatePriority(neighbours,
                                        neighbours.getDistanceToStart() + neighbours.getDistanceToExit(newDungeon));
                            } else if (!neighbours.isMarkedEnqueued()) {
                                // If neighbours isn't marked then mark it and add neighbours to our path
                                neighbours.markEnqueued();
                                dungeonLayout.add(neighbours,
                                        neighbours.getDistanceToStart() + neighbours.getDistanceToExit(newDungeon));
                            }
                        }
                    }
                }
            }
            if (exitFound) {
                // If exit was found then display the distance
                System.out.println("Path of length " + distance + " found");
            } else {
                // If not then no path must have been found
                System.out.println("No path found");
            }
            // Displays an appropriate mesage for all errors
        } catch (FileNotFoundException e1) {
            System.out.println("Input file not found");
        } catch (IOException e2) {
            System.out.println("No input file specified");
        } catch (InvalidDungeonCharacterException e3) {
            System.out.println("Invalid neighbour index");
        } catch (InvalidElementException e4) {
            System.out.println("Invalid element in Priority Queue");
        } catch (EmptyPriorityQueueException e5) {
            System.out.println("Priority Queue is empty");
        }

    }
}