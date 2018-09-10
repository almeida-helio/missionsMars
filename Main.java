package com.ipi;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    private static final int[] INITIAL_POSITION = {415, 260};

    private static final int[] FINAL_POSITION = {1000, 815};

    private static String DEFAULT_PATH = "./outputImages/";

    public static void main(String[] args)  {
        menu();
    }

    private static void menu() {
        System.out.println("Deseja procurar pelo caminho dos suprimentos ate a base? (S/N)");
        Scanner s = new Scanner(System.in);
        String r = s.nextLine();
        if (r.toLowerCase().equals("s")) {
            BufferedImage image = readAImage();
            discoverPath(image);
        }

        System.out.println("Deseja procurar por vida em Marte? (S/N)");
        r = s.nextLine();
        if (r.toLowerCase().equals("s")) {
            BufferedImage image = readAImage();
            findLife(image);
        }

        System.out.println("Algoritmo finalizado com sucesso!");
    }

    private static BufferedImage readAImage() {
        System.out.println("Insira o caminho de origem do arquivo incluindo o formato (.tif, .bmp, etc...).");
        Scanner s = new Scanner(System.in);
        String r = s.nextLine();
        try {
            return FileManager.read(r);
        } catch (IOException error) {
            System.out.println("Erro ao ler o caminho de origem.");
            return null;
        }

    }

    private static void findLife(BufferedImage image) {
        int[] result = FindLife.find(image);
        System.out.println("Exitem " + result[1] + " buracos nos " + result[0] + " componentes conectados.");
    }

    private static void discoverPath(BufferedImage image) {
        BufferedImage grayscaleImage, equalizedImage;
        grayscaleImage = ImageManager.toGrayScale(image);
        equalizedImage = Equalization.equalize(grayscaleImage);
        double [] result = DiscoverPath.calculePath(INITIAL_POSITION, FINAL_POSITION, equalizedImage, image);
        System.out.printf("O numero de pixels percorridos ate o destino e de %.2f. A distancia total percorrida e de %.2f\n", result[0], result[1]);
        try {
            String path = DEFAULT_PATH + "Result-"+ LocalDateTime.now().toString() + ".bmp";
            FileManager.write(image, path);
            Scanner s = new Scanner(System.in);
            System.out.println("Deseja salvar a imagem equalizada e a imagem em escala de cinza usadas na execucao (S/N)?");
            String res = s.nextLine();
            if (res.toLowerCase().equals("s")) {
                path = DEFAULT_PATH + "Grayscale-" + LocalDateTime.now().toString() + ".bmp";
                FileManager.write(grayscaleImage, path);
                path = DEFAULT_PATH + "Equalized-" + LocalDateTime.now().toString() + ".bmp";
                FileManager.write(equalizedImage, path);
            }
        } catch (IOException error) {
            System.out.println("Erro ao salvar imagem.");
        }
    }

}

