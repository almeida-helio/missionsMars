package com.ipi;

import java.awt.image.BufferedImage;

public class FindLife {

    public static int[] find(BufferedImage image) {
        int[] result = new int[2];
        result[0] = ConnectedComponentCounter.count(image);
        ImageManager.getNegative(image);
        // O fundo preto da imagem negativa é contado como buraco
        result[1] = ConnectedComponentCounter.count(image) - 1;
        return  result;
    }
}
