// Importamos las librerias necesarias
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class BMPImageHandler {

    // Atributos
    private static ArrayList<Object> pixels;
    private static int ImageWidth;
    private static int ImageHeight;

    // Funcion para setear los atributos de la imagen tales como ancho, alto y matriz de pixeles
    public static Boolean setImageAttributes(String path) {
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] bytes = new byte[54];
            bis.read(bytes, 0, 54);
            ByteBuffer bb = ByteBuffer.wrap(bytes);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            int width = bb.getInt(18);
            int height = bb.getInt(22);
            ArrayList<Double[]> pixelRows = new ArrayList<Double[]>();
            for(int y = 0; y < height; y++) {
                pixelRows= new ArrayList<Double[]>();
                for(int x = 0; x < width; x++) {
                    Double b = bis.read()/255.0;
                    Double g = bis.read()/255.0;
                    Double r = bis.read()/255.0;
                    Double[] pixel = {r, g, b};
                    pixelRows.add(pixel);
                    
                }
                pixels.add(pixelRows);
            }
            ImageWidth = width;
            ImageHeight = height;
            bis.close();
            return true;
          
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

  

    // Funcion para validar que los argumentos sean validos
    public static boolean validArgs(String[] args) {

         // Bandera para indicar si los argumentos son correctos
        boolean validArgs = false;

        if(args.length==1){
            if(args[0].equals("-help")){
                print("Uso: java BMPImageHandler [option] [input file]");
                print("Opciones:");
                print("-help: Desplegar mensaje de ayuda");
                print("-basics: Generar imagenes rgb y filtro sepia");
                print("-rotate: Generar rotaciones en el eje X y Y");
                print("-resize: Cambiar el tamaño de la imagen");
                print("-all: Ejecutar todas las opciones");
                validArgs = true;
            }
        }else if(args.length==2){
            if((args[0].equals("-basics") || args[0].equals("-rotate") || args[0].equals("-resize") || args[0].equals("-all") && ((args[1].endsWith(".bmp") || args[1].endsWith(".BMP")) && (new File(args[1])).exists()))){
                validArgs = true;
            }

        }

        return validArgs;
    }

    // Funcion para imprimir mensajes
    static void print(String s) {
        System.out.println(s);
    }

    // Funcion para correr el programa
    public static void main(String[] args) {

        pixels = new ArrayList();
        Boolean validArgs = validArgs(args);

        if(validArgs){
            String option = args[0];
            if(!option.equals("-help")){
                String inputFile = args[1];
                if(setImageAttributes(inputFile)){
                    if(option.equals("-basics") ){
                        print("Generando imagenes rgb y filtro sepia");
                        new BmpHandlerCore(pixels, ImageWidth, ImageHeight);
                    }
                    else if(option.equals("-rotate") ){
                        print("Generando rotaciones en el eje X y Y");
                        new BmpHandlerRotator(pixels, ImageWidth, ImageHeight);


                    }
                    else if(option.equals("-resize") ){
                        print("Cambiando el tamaño de la imagen");
                        new BmpHandlerResizer(pixels, ImageWidth, ImageHeight);
                        
                    }else if(option.equals("-all") ){
                        print("Generando imagenes rgb y filtro sepia");
                        new BmpHandlerCore(pixels, ImageWidth, ImageHeight);
                        print("Generando rotaciones en el eje X y Y");
                        new BmpHandlerRotator(pixels, ImageWidth, ImageHeight);
                        print("Cambiando el tamaño de la imagen");
                        new BmpHandlerResizer(pixels, ImageWidth, ImageHeight);
                    }
                }else {
                    print("Error al leer el archivo");
                }
            }
        }else{
            print("ERROR: Argumentos invalidos");
            print("Escriba java BMPImageHandler -help para mas informacion");
        }
       


    }


}