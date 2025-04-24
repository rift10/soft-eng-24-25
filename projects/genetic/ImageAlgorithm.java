
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.imageio.ImageIO;

public class ImageAlgorithm {

    private static final Random random = new Random();
    public static final int GEN_SIZE = 10;
    public static final int NUM_TRIS_START = 10;
    public static final int NUM_TRIS = 1000;
    public static final int NUM_GENS = 50;
    public static final double MUTATE_RATE = 0.01;
    public static final double GENE_POOL_PERCENT = 0.1;
    public static final double REMOVAL_RATE = 0.01;
    private static int IMAGE_WIDTH;
    private static int IMAGE_HEIGHT;

    private static double currentFitness = Integer.MAX_VALUE;
    private static double bestFitness = Integer.MAX_VALUE;
    private final List<List<Organism>> allGens = new ArrayList<>();
    private Organism output;

    public static int currentGen = 0;
    public Organism currentBest;

    private final ImageGUI gui;
    private BufferedImage targetImage;

    public ImageAlgorithm(ImageGUI gui, String imagePath) {
        this.gui = gui;
        try {
            targetImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println(e);
        }
        IMAGE_WIDTH = targetImage.getWidth();
        IMAGE_HEIGHT = targetImage.getHeight();
        gui.setImageSize(IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    public void start() {
        allGens.add(generateOrganisms(generateFirstGen()));
        System.out.println("generated gen 0");
        currentFitness = getBestFitness(0);
        bestFitness = getBestFitness(0);
        gui.updateFitness(currentFitness, bestFitness);
        System.out.println("Gen 0 Fitness: " + getBestFitness(0));
        output = getBestImage(0);
        gui.updateImage(output.image(), currentGen);
        currentGen = 1;
        while (currentGen <= NUM_GENS) run();
    }

    public void run() {
        if (currentGen > NUM_GENS) return;
        allGens.add(generateOrganisms(generateNextGen(allGens.get(currentGen - 1))));
        System.out.println("generated gen " + currentGen);
        currentFitness = getBestFitness(currentGen);
        output = getBestImage(currentGen);
        bestFitness = Math.min(bestFitness, currentFitness);
        gui.updateFitness(currentFitness, bestFitness);
        currentBest = output;
        System.out.println("Gen " + currentGen + " Fitness: " + currentFitness);
        gui.updateImage(output.image(), currentGen);
        currentGen++;
    }

    /** Generates the next generation from the previous one */
    private List<List<Triangle>> generateNextGen(List<Organism> previousGen) {
        Map<Integer, Organism> fitMap = new TreeMap<>(Collections.reverseOrder());
        previousGen.forEach(o -> fitMap.put((int) fitness(o), o));
        List<Organism> temp = fitMap.values().stream().toList();
        List<Organism> genePool = new ArrayList<>();

        System.out.println("gene pool size: " + (temp.size() * GENE_POOL_PERCENT));

        for (int i = 0; i < (int) (temp.size() * GENE_POOL_PERCENT); i++) {
            genePool.add(temp.get(i));
        }

        List<List<Triangle>> result = new ArrayList<>();
        for (int i = 0; i < genePool.size()/2; i++) {
            Organism parentOne = previousGen.get(i);
            Organism parentTwo = previousGen.get(i + previousGen.size()/2);
            List<Triangle> child = new ArrayList<>();
            List<Triangle> parentOneTris = parentOne.triangles();
            List<Triangle> parentTwoTris = parentTwo.triangles();
            double opacityMult = random.nextDouble();
            parentOneTris.forEach(t -> {if (random.nextDouble() < REMOVAL_RATE) parentOneTris.remove(t);});
            parentTwoTris.forEach(t -> {if (random.nextDouble() < REMOVAL_RATE) parentTwoTris.remove(t);});
            parentOneTris.forEach(t -> t.setOpacity(opacityMult));
            parentTwoTris.forEach(t -> t.setOpacity(1 - opacityMult));
            System.out.println("parent one tris: " + parentOneTris.size());
            System.out.println("parent two tris: " + parentTwoTris.size());
            child.addAll(parentOneTris);
            child.addAll(parentTwoTris);
            if (random.nextDouble() < MUTATE_RATE) {
                if (random.nextDouble() < 0.5) {
                    child.add((random.nextDouble() < 0.5)
                            ? parentOne.triangles().get(random.nextInt(parentOne.triangles().size())).withRandomColor()
                            : parentOne.triangles().get(random.nextInt(parentOne.triangles().size())).withRandomPoint());
                } else {
                    child.add((random.nextDouble() < 0.5)
                            ? parentTwo.triangles().get(random.nextInt(parentOne.triangles().size())).withRandomColor()
                            : parentTwo.triangles().get(random.nextInt(parentOne.triangles().size())).withRandomPoint());
                }
            }
            result.add(child);
        }
        System.out.println("result size: " + result.size());
        return result;
    }

    /** Returns the fitness of the best image in a specified generation */
    private double getBestFitness(int gen) {
        return fitness(getBestImage(gen));
    }

    /** Returns the image with the highest fitness in a specified generation */
    public Organism getBestImage(int gen) {
        double fitness = Integer.MAX_VALUE;
        Organism result = allGens.get(gen).get(0);
        for (int i = 0; i < allGens.get(gen).size(); i++) {
            if (fitness(allGens.get(gen).get(i)) < fitness) {
                fitness = fitness(allGens.get(gen).get(i));
                result = allGens.get(gen).get(i);
            }
        }
        return result;
    }

    /** Returns the fitness of the image of the specified organism */
    private double fitness(Organism organism) {
        return fitness(organism.image());
    }

    /** Returns the fitness of the specified image */
    private double fitness(BufferedImage image) {
        double fitness = 0;
        for (int x = 0; x < image.getWidth() - 1; x++) {
            for (int y = 0; y < image.getHeight() - 1; y++) {
                Color color = new Color(image.getRGB(x, y), true);
                Color targetColor = new Color(targetImage.getRGB(x, y), true);
                fitness += Math.abs(targetColor.getRed() - color.getRed());
                fitness += Math.abs(targetColor.getGreen() - color.getGreen());
                fitness += Math.abs(targetColor.getBlue() - color.getBlue());
                fitness += Math.abs(targetColor.getAlpha() - color.getAlpha());
            }
        }
        fitness /= (image.getWidth() * image.getHeight());
        return fitness;
    }

    private List<Organism> generateOrganisms(List<List<Triangle>> triangles) {
        List<Organism> result = new ArrayList<>();
        triangles.forEach(genes -> result.add(generateOrganism(genes)));
        return result;
    }

    /** Generates images from lists of triangles to return a full organism */
    private Organism generateOrganism(List<Triangle> triangles) {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
    
        g2d.setComposite(AlphaComposite.Src);
        g2d.setColor(new Color(255, 255, 255, 255)); // white background
        g2d.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
    
        for (Triangle t : triangles) {
            Polygon p = new Polygon();
            p.addPoint(t.p1().x, t.p1().y);
            p.addPoint(t.p2().x, t.p2().y);
            p.addPoint(t.p3().x, t.p3().y);
    
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) t.a()));
            g2d.setColor(new Color(t.r, t.g, t.b, (int) (t.a * 255)));
            g2d.fillPolygon(p);
        }
    
        g2d.dispose();
        System.out.println("generated organism");
        return new Organism(triangles, image);
    }

    /** Generates a list of lists of random Triangles to fill the first generation */
    private List<List<Triangle>> generateFirstGen() {
        List<List<Triangle>> result = new ArrayList<>();
        for (int i = 0; i < GEN_SIZE; i++) {
            List<Triangle> tris = new ArrayList<>();
            for (int j = 0; j < NUM_TRIS_START; j++) {
                tris.add(randomTriangle());
            }
            result.add(tris);
        }
        return result;
    }

    private Triangle randomTriangle() {
        return new Triangle(
            new Point(random.nextInt(IMAGE_WIDTH), random.nextInt(IMAGE_HEIGHT)),
            new Point(random.nextInt(IMAGE_WIDTH), random.nextInt(IMAGE_HEIGHT)),
            new Point(random.nextInt(IMAGE_WIDTH), random.nextInt(IMAGE_HEIGHT)),
            random.nextInt(255), random.nextInt(255), random.nextInt(255), random.nextDouble());
    }

    /** <p> Genotype: List of Triangles </p>
     *  <p> Phenotype: Image  </p> */
    public record Organism(List<Triangle> triangles, BufferedImage image) {}
 
    public record Triangle(Point p1, Point p2, Point p3, int r, int g, int b, double a) {

        public Triangle setOpacity(double multiplier) {
            return new Triangle(p1, p2, p3, r, g, b, a * multiplier);
        }

        public Triangle withRandomPoint() {
            Point point = new Point(random.nextInt(IMAGE_WIDTH), random.nextInt(IMAGE_HEIGHT));
            return switch (random.nextInt(2)) {
                case 0 -> new Triangle(point, p2, p3, r, g, b, a);
                case 1 -> new Triangle(p1, point, p3, r, g, b, a);
                default -> new Triangle(p1, p2, point, r, g, b, a);
            };
        }

        public Triangle withRandomColor() {
            int x = Math.clamp(random.nextInt(20) - 10, 0, 255);
            return switch (random.nextInt(3)) {
                case 0 -> new Triangle(p1, p2, p3, x, g, b, a);
                case 1 -> new Triangle(p1, p2, p3, r, x, b, a);
                case 2 -> new Triangle(p1, p2, p3, r, g, x, a);
                default -> new Triangle(p1, p2, p3, r, g, b, x);
            };
        }
    }
}
