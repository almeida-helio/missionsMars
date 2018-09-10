package com.ipi;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalTime;

public class Main {

    private static final int[] INITIAL_POSITION = {415, 260};

    private static final int[] FINAL_POSITION = {1000, 815};

    public static void main(String[] args)  {
        BufferedImage imagem, imagemGrayScale, imagemEqualizada;
        try {
            imagem = FileManager.read("src/com/ipi/spots.tif");
            FindLife.find(imagem);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getName() {
        return LocalTime.now().toString();
    }
}

