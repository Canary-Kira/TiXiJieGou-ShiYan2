package OOP;

public class Main {

    public static void excute(String inputPath, String outputPath){
        Input input = new Input();
        input.input(inputPath);
        Shift shift = new Shift(input.getLineTxt());
        shift.shift();
        Alphabetizer alphabetizer = new Alphabetizer(shift.getKwicList());
        alphabetizer.sort();
        Output output = new Output(alphabetizer.getKwicList());
        output.output(outputPath);
    }
}