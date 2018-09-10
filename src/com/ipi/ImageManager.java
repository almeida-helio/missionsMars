package com.ipi;

import java.awt.image.BufferedImage;

public class ImageManager {

    // Transforma img em escala de cinza
    public static BufferedImage toGrayScale(BufferedImage img) {
        BufferedImage escalaCinza = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getWidth(); ++i) {
            for (int j = 0; j < img.getHeight(); ++j) {
                int rgb = img.getRGB(i, j);
                int r = (rgb >> 16) & 255;
                int g = (rgb >> 8) & 255;
                int b = (rgb & 255);
                int nivelCinza =  (int)(0.2125*r + 0.7154*g + 0.0721*b);
                int gray = (nivelCinza << 16) + (nivelCinza << 8) + nivelCinza;
                escalaCinza.setRGB(i, j, gray);
            }
        }
        return escalaCinza;
    }


    // Transforma a imagem recebida no seu negativo
    public static void getNegative (BufferedImage image){
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int p = image.getRGB(x,y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                p = (a<<24) | (r<<16) | (g<<8) | b;
                image.setRGB(x, y, p);
            }
        }
    }
}
