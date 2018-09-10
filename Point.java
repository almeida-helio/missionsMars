package com.ipi;

import java.util.Objects;

public class Point {

    private Boolean wasVisited;

    private int x;

    private int y;

    private int value;

    private double distance;

    private double originDistance;

    public boolean wasVisited() {
        return wasVisited;
    }

    public void setWasVisited(Boolean marked) {
        this.wasVisited = marked;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getOriginDistance() {
        return originDistance;
    }

    public void setOriginDistance(double originDistance) {
        this.originDistance = originDistance;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
            y == point.y &&
            value == point.value &&
            Double.compare(point.distance, distance) == 0 &&
            Double.compare(point.originDistance, originDistance) == 0 &&
            Objects.equals(wasVisited, point.wasVisited);
    }

    @Override
    public int hashCode() {

        return Objects.hash(wasVisited, x, y, value, distance, originDistance);
    }

    @Override
    public String toString() {
        return "Point{" +
            "wasVisited=" + wasVisited +
            ", x=" + x +
            ", y=" + y +
            ", value=" + value +
            ", distance=" + distance +
            ", originDistance=" + originDistance +
            '}';
    }

    public Point clone() {
            Point point = new Point();
            point.setWasVisited(this.wasVisited);
            point.setDistance(this.distance);
            point.setX(this.x);
            point.setY(this.y);
            point.setValue(this.value);
            point.setOriginDistance(this.originDistance);
            return point;
    }


}
