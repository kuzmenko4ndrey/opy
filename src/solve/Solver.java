/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solve;

import GUI.MainForm;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.imageio.ImageIO;

/**
 *
 * @author neophron
 */
public class Solver {

    private VertexSet vertexes;
    private List<Triangle> triangles = new ArrayList<>();
    private BufferedImage bi;
    private Graphics g;
    private int trCount = 0;
    public static int MULT = 90;
    
    public int getN() {
        return vertexes.size();
    }

    public Solver(List<Pair<Integer, Integer>> l) {
        vertexes = new VertexSet(l);
        int m = 0;
        for (Pair<Integer, Integer> a : l) {
            if (m < a.getKey()) {
                m = a.getKey();
            }
            if (m < a.getValue()) {
                m = a.getValue();
            }
        }
        MULT = 600 / m;
    }

    private void triangulation() {
        VertexSet vs = new VertexSet(vertexes);
        while (vs.size() >= 3) {
            Triangle a = findEar(vs);
            if (a == null) {
                break;
            }
            g.drawLine(a.a.getX() * MULT, a.a.getY() * MULT,
                    a.b.getX() * MULT, a.b.getY() * MULT);
            g.drawLine(a.c.getX() * MULT, a.c.getY() * MULT,
                    a.b.getX() * MULT, a.b.getY() * MULT);
            g.drawLine(a.a.getX() * MULT, a.a.getY() * MULT,
                    a.c.getX() * MULT, a.c.getY() * MULT);
//            String p = "E:/opy/" + Integer.toString(trCount) + ".jpg";
//            trCount++;
//            File outputfile = new File(p);
//            try {
//                ImageIO.write(bi, "jpg", outputfile);
//            } catch (IOException ex) {
//                Logger.getLogger(Solver.class.getName()).log(Level.SEVERE, null, ex);
//            }
            triangles.add(a);
        }
    }

    private boolean isConvex(Vertex a, Vertex b, Vertex c) {
        double cp = (b.getX() - a.getX()) * (c.getY() - a.getY())
                - (b.getY() - a.getY()) * (c.getX() - a.getX());
        return cp > 0;
    }

    private boolean inTriangle(Vertex a, Vertex b, Vertex c, Vertex p) {
        double eps = 0;
        int x0 = p.getX(), y0 = p.getY();
        int x1 = a.getX(), y1 = a.getY();
        int x2 = b.getX(), y2 = b.getY();
        int x3 = c.getX(), y3 = c.getY();
        double v1 = (x1 - x0) * (y2 - y1) - (x2 - x1) * (y1 - y0);
        double v2 = (x2 - x0) * (y3 - y2) - (x3 - x2) * (y2 - y0);
        double v3 = (x3 - x0) * (y1 - y3) - (x1 - x3) * (y3 - y0);
        return (v1 >= eps && v2 >= eps && v3 >= eps)
                || (v1 <= eps && v2 <= eps && v3 <= eps);
    }

    private Triangle findEar(VertexSet v) {
        if (v.size() == 3) {
            Triangle t = new Triangle();
            t.a = v.get(0);
            t.b = v.get(1);
            t.c = v.get(2);
            v.del(0);
            v.del(0);
            v.del(0);
            return t;
        }
        for (int i = 0; i < v.size(); i++) {
            boolean tFail = false;
            Vertex p1 = v.get(v.prev(i));
            Vertex p2 = v.get(i);
            Vertex p3 = v.get(v.next(i));
            if (isConvex(p1, p2, p3)) {
                for (int j = 0; j < v.size(); j++) {
                    Vertex p = v.get(j);
                    if ((j != i && j != v.prev(i) && j != v.next(i))
                            && inTriangle(p1, p2, p3, p)) {
                        tFail = true;
                    }
                }
                if (!tFail) {
                    v.del(i);
                    Triangle t = new Triangle();
                    t.a = p1;
                    t.b = p2;
                    t.c = p3;
                    return t;
                }
            }
        }
        return null;
    }

    private void coloring() {
        for (int i = triangles.size() - 1; i >= 0; i--) {
            Triangle t = triangles.get(i);
            if (t.a.getColor() == -1) {
                if (t.b.getColor() != 0 && t.c.getColor() != 0) {
                    t.a.setColor(0);
                }
                if (t.b.getColor() != 1 && t.c.getColor() != 1) {
                    t.a.setColor(1);
                }
                if (t.b.getColor() != 2 && t.c.getColor() != 2) {
                    t.a.setColor(2);
                }
            }
            if (t.b.getColor() == -1) {
                if (t.a.getColor() != 0 && t.c.getColor() != 0) {
                    t.b.setColor(0);
                }
                if (t.a.getColor() != 1 && t.c.getColor() != 1) {
                    t.b.setColor(1);
                }
                if (t.a.getColor() != 2 && t.c.getColor() != 2) {
                    t.b.setColor(2);
                }
            }
            if (t.c.getColor() == -1) {
                if (t.b.getColor() != 0 && t.a.getColor() != 0) {
                    t.c.setColor(0);
                }
                if (t.b.getColor() != 1 && t.a.getColor() != 1) {
                    t.c.setColor(1);
                }
                if (t.b.getColor() != 2 && t.a.getColor() != 2) {
                    t.c.setColor(2);
                }
            }
        }
    }

    public BufferedImage getImage() {
        return bi;
    }

    public int doIt(Graphics gg) {
        bi = new BufferedImage(610, 610,
                BufferedImage.TYPE_INT_RGB);
        g = bi.getGraphics();
        g = gg;
        g.drawLine(vertexes.get(vertexes.size() - 1).getX() * MULT,
                vertexes.get(vertexes.size() - 1).getY() * MULT,
                vertexes.get(0).getX() * MULT,
                vertexes.get(0).getY() * MULT);
        for (int i = 0; i < vertexes.size() - 1; i++) {
            g.drawLine(vertexes.get(i).getX() * MULT,
                    vertexes.get(i).getY() * MULT,
                    vertexes.get(i + 1).getX() * MULT,
                    vertexes.get(i + 1).getY() * MULT);
        }
//        File outputfile = new File("E:/opy/image.jpg");
//        try {
//            ImageIO.write(bi, "jpg", outputfile);
//        } catch (IOException ex) {
//            Logger.getLogger(Solver.class.getName()).log(Level.SEVERE, null, ex);
//        }
        triangulation();
        coloring();
        int[] c = new int[3];
        for (int i = 0; i < vertexes.size(); i++) {
            c[vertexes.get(i).getColor()]++;
            switch (vertexes.get(i).getColor()) {
                case 0:
                    g.setColor(Color.red);
                    g.fillRect(vertexes.get(i).getX() * MULT - 2, vertexes.get(i).getY() * MULT - 2,
                            5, 5);
                    break;
                case 1:
                    g.setColor(Color.green);
                    g.fillRect(vertexes.get(i).getX() * MULT - 2, vertexes.get(i).getY() * MULT - 2,
                            5, 5);
                    break;
                case 2:
                    g.setColor(Color.blue);
                    g.fillRect(vertexes.get(i).getX() * MULT - 2, vertexes.get(i).getY() * MULT - 2,
                            5, 5);
                    break;
            }
        }
        int m = Math.min(c[0], Math.min(c[1], c[2]));
        if (c[0] == m) {
            for (int i = 0; i < vertexes.size(); i++) {
                if (0 == vertexes.get(i).getColor()) {
                    g.setColor(Color.white);
                    g.fillRect(vertexes.get(i).getX() * MULT - 3, vertexes.get(i).getY() * MULT - 3,
                            7, 7);
                }
            }
        } else if (c[1] == m) {
            for (int i = 0; i < vertexes.size(); i++) {
                if (1 == vertexes.get(i).getColor()) {
                    g.setColor(Color.white);
                    g.fillRect(vertexes.get(i).getX() * MULT - 3, vertexes.get(i).getY() * MULT - 3,
                            7, 7);
                }
            }
        } else if (c[2] == m) {
            for (int i = 0; i < vertexes.size(); i++) {
                if (2 == vertexes.get(i).getColor()) {
                    g.setColor(Color.white);
                    g.fillRect(vertexes.get(i).getX() * MULT - 3, vertexes.get(i).getY() * MULT - 3,
                            7, 7);
                }
            }
        }
//        outputfile = new File("E:/opy/fin.jpg");
//        try {
//            ImageIO.write(bi, "jpg", outputfile);
//        } catch (IOException ex) {
//            Logger.getLogger(Solver.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return m;

    }

//    public static void main(String args[]) {
//        Scanner in = new Scanner(System.in);
//        in.useLocale(Locale.US);
//        MainForm mf = new MainForm();
//        mf.setVisible(true);
//        List<Pair<Integer, Integer>> l = new ArrayList<>();
//        int n = in.nextInt();
//        for (int i = 0; i < n; i++) {
//            int x = in.nextInt();
//            int y = in.nextInt();
//            l.add(new Pair<>(x, y));
//        }
//        Solver s = new Solver(l);
//        int res = s.doIt();
//        System.out.println(res);
//    }
}
