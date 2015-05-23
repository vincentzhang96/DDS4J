package co.phoenixlab.dds;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Path path = Paths.get(scanner.nextLine());
        Dds dds = new Dds();
        dds.read(Files.newByteChannel(path, StandardOpenOption.READ));
        System.out.println(dds.toString());
    }
}
