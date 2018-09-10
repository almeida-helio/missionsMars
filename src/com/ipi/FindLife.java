package com.ipi;

import java.awt.image.BufferedImage;

public class FindLife {

    public static void find(BufferedImage image) {
        ConnectedComponentCounter.count(image);
        ImageManager.getNegative(image);
        ConnectedComponentCounter.count(image);
    }
}
