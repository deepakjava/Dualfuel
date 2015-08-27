package com.dualfual.common;

import java.util.ArrayList;

import java.util.Scanner;


public class QuickHull{

    public ArrayList<Point> quickHull(ArrayList<Point> points) {

        ArrayList<Point> convexHull = new ArrayList<Point>();
        if (points.size() < 3)
            return (ArrayList) points.clone();

        int minPoint = -1, maxPoint = -1;
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).getX() < minX){
                minX = points.get(i).getX();
                minPoint = i;
            }

            if (points.get(i).getX() > maxX) {
                maxX = points.get(i).getX();
                maxPoint = i;
            }

        }

        Point A = points.get(minPoint);
        Point B = points.get(maxPoint);

        convexHull.add(A);
        convexHull.add(B);

        points.remove(A);
        points.remove(B);


        ArrayList<Point> leftSet = new ArrayList<Point>();
        ArrayList<Point> rightSet = new ArrayList<Point>();

        for (int i = 0; i < points.size(); i++) {

            Point p = points.get(i);
            if (pointLocation(A, B, p) == -1)
                leftSet.add(p);

            else if (pointLocation(A, B, p) == 1)
                rightSet.add(p);

        }

        hullSet(A, B, rightSet, convexHull);
        hullSet(B, A, leftSet, convexHull);
        return convexHull;

    }


    public double distance(Point A, Point B, Point C) {

        double ABx = B.getX() - A.getX();
        double ABy = B.getY() - A.getY();

        double num = ABx * (A.getY() - C.getY()) - ABy * (A.getX() - C.getX());
        if (num < 0)
            num = -num;

        return num;
    }


    public void hullSet(Point A, Point B, ArrayList<Point> set, ArrayList<Point> hull) {

        int insertPosition = hull.indexOf(B);
        if (set.size() == 0)
            return;

        if (set.size() == 1) {

            Point p = set.get(0);
            set.remove(p);
            hull.add(insertPosition, p);
            return;
        }

        double dist = Double.MIN_VALUE;
        int furthestPoint = -1;

        for (int i = 0; i < set.size(); i++) {

            Point p = set.get(i);
            double distance = distance(A, B, p);
            if (distance > dist) {
                dist = distance;
                furthestPoint = i;
            }

        }

        Point P = set.get(furthestPoint);
        set.remove(furthestPoint);
        hull.add(insertPosition, P);


        // Determine who's to the left of AP

        ArrayList<Point> leftSetAP = new ArrayList<Point>();
        for (int i = 0; i < set.size(); i++) {

            Point M = set.get(i);
            if (pointLocation(A, P, M) == 1) {
                leftSetAP.add(M);
            }

        }


        // Determine who's to the left of PB

        ArrayList<Point> leftSetPB = new ArrayList<Point>();
        for (int i = 0; i < set.size(); i++) {

            Point M = set.get(i);
            if (pointLocation(P, B, M) == 1) {
                leftSetPB.add(M);
            }
        }

        hullSet(A, P, leftSetAP, hull);
        hullSet(P, B, leftSetPB, hull);

    }


    public int pointLocation(Point A, Point B, Point P) {

        double cp1 = (B.getX() - A.getX()) * (P.getY() - A.getY()) - (B.getY() - A.getY()) * (P.getX() - A.getX());
        if (cp1 > 0)
            return 1;

        else if (cp1 == 0)
            return 0;
        else
            return -1;
    }


    public static void main(String args[]) {

        System.out.println("Quick Hull Test");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of points");
        int N = sc.nextInt();

        ArrayList<Point> points = new ArrayList<Point>();
        System.out.println("Enter the coordinates of each points: <x> <y>");
        for (int i = 0; i < N; i++) {

            double x = sc.nextDouble();
            double y = sc.nextDouble();

            Point e = new Point(x, y);
            points.add(i, e);
        }


        QuickHull qh = new QuickHull();
        ArrayList<Point> p = qh.quickHull(points);
        System.out.println("The points in the Convex hull using Quick Hull are: ");
        for (int i = 0; i < p.size(); i++)
            System.out.println("(" + p.get(i).getX() + ", " + p.get(i).getY() + ")");

        sc.close();

    }

}