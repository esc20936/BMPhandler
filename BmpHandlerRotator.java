import java.io.FileOutputStream;
import java.util.ArrayList;

public class BmpHandlerRotator {
    // Atributos
    private static ArrayList<Object> pixels;
    private static int ImageWidth;
    private static int ImageHeight;

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


    
    public BmpHandlerRotator(ArrayList<Object> pixels, int imageWidth, int imageHeight) {
        this.pixels = pixels;
        ImageWidth = imageWidth;
        ImageHeight = imageHeight;
        generateFile("Image-hrotation.bmp", "h");
        generateFile("Image-vrotation.bmp", "v");
    }

    public void generateFile(String filename, String typeRotation) {
       
        // Generate bmp black image with the same size of the original image
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
            if(typeRotation.equals("h")){
                for(int y = ImageHeight-1; y >0 ; y--) {
                    for(int x = 0; x<ImageWidth; x++) {
                        file.write( color(
                            (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[0],
                            (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[1],
                            (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[2]
                        ));
                    }
                }
            }else if(typeRotation.equals("v")){
                // rotate image vertically
                for(int y = 0; y < ImageHeight; y++) {
                    for(int x = 0; x < ImageWidth; x++) {
                        file.write( color(
                            (double) ((ArrayList<Double[]>) pixels.get(y)).get((ImageWidth-1)-x)[0],
                            (double) ((ArrayList<Double[]>) pixels.get(y)).get((ImageWidth-1)-x)[1],
                            (double) ((ArrayList<Double[]>) pixels.get(y)).get((ImageWidth-1)-x)[2]
                        ));
                        // System.out.println(x);
                    }
                }
            
                    // for(int x= ImageWidth-1; x > 0; x--) {
                    //     for(int y = 0; y < ImageHeight; y++) {
                    //         file.write( color(
                    //             (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[0],
                    //             (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[1],
                    //             (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[2]
                    //         ));
                    //     }
                    // }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
           
    }
}
