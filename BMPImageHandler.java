import java.io.File;

public class BMPImageHandler {

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
    public static void main(String[] args) {

        Boolean validArgs = validArgs(args);

        if(validArgs){
            String option = args[0];

            if(!option.equals("-help")){
                String inputFile = args[1];
                
            }
        }else{
            print("ERROR: Argumentos invalidos");
            print("Escriba java BMPImageHandler -help para mas informacion");
        }
       


    }


}