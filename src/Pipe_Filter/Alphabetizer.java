package Pipe_Filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Alphabetizer extends Filter {

    private ArrayList<String> kwicList = new ArrayList<>();
    public Alphabetizer(Pipe input, Pipe output) {

        super(input, output);
    }

    @Override
    protected void transform() throws IOException {

        while (input.hashNextLine()){

            kwicList.add(input.readerLine());
        }
        Collections.sort(this.kwicList, new AlphabetizerComparator());
        for (String line:kwicList){

            output.writerLine(line);
        }
        input.closeReader();
        output.closeWriter();
    }
    private class AlphabetizerComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {

            if (o1 == null && o2 == null) {

                throw new NullPointerException();
            }
            int compareValue = 0;
            char o1c = o1.toLowerCase().charAt(0); //忽略大小写
            char o2c = o2.toLowerCase().charAt(0); //忽略大小写
            compareValue = o1c - o2c;
            return compareValue;

        }

    }
}