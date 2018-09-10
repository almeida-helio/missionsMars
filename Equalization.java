package com.ipi;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Equalization {

    // Equaliza a imagem com base em um histograma
    public static BufferedImage equalize(BufferedImage img) {
        int[] mappingPixel = computeHistogram(img);
        BufferedImage equalizedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        // Associa cada pixel da imagem anterior com sua normalizacao correspondente
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                applyHistogram(img, mappingPixel, equalizedImage, j, i);
            }
        }
        return equalizedImage;
    }

    // Aplica o histograma em cada pixel da nova imagem
    private static void applyHistogram(BufferedImage img, int[] mappingPixel, BufferedImage equalizedImage, int j, int i) {
        Color pixel = new Color(img.getRGB(i, j));
        int blue = pixel.getBlue();
        int newIntensity = mappingPixel[blue];
        Color newPixel = new Color(newIntensity, newIntensity, newIntensity);
        equalizedImage.setRGB(i, j, newPixel.getRGB());
    }

    // Distribuição de massa de probabilidade (Probability mass function)
    private static int[] pmf(BufferedImage img){
        int[] histogram = new int[256];
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color pixel = new Color(img.getRGB(x, y));
                int blue = pixel.getBlue();
                histogram[blue] += 1;
            }
        }
        return histogram;
    }

    // Distribuição acumulada de probabilidade (Cumulative Distributive function)
    private static int[] cdf(int[] histogram) {
        int[] a = new int[256];
        a[0] = histogram[0];
        for(int i=1;i < histogram.length;i++) {

            a[i] = histogram[i] + a[i-1];
        }
        return a;
    }

    // Retorna o primeiro valor com alguma incidência na imagem
    private static int minorValue(int[] histogram) {
        for(int i=0; i <histogram.length; i++) {
            if(histogram[i] > 0){
                return histogram[i];
            }
        }
        return 0;
    }

    // Normaliza o histograma
    private static int[] computeHistogram(BufferedImage img) {
        int numberOfPixels = img.getHeight() * img.getWidth();
        int[] histogram = pmf(img);
        int[] pixelMapping = new int[256];
        int[] a = cdf(histogram);
        float minor = minorValue(histogram);
        for(int i=0; i < histogram.length; i++) {
            // Normaliza a intensidade de cada pixel
            pixelMapping[i] = Math.round(((a[i] - minor) / (numberOfPixels - minor)) * 255);
        }
        return pixelMapping;
    }


}
