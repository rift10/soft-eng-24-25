import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class ImageAlgorithm {

    private static final Random random = new Random();
    public static final int NUM_GENS = 1000;
    private static final int GEN_SIZE = 10;
    private static final int NUM_TRIS_START = 20;
    private static final double MUTATE_RATE = 0.25;
    private static final double GENE_POOL_PERCENT = 0.2;

    private static int IMAGE_WIDTH;
    private static int IMAGE_HEIGHT;

    private static double currentFitness = Integer.MAX_VALUE;
    private static double bestFitness = Integer.MAX_VALUE;
    private final List<List<Organism>> gens = new ArrayList<>();

    private static int currentGen = 0;
    private Organism currentBest;

    private final ImageGUI gui;
    private BufferedImage targetImage;

    public ImageAlgorithm(ImageGUI gui, String imagePath) {
        this.gui = gui;
        try {
            targetImage = ImageIO.read(new File(imagePath));
            IMAGE_WIDTH = targetImage.getWidth();
            IMAGE_HEIGHT = targetImage.getHeight();
        } catch (IOException e) {
            throw new RuntimeException("Error loading image: " + imagePath, e);
        }
        gens.add(new ArrayList<>());
        gens.add(new ArrayList<>());
    }

    public void start() {
        gens.set(0, generateOrganisms(generateFirstGen()));
        currentFitness = getBestFitness(0);
        bestFitness = getBestFitness(0);
        updateGUI(gens.get(0));
        System.out.println("Gen 0 Fitness: " + getBestFitness(0) + " # triangles: " + currentBest.triangles().size());
        currentGen++;
        while (currentGen <= NUM_GENS) run();
    }

    private void run() {
        int nextGen = currentGen % 2;
        int prevGen = 1 - nextGen;
        gens.set(nextGen, generateOrganisms(generateNextGen(gens.get(prevGen))));
        currentFitness = getBestFitness(currentGen);
        System.out.println("Gen " + currentGen + " Fitness: " + currentFitness + " # triangles: " + currentBest.triangles().size());
        updateGUI(gens.get(nextGen));
        currentGen++;
    }

    private void updateGUI(List<Organism> gen) {
        currentBest = Collections.min(gen, Comparator.comparingDouble(Organism::fitness));
        bestFitness = Math.min(bestFitness, currentBest.fitness());
        gui.updateImage(renderOrganism(currentBest));
        gui.updateLoad(currentGen + 1);
        gui.updateFitness(currentGen, (int) currentBest.fitness(), (int) bestFitness);
    }

    /** Generates the next generation from the previous one */
    private List<List<Triangle>> generateNextGen(List<Organism> prevGen) {
        // creating a list with fitness ordered from least to greatest
        List<Organism> genePool = new ArrayList<>(prevGen);
        genePool.sort(Comparator.comparingDouble(Organism::fitness));

        int poolSize = Math.max(1, (int) (genePool.size() * GENE_POOL_PERCENT));
        System.out.println("gene pool size: " + poolSize);

        // removing lower fitness organisms
        for (int i = poolSize; i < prevGen.size(); i++) {
            genePool.remove(poolSize);
        }

        List<List<Triangle>> children = new ArrayList<>(GEN_SIZE);
        for (int i = 0; i < GEN_SIZE / 2; i++) {
            Organism p1 = genePool.get(random.nextInt(poolSize));
            Organism p2 = genePool.get(random.nextInt(poolSize));
            children.add(crossover(p1, p2));
            children.add(crossover(p1, p2));
        }
        return children;
    }

    /** Crosses two parent organisms */
    private List<Triangle> crossover(Organism p1, Organism p2) {
        List<Triangle> child = new ArrayList<>();

        double opacity = random.nextDouble();
        for (int i = 0; i < Math.max(p1.triangles.size(), p2.triangles.size()); i++) {
            if (i < p1.triangles().size()) child.add(p1.triangles().get(i).withOpacity(opacity));
            if (i < p2.triangles().size()) child.add(p2.triangles().get(i).withOpacity(1 - opacity));
        }

        // remove any copies of triangles
        child = new ArrayList<>(child.stream().distinct().toList());

        if (random.nextDouble() < MUTATE_RATE / 2)
            child.add((random.nextDouble() < 0.5)
                    ? p1.triangles().get(random.nextInt(p1.triangles().size() - 1)).withRandomColor()
                    : p1.triangles().get(random.nextInt(p1.triangles().size() - 1)).withRandomPoint());
        if (random.nextDouble() < MUTATE_RATE / 2)
            child.add((random.nextDouble() < 0.5)
                    ? p2.triangles().get(random.nextInt(p2.triangles().size() - 1)).withRandomColor()
                    : p2.triangles().get(random.nextInt(p2.triangles().size() - 1)).withRandomPoint());
        // System.out.println("child size: " + child.size());
        // printTriangles(child);
        return child;
    }

    /** Returns the fitness of the best image in a specified generation */
    private double getBestFitness(int gen) {
        return getFittestOrganism(gen).fitness();
    }

    /** Returns the organism with the lowest fitness in a specified generation */
    public Organism getFittestOrganism(int gen) {
        return Collections.min(getGen(gen), Comparator.comparingDouble(o -> o.fitness()));
    }

    private List<Organism> getGen(int gen) {
        return gens.get(gen % 2 == 0 ? 0 : 1);
    }

    /** Generates organisms from a List of Lists of Triangles */
    private List<Organism> generateOrganisms(List<List<Triangle>> triangles) {
        List<Organism> organisms = new ArrayList<>(triangles.size());
        for (List<Triangle> tris : triangles) {
            organisms.add(new Organism(tris, fitness(tris)));
        }
        return organisms;
    }

    /** Returns the fitness of a list of Triangles */
    private double fitness(List<Triangle> triangles) {
        BufferedImage img = renderOrganism(triangles);
        // error equals number of triangles so that less triangles are preferred
        double error = triangles.size(); Color c1, c2;
        for (int x = 0; x < IMAGE_WIDTH; x++) {
            for (int y = 0; y < IMAGE_HEIGHT; y++) {
                c1 = new Color(targetImage.getRGB(x, y));
                c2 = new Color(img.getRGB(x, y), true);
                error += Math.abs(c1.getRed() - c2.getRed());
                error += Math.abs(c1.getGreen() - c2.getGreen());
                error += Math.abs(c1.getBlue() - c2.getBlue());
            }
        }
        img.flush();
        return error / (IMAGE_WIDTH * IMAGE_HEIGHT);
    }

    private BufferedImage renderOrganism(Organism o) {
        return renderOrganism(o.triangles());
    }

    /** Renders a BufferedImage from a List of Triangles */
    private BufferedImage renderOrganism(List<Triangle> org) {
        BufferedImage img = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g.setComposite(AlphaComposite.Src);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        Polygon poly = new Polygon(new int[3], new int[3], 3);

        for (Triangle t : org) {
            poly.xpoints[0] = t.x1();
            poly.ypoints[0] = t.y1();
            poly.xpoints[1] = t.x2();
            poly.ypoints[1] = t.y2();
            poly.xpoints[2] = t.x3();
            poly.ypoints[2] = t.y3();
            poly.invalidate();

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) t.a()));
            g.setColor(new Color(t.r(), t.g(), t.b(), (int) (t.a() * 255)));
            g.fillPolygon(poly);
        }

        g.dispose();
        // System.out.println("generated organism");
        return img;
    }

    private List<List<Triangle>> generateFirstGen() {
        List<List<Triangle>> result = new ArrayList<>(GEN_SIZE);
        for (int i = 0; i < GEN_SIZE; i++) {
            result.add(randomTriangles(NUM_TRIS_START));
        }
        return result;
    }

    private List<Triangle> randomTriangles(int count) {
        List<Triangle> tris = new ArrayList<>(count);
        for (int i = 0; i < count; i++) tris.add(randomTriangle());
        return tris;
    }

    private Triangle randomTriangle() {
        return new Triangle(
                random.nextInt(IMAGE_WIDTH), random.nextInt(IMAGE_HEIGHT),
                random.nextInt(IMAGE_WIDTH), random.nextInt(IMAGE_HEIGHT),
                random.nextInt(IMAGE_WIDTH), random.nextInt(IMAGE_HEIGHT),
                random.nextInt(256), random.nextInt(256), random.nextInt(256),
                1
        );
    }

    private void printTriangles(List<Triangle> list) {
        list.forEach(tri -> System.out.println("p1: (" + tri.x1() + ", " + tri.y1() + "), p2: (" 
        + tri.x2() + ", " + tri.y2() + "), p3: ("
        + tri.x3() + ", " + tri.y3() + "), rgba: ("
        + tri.r() + ", " + tri.g() + ", " + tri.b() + ", " + Math.round(tri.a() * 100.0) / 100.0 + ")"));
    }

    public record Organism(List<Triangle> triangles, double fitness) {}
    public record Triangle(int x1, int y1, int x2, int y2, int x3, int y3, int r, int g, int b, double a) {

        public Triangle withOpacity(double newA) {
            return new Triangle(x1, y1, x2, y2, x3, y3, r, g, b, newA);
        }

        public Triangle withRandomPoint() {
            return switch (random.nextInt(2)) {
                case 0 -> new Triangle(random.nextInt(IMAGE_WIDTH), random.nextInt(IMAGE_HEIGHT), x2, y2, x3, y3, r, g, b, a);
                case 1 -> new Triangle(x1, y1, random.nextInt(IMAGE_WIDTH), random.nextInt(IMAGE_HEIGHT), x3, y3, r, g, b, a);
                default -> new Triangle(x1, y1, x2, y2, random.nextInt(IMAGE_WIDTH), random.nextInt(IMAGE_HEIGHT), r, g, b, a);
            };
        }

        public Triangle withRandomColor() {
            int x = Math.clamp(random.nextInt(20) - 10, 0, 255);
            return switch (random.nextInt(3)) {
                case 0 -> new Triangle(x1, y1, x2, y2, x3, y3, x, g, b, a);
                case 1 -> new Triangle(x1, y1, x2, y2, x3, y3, r, x, b, a);
                case 2 -> new Triangle(x1, y1, x2, y2, x3, y3, r, g, x, a);
                default -> new Triangle(x1, y1, x2, y2, x3, y3, r, g, b, x);
            };
        }
    }
}
