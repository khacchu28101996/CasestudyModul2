package model;

import java.util.ArrayList;

public interface IOFileInterface<E> {

        void writeFile(ArrayList<E> e, String path);

        ArrayList<E> readFile(String path);
    }

