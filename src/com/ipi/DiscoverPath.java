package com.ipi;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DiscoverPath {

    public static void calculePath (int[] initialPosition, int[] finalPosition, BufferedImage eqImage, BufferedImage orImage) {
        if (validationPair(initialPosition) || validationPair(finalPosition)) {
            return;
        }

        Point[][] points = getPoints(eqImage);

        int[] currentPosition = {initialPosition[0], initialPosition[1]};
        double distance = 0;
        int foots = 0;
        while (true) {
            List<Point> selected = neighboursDistance(points, currentPosition, finalPosition);
            Point choosen = choosePoint(selected);
            int[] pair = {choosen.getX(), choosen.getY()};
            distance += distancePair(currentPosition, pair);
            currentPosition = pair;
            drawPath(orImage, currentPosition);
            foots ++;
            if (currentPosition[0] == finalPosition[0] && currentPosition[1] == finalPosition[1]) {
                break;
            }
        }
        System.out.println("foots: " + foots);
        System.out.println("distance: " + distance);
    }


    public static void drawPath (BufferedImage image, int[] pass){

        image.setRGB(pass[0], pass[1], 0);
    }

    // valida um par
    private static boolean validationPair(int[] position) {
        if (position.length != 2  || position[0] < 0 || position[1] < 0) {
            return true;
        }
        return false;
    }


    // Inicializa o array de pontos
    private static Point[][] getPoints(BufferedImage image) {
        if (!image.equals(null)) {
            Point[][] points = new Point[image.getWidth()][image.getHeight()];
            for (int y = 0; y < image.getHeight(); y++) {
                for(int x = 0; x < image.getWidth(); x++) {
                    points[x][y] = new Point();
                    Color pixel = new Color(image.getRGB(x, y));
                    points[x][y].setValue(pixel.getBlue());
                    points[x][y].setX(x);
                    points[x][y].setY(y);
                    points[x][y].setWasVisited(Boolean.FALSE);
                }
            }
            return points;
        }
        return null;
    }

    // Calcula a distancia dos 8 vizinhos mais proximos
    private static List<Point> neighboursDistance(Point[][] points, int[] current, int[] destiny) {
        if (validationPair(current) || validationPair(destiny)) {
            return null;
        }
        // Faca o ponto atual ser visitado
        points[current[0]][current[1]].setWasVisited(Boolean.TRUE);
        List<Point> selected = new ArrayList<>();

        // Escolha e calcule a distancia dos 100 pontos(no maximo) mais proximos da posicaoo atual ate o destino
        for (int j = current[1] - 2; j <= (current[1] + 20); j++) {

            for (int i = current[0] - 2; i <= (current[0] + 20); i++) {
                if (!points[i][j].wasVisited()) {
                    int[] neighbour = {points[i][j].getX(), points[i][j].getY()};
                    points[i][j].setDistance(distancePair(neighbour, destiny));
                    points[i][j].setOriginDistance(distancePair(neighbour, current));
                    selected.add(points[i][j].clone());
                }
            }
        }
        // Reduza a lista aos 8 pontos com menor distancia
        return filterEightMinors(selected);
    }

    // Escolha os 8 pontos com menor distancia da origem
    private static List<Point> filterEightMinors(List<Point> points) {
        List<Point> selected = new ArrayList<>();
        threeMinors(points, selected, true);
        threeMinors(points, selected, true);
        threeMinors(points, selected, true);
        // Neste ponto selected contem 9 pontos ordenados crescentemente por distancia, remova o com maior distancia
        // Ou seja o ultimo
        selected.remove(selected.size() - 1);
        return selected;
    }

    // Calcula a distancia de um par
    private static double distancePair(int[] current, int [] destiny) {
        if (validationPair(current) || validationPair(destiny)) {
            return 0;
        }
        double squareX = Math.pow((destiny[0] - current[0]), 2.0);
        double squareY = Math.pow((destiny[1] - current[1]), 2.0);
        return Math.sqrt((squareX + squareY));
    }

    // Escolhe o ponto mais escuro dentre os tres menores de points
    private static Point choosePoint(List<Point> points) {
        List<Point> selected = new ArrayList<>();
        threeMinors(points, selected, false);
        int choosen = 0;
        for (int i = 0; i < 3 ; i++) {
            if (selected.get(choosen).getDistance() == 0) {
                choosen = i;
                break;
            } else if (selected.get(choosen).getValue() < selected.get(i).getValue()) {
                choosen = i;
            }
        }
        return selected.get(choosen);
    }

    // Escolha os tres pontos com a menor distancia do destino da lista de pontos
    private static void threeMinors(List<Point> points, List<Point> selected, boolean opc) {
        for (int i = 0; i < 3; i++) {
            Double minor = Double.MAX_VALUE;
            int choosen = 0;
            for (int j = 0; j < points.size(); j++) {
                if (minor > filterDistance(points.get(j), opc)) {
                    minor = filterDistance(points.get(j), opc);
                    choosen = j;
                }
            }
            selected.add(points.get(choosen).clone());
            points.remove(choosen);
        }
    }

    private static double filterDistance(Point point, boolean opc) {
        if (opc) {
            return point.getOriginDistance();
        } else {
            return point.getDistance();
        }
    }
}
