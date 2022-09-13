import java.io.FileOutputStream;
import java.util.ArrayList;

public class BmpHandlerCore {
    // Atributos
    private static ArrayList<Object> pixels;
    private static int ImageWidth;
    private static int ImageHeight;

    // Funcion para obtener en formato de byte los pixeles de la imagen
    public static byte[] color(double r, double g, double b) {
        return new byte[] {
            (byte) (int) (b * 255),
            (byte) (int) (g * 255),
            (byte) (int) (r * 255),
        };
    }

    // return word value as 2 bytes
    public static byte[] word(int value) {
        return new byte[] {
            (byte) (value & 0xff),
            (byte) ((value >> 8) & 0xff),
        };
    }
    // return double word value as 4 bytes
    public static byte[] dword(int value) {
        return new byte[] {
            (byte) (value & 0xff),
            (byte) ((value >> 8) & 0xff),
            (byte) ((value >> 16) & 0xff),
            (byte) ((value >> 24) & 0xff),
        };
    }


    // Constructor de la clase
    public BmpHandlerCore(ArrayList<Object> pixels, int imageWidth, int imageHeight) {
        this.pixels = pixels;
        ImageWidth = imageWidth;
        ImageHeight = imageHeight;
        glFinish("Image-red.bmp","r");
        glFinish("Image-green.bmp","g");
        glFinish("Image-blue.bmp","b");
        glFinish("Image-sepia.bmp","s");
    }

    // Create a new image from array of pixels
    public void glFinish(String filename) {
       
        // Create the file
        try {
            FileOutputStream file = new FileOutputStream(filename);
            // Header
            file.write("B".getBytes());
            file.write("M".getBytes());
            file.write(dword(14 + 40 + (ImageWidth * ImageHeight * 3)));
            file.write(dword(0));
            file.write(dword(14 + 40));

            //InfoHeader
            file.write(dword(40));
            file.write(dword(ImageWidth));
            file.write(dword(ImageHeight));
            file.write(word((short) 1));
            file.write(word((short) 24));
            file.write(dword(0));
            file.write(dword(ImageWidth * ImageHeight * 3));
            file.write(dword(0));
            file.write(dword(0));
            file.write(dword(0));
            file.write(dword(0));
            for(int y = 0; y < ImageHeight; y++) {
                for(int x = 0; x < ImageWidth; x++) {
                    file.write( color(
                        (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[0],
                        (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[1],
                        (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[2]
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
           
    }

    public void glFinish(String filename, String rgb) {
       
        // Create the file
        try {
            FileOutputStream file = new FileOutputStream(filename);
            // Header
            file.write("B".getBytes());
            file.write("M".getBytes());
            file.write(dword(14 + 40 + (ImageWidth * ImageHeight * 3)));
            file.write(dword(0));
            file.write(dword(14 + 40));

            //InfoHeader
            file.write(dword(40));
            file.write(dword(ImageWidth));
            file.write(dword(ImageHeight));
            file.write(word((short) 1));
            file.write(word((short) 24));
            file.write(dword(0));
            file.write(dword(ImageWidth * ImageHeight * 3));
            file.write(dword(0));
            file.write(dword(0));
            file.write(dword(0));
            file.write(dword(0));
            for(int y = 0; y < ImageHeight; y++) {
                for(int x = 0; x < ImageWidth; x++) {
                    if(rgb == "r") {
                        file.write( color(
                            (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[0],
                            (double) 0,
                            (double) 0
                        ));
                    } else if(rgb == "g") {
                        file.write( color(
                            (double) 0,
                            (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[1],
                            (double) 0
                        ));
                    } else if(rgb == "b") {
                        file.write( color(
                            (double) 0,
                            (double) 0,
                            (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[2]
                        ));
                    }else if(rgb == "s"){
                        double r = (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[0];
                        double g = (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[1];
                        double b = (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[2];

                        double tr = 0.393*r + 0.769*g + 0.189*b;
                        double tg = 0.349*r + 0.686*g + 0.168*b;
                        double tb = 0.272*r + 0.534*g + 0.131*b;

                        tr = tr > 1 ? 1 : tr;
                        tg = tg > 1 ? 1 : tg;
                        tb = tb > 1 ? 1 : tb;

                        file.write( color(
                            tr,
                            tg,
                            tb
                        ));

                    }
                    
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
           
    }

}
