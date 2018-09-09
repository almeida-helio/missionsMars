package com.ipi;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileManager {

    // Lê uma imagem no caminho path
    public static BufferedImage read(String path) throws IOException{
        BufferedImage imagem = ImageIO.read(new File(path));
        return imagem;
    }

    // Salva uma imagem no caminho path
    public static void write(BufferedImage imagem, String path) throws IOException{
        File output_file = new File(path);

        ImageIO.write(imagem, "bmp", output_file);
    }
}
