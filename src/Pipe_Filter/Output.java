package Pipe_Filter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Output extends Filter {

    private File file;
    public Output(File file,Pipe input) {

        super(input, null);
        this.file = file;
    }

    @Override
    protected void transform() throws IOException {

        BufferedWriter outputFile =null;
        String line;
        try {

            outputFile = new BufferedWriter(new FileWriter(file));
            while (input.hashNextLine()) {

                outputFile.write(input.readerLine()+"\n");
            }
        }catch (IOException e){

            e.printStackTrace();
        }finally {

            try {

                if (outputFile!=null) {

                    outputFile.close();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        input.closeReader();
    }
}
