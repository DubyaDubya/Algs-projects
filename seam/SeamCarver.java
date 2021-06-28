/* *****************************************************************************
 *  Name:William
 *  Date:Thursday, October 29th 2020
 *  Description:Seam Carver API as described in Princeton Algorithms part 2
 * Convention for points is that point (x,y) in picture is x pixels from
 * leftmost and point y is y pixels from topmost. in energies array, items are
 * expresed as [y][x] in priority queue, index is y*width + x
 **************************************************************************** */

import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;

public class SeamCarver {
    private int width, height;
    private EdgeWeightedDigraph G;
    private double[][] energies;
    private int[][] pixels;
    private double[][] distTo;
    private int[][][] vertexTo;


    public SeamCarver(Picture picture) {

        width = picture.width();
        height = picture.height();
        energies = new double[height][width];
        pixels = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y][x] = picture.getRGB(x, y);
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                energies[y][x] = energyCalculator(x, y);
            }
        }
    }

    public Picture picture() {
        Picture p = new Picture(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                p.setRGB(x, y, pixels[y][x]);
            }
        }
        return p;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    private double energyCalculator(int x, int y) {
        if (x >= width() || y >= height() || x < 0 || y < 0)
            throw new IllegalArgumentException();


        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
            return (double) 1000;

        // Store pixel values in Color objects.
        Color top = new Color(pixels[y - 1][x]);
        Color bottom = new Color(pixels[y + 1][x]);
        Color left = new Color(pixels[y][x - 1]);
        Color right = new Color(pixels[y][x + 1]);

        return Math.sqrt(gradient(top, bottom) + gradient(left, right));
    }

    private double gradient(Color a, Color b) {
        return (Math.pow(a.getRed() - b.getRed(), 2) +
                Math.pow(a.getBlue() - b.getBlue(), 2) +
                Math.pow(a.getGreen() - b.getGreen(), 2));
    }

    public double energy(int x, int y) {
        if (x >= width() || y >= height() || x < 0 || y < 0)
            throw new IllegalArgumentException();

        return energies[y][x];
    }

    public int[] findHorizontalSeam() {
        distTo = new double[height][width];
        vertexTo = new int[height][width][2];

        //set all unknowns equal to rediculous values
        for (int y = 0; y < height; y++) {
            for (int x = 1; x < width; x++) {
                distTo[y][x] = Double.POSITIVE_INFINITY;
                vertexTo[y][x][0] = Integer.MAX_VALUE;
                vertexTo[y][x][1] = Integer.MAX_VALUE;
            }
        }
        //set items on far left = to their energy
        for (int y = 0; y < height; y++) {
            distTo[y][0] = energy(0, y);
        }

        //set distTo and vertexTo  for all values from left to right
        for (int x = 1; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double minB4 = distTo[y][x - 1];
                int[] vertexB4 = { y, x - 1 };
                if (y != 0 && distTo[y - 1][x - 1] < minB4) {
                    minB4 = distTo[y - 1][x - 1];
                    vertexB4[0] = y - 1;
                }
                if (y != height - 1 && distTo[y + 1][x - 1] < minB4) {
                    minB4 = distTo[y + 1][x - 1];
                    vertexB4[0] = y + 1;
                }
                vertexTo[y][x] = vertexB4;
                distTo[y][x] = minB4 + energy(x, y);
            }
        }

        //find the minimum distTo all the way on the right
        int[] minID = { 0, width - 1 };
        for (int i = 1; i < height; i++) {
            if (distTo[i][width - 1] < distTo[minID[0]][minID[1]])
                minID[0] = i;
        }

        //find seam (indexed) by x value of pixel, value is y value of pixel
        int[] seam = new int[width];
        for (int i = width - 1; i >= 0; i--) {
            seam[i] = minID[0];
            minID = vertexTo[minID[0]][i];
        }
        distTo = null;
        vertexTo = null;
        return seam;
    }

    public int[] findVerticalSeam() {
        distTo = new double[height][width];
        vertexTo = new int[height][width][2];

        //set all unknowns equal to ridiculous values
        for (int y = 0; y < height; y++) {
            for (int x = 1; x < width; x++) {
                distTo[y][x] = Double.POSITIVE_INFINITY;
                vertexTo[y][x][0] = Integer.MAX_VALUE;
                vertexTo[y][x][1] = Integer.MAX_VALUE;
            }
        }
        //set items on top = to their energy
        for (int x = 0; x < width; x++) {
            distTo[0][x] = energy(x, 0);
        }

        //set distTo and vertexTo  for all values from top to bottom
        for (int y = 1; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double minB4 = distTo[y - 1][x];
                int[] vertexB4 = { y - 1, x };
                if (x != 0 && distTo[y - 1][x - 1] < minB4) {
                    minB4 = distTo[y - 1][x - 1];
                    vertexB4[1] = x - 1;
                }
                if (x != width - 1 && distTo[y - 1][x + 1] < minB4) {
                    minB4 = distTo[y - 1][x + 1];
                    vertexB4[1] = x + 1;
                }
                vertexTo[y][x] = vertexB4;
                distTo[y][x] = minB4 + energy(x, y);
            }
        }

        //find the minimum distTo all the way on the bottom
        int[] minID = { height - 1, 0 };
        for (int i = 1; i < width; i++) {
            if (distTo[height - 1][i] < distTo[minID[0]][minID[1]])
                minID[1] = i;
        }

        //find seam (indexed) by y value of pixel, value is x value of pixel
        int[] seam = new int[height];
        for (int i = height - 1; i >= 0; i--) {
            seam[i] = minID[1];
            minID = vertexTo[minID[0]][minID[1]];
        }
        distTo = null;
        vertexTo = null;
        return seam;
    }

    public void removeHorizontalSeam(int[] seam) {
        nullChecker(seam);
        if (seam.length != width) throw new IllegalArgumentException();
        for (int i = 0; i < seam.length; i++)
            if (seam[i] < 0 || seam[i] >= height) throw new IllegalArgumentException();

        for (int i = 1; i < seam.length; i++) {
            if (seam[i - 1] != seam[i] - 1 && seam[i - 1] != seam[i] + 1
                    && seam[i - 1] != seam[i]) throw new IllegalArgumentException();
        }

        int[][] newPixels = new int[height - 1][width];

        for (int i = 0; i < seam.length; i++) {
            for (int j = 0; j < (height - 1); j++) {
                if (j < seam[i]) {
                    newPixels[j][i] = pixels[j][i];
                }
                else {
                    newPixels[j][i] = pixels[j + 1][i];
                }
            }
        }
        pixels = newPixels;

        height = height - 1;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                energies[y][x] = energyCalculator(x, y);
            }
        }
    }

    public void removeVerticalSeam(int[] seam) {
        nullChecker(seam);
        if (seam.length != height) throw new IllegalArgumentException();
        for (int i = 0; i < seam.length; i++)
            if (seam[i] < 0 || seam[i] >= width) throw new IllegalArgumentException();
        for (int i = 1; i < seam.length; i++) {
            if (seam[i - 1] != seam[i] - 1 && seam[i - 1] != seam[i] + 1
                    && seam[i - 1] != seam[i]) throw new IllegalArgumentException();
        }

        int[][] newPixels = new int[height][width - 1];

        for (int j = 0; j < seam.length; j++) {
            for (int i = 0; i < (width - 1); i++) {
                if (i < seam[j]) {
                    newPixels[j][i] = pixels[j][i];
                }
                else {
                    newPixels[j][i] = pixels[j][i + 1];
                }
            }
        }
        pixels = newPixels;

        width = width - 1;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                energies[y][x] = energyCalculator(x, y);
            }
        }
    }


    private void nullChecker(Object a) {
        if (a == null) throw new IllegalArgumentException();
    }

    private void rangeChecker(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) throw new IllegalArgumentException();
    }


    public static void main(String[] args) {
        Picture p = new Picture("/Users/williamweiler/Downloads/6x5.png");
        SeamCarver sc = new SeamCarver(p);
        StdOut.println(sc.energy(1, 1));

    }
}
