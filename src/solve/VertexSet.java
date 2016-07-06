/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solve;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author neophron
 */
public class VertexSet {

    private List<Vertex> vertexes;

    public VertexSet(List<Pair<Integer, Integer>> l) {
        vertexes = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            vertexes.add(new Vertex(l.get(i).getKey(), l.get(i).getValue()));
        }
        if (/*checkClock()*/true) {
            for (int i = 0; i < vertexes.size() / 2; i++) {
                Vertex tmp = vertexes.get(i);
                vertexes.set(i, vertexes.get(vertexes.size() - i - 1));
                vertexes.set(vertexes.size() - i - 1, tmp);
            }
        }
    }

    private boolean checkClock() {
        double s = (vertexes.get(0).getX()
                - vertexes.get(vertexes.size() - 1).getX())
                * (vertexes.get(0).getY()
                - vertexes.get(vertexes.size() - 1).getY());
        for (int i = 0; i < vertexes.size() - 1; i++) {
            s += (vertexes.get(i + 1).getX()
                    - vertexes.get(i).getX())
                    * (vertexes.get(i + 1).getY()
                    - vertexes.get(i).getY());
        }
        return s > 0;
    }

    public VertexSet(VertexSet v) {
        vertexes = new ArrayList<>(v.vertexes);
    }

    public int next(int i) {
        i++;
        if (i == vertexes.size()) {
            i = 0;
        }
        return i;
    }

    public int prev(int i) {
        i--;
        if (i == -1) {
            i = vertexes.size() - 1;
        }
        return i;
    }

    public Vertex get(int i) {
        return vertexes.get(i);
    }

    public void del(int i) {
        vertexes.remove(i);
    }

    public int size() {
        return vertexes.size();
    }
}
