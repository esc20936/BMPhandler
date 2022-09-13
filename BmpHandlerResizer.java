import java.io.FileOutputStream;
import java.util.ArrayList;

public class BmpHandlerResizer {

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


    public static byte[] color(double r, double g, double b) {
        return new byte[] {
            (byte) (int) (b * 255),
            (byte) (int) (g * 255),
            (byte) (int) (r * 255),
        };
    }


    public BmpHandlerResizer(ArrayList pixels, int ImageWidth, int ImageHeight) {
        generateFile("Image-thin.bmp", pixels, ImageWidth, ImageHeight,"thin");
        generateFile("Image-flat.bmp", pixels, ImageWidth, ImageHeight,"flat");
    }

    public void generateFile(String filename, ArrayList pixels, int ImageWidth, int ImageHeight, String typeResize) {
        // Generate bmp black image with the same size of the original image

        // ImageWidth = (ImageWidth%2==0)?ImageWidth/2:(ImageWidth+1)/2;

        // Create the file
        try {
            FileOutputStream file = new FileOutputStream(filename);
            if(typeResize.equals("thin")) {
                // Header
                file.write("B".getBytes());
                file.write("M".getBytes());
                file.write(dword(14 + 40 + (((ImageWidth%2==0)?ImageWidth/2:(ImageWidth+1)/2) * ImageHeight * 3)));
                file.write(dword(0));
                file.write(dword(14 + 40));

                //InfoHeader
                file.write(dword(40));
                file.write(dword(((ImageWidth%2==0)?ImageWidth/2:(ImageWidth+1)/2)));
                file.write(dword(ImageHeight));
                file.write(word(1));
                file.write(word(24));
                file.write(dword(0));
                file.write(dword(((ImageWidth%2==0)?ImageWidth/2:(ImageWidth+1)/2) * ImageHeight * 3));
                file.write(dword(0));
                file.write(dword(0));
                file.write(dword(0));
                file.write(dword(0));
            
               
                for(int y = 0; y < ImageHeight; y++) {
                    for(int x = 0; x < ImageWidth; x++) {
                        if(x%2==0) {
                            file.write( color(
                                (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[0],
                                (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[1],
                                (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[2]
                            ));
                        }
                        
                    }
                }

            } else if(typeResize.equals("flat")) {
               
                // Header
                file.write("B".getBytes());
                file.write("M".getBytes());
                file.write(dword(14 + 40 + (ImageWidth * ((ImageHeight%2==0)? ImageHeight/2 : (ImageHeight+1)/2) * 3)));
                file.write(dword(0));
                file.write(dword(14 + 40));

                //InfoHeader
                file.write(dword(40));
                file.write(dword(ImageWidth));
                file.write(dword(((ImageHeight%2==0)? ImageHeight/2 : (ImageHeight+1)/2)));
                file.write(word(1));
                file.write(word(24));
                file.write(dword(0));
                file.write(dword(ImageWidth * ((ImageHeight%2==0)? ImageHeight/2 : (ImageHeight+1)/2) * 3));
                file.write(dword(0));
                file.write(dword(0));
                file.write(dword(0));
                file.write(dword(0));
            
               
                for(int y = 0; y < ImageHeight; y++) {
                    for(int x = 0; x < ImageWidth; x++) {
                        if(y%2==0) {
                            file.write( color(
                                (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[0],
                                (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[1],
                                (double) ((ArrayList<Double[]>) pixels.get(y)).get(x)[2]
                            ));
                        }
                        
                    }
                }



            }
            

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
