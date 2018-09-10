package com.ipi;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ConnectedComponentCounter {

    // Conta o numero de componentes conectados
    public static int count (BufferedImage image) {
        int rows = image.getHeight();
        int columns = image.getWidth();
        int[][] matrix = new int[rows][columns];

        int countObject = 0;
        int countFile = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Color color = new Color(image.getRGB(i, j));
                int red = color.getRed();
                if (red == 0 && matrix[i][j] == 0) {
                    countFile = countConnected(image, matrix, countFile, i, j);
                    countObject++;
                }
            }
        }
        return countObject;
    }

    // Rotula a vizinhanca
    private static int countConnected(BufferedImage image, int[][] matrix, int countFile, int i, int j) {
        int rows = image.getHeight();
        int columns = image.getWidth();
        int[] fileX = new int[rows*columns];
        int[] fileY = new int[rows*columns];
        boolean stop = false;
        while (!stop) {
            countFile = westNeighbours(image, matrix, countFile, i, j, columns, fileX, fileY);
            countFile = eastNeighbours(image, matrix, countFile, i, j, rows, columns, fileX, fileY);
            countFile = northAndSouthNeighbours(image, matrix, countFile, i, j, rows, fileX, fileY);
            if (countFile > 0) {
                countFile--;
                i = fileX[countFile];
                j = fileY[countFile];
            } else {
                stop = true;
            }
        }
        return countFile;
    }

    // Verifica se existem vizinhos no sul e no norte
    private static int northAndSouthNeighbours(BufferedImage image, int[][] matrix, int countFile, int i, int j, int rows,
        int[] fileX, int[] fileY) {
        // Vizinho do sul
        if (i < rows - 1) {
            Color c = new Color(image.getRGB(i + 1, j));
            // Rotula esses vizinhos
            if (c.getRed() == 0 && matrix[i + 1][j] == 0) {
                matrix[i + 1][j] = 1;
                fileX[countFile] = i + 1;
                fileY[countFile] = j;
                countFile++;
            }
        }

        // Vizinho do norte
        if (i > 0) {
            Color c = new Color(image.getRGB(i - 1, j));
            // Rotula esses vizinhos
            if (c.getRed() == 0 && matrix[i - 1][j] == 0) {
                matrix[i - 1][j] = 1;
                fileX[countFile] = i - 1;
                fileY[countFile] = j;
                countFile++;
            }
        }
        return countFile;
    }

    // Verifica se existem ao leste
    private static int eastNeighbours(BufferedImage image, int[][] matrix, int countFile, int i, int j, int rows,
        int columns, int[] fileX, int[] fileY) {

        // Vizinho do leste
        if (j < columns - 1) {
            Color c = new Color(image.getRGB(i, j + 1));
            // Rotula esses vizinhos
            if (c.getRed() == 0 && matrix[i][j + 1] == 0) {
                matrix[i][j + 1] = 1;
                fileX[countFile] = i;
                fileY[countFile] = j + 1;
                countFile++;
            }
        }

        // Vizinho do nordeste
        if (i > 0 && j < rows - 1) {
            Color c = new Color(image.getRGB(i - 1, j + 1));
            // Rotula esses vizinhos
            if (c.getRed() == 0 && matrix[i - 1][j + 1] == 0) {
                matrix[i - 1][j + 1] = 1;
                fileX[countFile] = i - 1;
                fileY[countFile] = j + 1;
                countFile++;
            }
        }

        // Vizinho do sudeste
        if (i < columns - 1 && j < rows - 1) {
            Color c = new Color(image.getRGB(i + 1, j + 1));
            // Rotula esses vizinhos
            if (c.getRed() == 0 && matrix[i + 1][j + 1] == 0) {
                matrix[i + 1][j + 1] = 1;
                fileX[countFile] = i + 1;
                fileY[countFile] = j + 1;
                countFile++;
            }
        }
        return countFile;
    }

    // Verifica se existem ao oeste
    private static int westNeighbours(BufferedImage image, int[][] matrix, int countFile, int i, int j, int columns,
        int[] fileX, int[] fileY) {

        // Vizinho do oeste
        if (j > 0) {
            Color c = new Color(image.getRGB(i, j - 1));
            // Rotula esses vizinhos
            if (c.getRed() == 0 && matrix[i][j - 1] == 0) {
                matrix[i][j - 1] = 1;
                fileX[countFile] = i;
                fileY[countFile] = j - 1;
                countFile++;
            }
        }

        // Vizinho do noroeste
        if (i > 0 && j > 0) {
            Color c = new Color(image.getRGB(i - 1, j - 1));
            // Rotula esses vizinhos
            if (c.getRed() == 0 && matrix[i - 1][j - 1] == 0) {
                matrix[i - 1][j - 1] = 1;
                fileX[countFile] = i - 1;
                fileY[countFile] = j - 1;
                countFile++;
            }
        }

        // Vizinho do sudoeste
        if (i < columns - 1 && j > 0) {
            Color c = new Color(image.getRGB(i + 1, j - 1));
            // Rotula esses vizinhos
            if (c.getRed() == 0 && matrix[i + 1][j - 1] == 0) {
                matrix[i + 1][j - 1] = 1;
                fileX[countFile] = i + 1;
                fileY[countFile] = j - 1;
                countFile++;
            }
        }
        return countFile;
    }

}
