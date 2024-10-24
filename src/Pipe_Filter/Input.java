package Pipe_Filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Input extends Filter {

    private File file;
    public Input(File file,Pipe output) {

        super(null, output);
        this.file = file;
    }

    @Override
    protected void transform() throws IOException {

        BufferedReader inputFile = null;
        try {

            inputFile = new BufferedReader(new FileReader(file));
        } catch (Exception e) {

            e.printStackTrace();
        }
        String line;
        try {

            while ((line = inputFile.readLine()) != null) {

                output.writerLine(line);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        output.closeWriter();
    }
}