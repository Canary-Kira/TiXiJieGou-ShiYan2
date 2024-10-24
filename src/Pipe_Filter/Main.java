package Pipe_Filter;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void excute(String inputPath, String outputPath) throws IOException {
        File inFile = new File(inputPath);
        File outFile = new File(outputPath);
        Pipe pipe1 = new Pipe();
        Pipe pipe2 = new Pipe();
        Pipe pipe3 = new Pipe();
        Input input = new Input(inFile, pipe1);
        Shift shift = new Shift(pipe1, pipe2);
        Alphabetizer alphabetizer  = new Alphabetizer(pipe2, pipe3);
        Output output = new Output(outFile,pipe3);
        input.transform();
        shift.transform();
        alphabetizer.transform();
        output.transform();
        System.out.println("管道-过滤器");
    }
}
