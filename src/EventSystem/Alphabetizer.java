package EventSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Alphabetizer implements Observer {

    private ArrayList<String> kwicList;

    public Alphabetizer(ArrayList<String> kwicList) {

        this.kwicList = kwicList;
    }

    public ArrayList<String> getKwicList() {

        return kwicList;
    }

    @Override
    public void toDo() {

        Collections.sort(this.kwicList, new AlphabetizerComparator());
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