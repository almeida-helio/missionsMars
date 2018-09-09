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
            imagem = FileManager.read("src/com/ipi/Mars.bmp");
            imagemGrayScale =  ImageManager.toGrayScale(imagem);
            imagemEqualizada = Equalization.equalize(imagemGrayScale);
            DiscoverPath.calculePath(INITIAL_POSITION, FINAL_POSITION, imagemEqualizada, imagem);
            FileManager.write(imagem, "src/com/ipi/tests/" + getName() +".bmp");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getName() {
        return LocalTime.now().toString();
    }
}

