/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
    	for (int i = 0; i < 4;  i ++) {
    		turtle.forward(sideLength);
    		turtle.turn(90);
    	}
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return (((sides - 2) * 180) / (float)(sides));
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        double sides = 360 / (180 - angle);
        return Math.round((float)sides);
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angles = calculateRegularPolygonAngle(sides);
        for (int i = 0; i < sides; i ++) {
        	turtle.forward(sideLength);
        	turtle.turn(angles);
        }
        
        
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
    	
    	double dx = targetX - currentX;
        double dy = targetY - currentY;

        // Already at the target: no turn needed
        if (dx == 0 && dy == 0) return 0.0;

        double bearing = Math.toDegrees(Math.atan2(dx, dy));
        if (bearing < 0) bearing += 360.0;  // normalize to [0, 360)

        // Right-turn amount from currentHeading to bearing, normalized to [0, 360)
        double turn = (bearing - currentHeading) % 360.0;
        if (turn < 0) turn += 360.0;

        return turn;
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        int index = 0;
        List <Double> result = new ArrayList();
        result.add(0.0);
        while(index != xCoords.size() -1) {
        	int curr_x = xCoords.get(index);
        	int next_x = xCoords.get(index + 1);
        	int curr_y = yCoords.get(index);
        	int next_y = yCoords.get(index + 1);
        	result.add(calculateHeadingToPoint(result.get(index), curr_x, curr_y, next_x, next_y));
        	index ++;
        }
        result.remove(0);
        return result;
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        int side = 70;            // starting side length
        int squares = 36;         // how many rotated squares to draw
        int rotate = 10;          // rotation between squares (degrees)

        for (int i = 0; i < squares; i++) {
            // cycle through colors
            switch (i % 6) {
                case 0: turtle.color(PenColor.RED);    break;
                case 1: turtle.color(PenColor.ORANGE); break;
                case 2: turtle.color(PenColor.YELLOW); break;
                case 3: turtle.color(PenColor.GREEN);  break;
                case 4: turtle.color(PenColor.BLUE);   break;
                default: turtle.color(PenColor.MAGENTA);
            }

            // draw one square
            for (int s = 0; s < 4; s++) {
                turtle.forward(side);
                turtle.turn(90);
            }

            // rotate a bit and gently vary size for a nicer pattern
            turtle.turn(rotate);
            side = 60 + (i % 12);  // small size wobble: 60..71
        }
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();
    }

}
