package Pipe_Filter;

import java.io.IOException;

public abstract class Filter {
    protected Pipe input;
    protected Pipe output;

    public Filter(Pipe input, Pipe output) {

        this.input = input;
        this.output = output;
    }
    protected abstract void transform() throws IOException;
}
