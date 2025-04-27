
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class ImageAlgorithm {

    private static final Random random = new Random();
    public static final int GEN_SIZE = 10;
    public static final int NUM_TRIS_START = 10;
    public static final int NUM_TRIS = 1000;
    public static final int NUM_GENS = 50;
    public static final double MUTATE_RATE = 0.1;
    public static final double GENE_POOL_PERCENT = 0.2;
    public static final double REMOVAL_PERCENT = 0.7;
    private static int IMAGE_WIDTH;
    private static int IMAGE_HEIGHT;

    private static double currentFitness = Integer.MAX_VALUE;
    private static double bestFitness = Integer.MAX_VALUE;
    private final List<List<Organism>> gens = new ArrayList<>();
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
    }

    public void start() {
        gens.add(generateOrganisms(generateFirstGen()));
        System.out.println("generated gen 0");
        currentFitness = getBestFitness(0);
        bestFitness = getBestFitness(0);
        gui.updateFitness(0, currentFitness, bestFitness);
        System.out.println("Gen 0 Fitness: " + getBestFitness(0));
        output = getBestImage(0);
        gui.updateImage(output.image());
        currentGen = 1;
        while (currentGen <= NUM_GENS) run();
    }

    public void run() {
        if (currentGen > NUM_GENS) return;
        gens.add((currentGen % 2 == 0 ? 0 : 1), generateOrganisms(generateNextGen(gens.get((currentGen % 2 == 0 ? 1 : 0)))));
        System.out.println("generated gen " + currentGen);
        currentFitness = getBestFitness(currentGen);
        output = getBestImage(currentGen);
        bestFitness = Math.min(bestFitness, currentFitness);
        gui.updateFitness(currentGen, currentFitness, bestFitness);
        currentBest = output;
        System.out.println("Gen " + currentGen + " Fitness: " + currentFitness);
        gui.updateImage(output.image());
        currentGen++;
    }

    /** Generates the next generation from the previous one */
    private List<List<Triangle>> generateNextGen(List<Organism> previousGen) {
        List<Organism> genePool = new ArrayList<>(previousGen);
        genePool.sort(Comparator.comparingDouble(Organism::fitness));

        int genePoolSize = (int) Math.ceil(genePool.size() * GENE_POOL_PERCENT);
        System.out.println("gene pool size: " + genePoolSize);

        for (int i = genePoolSize; i < previousGen.size(); i++) {
            genePool.remove(genePoolSize);
        }

        List<List<Triangle>> result = new ArrayList<>();
        for (int j = 0; j < GEN_SIZE; j++) {
            for (int i = 0; i < (genePool.size()/2 == 0 ? 1 : genePool.size()/2); i++) {
                Organism parentOne = genePool.get(i);
                Organism parentTwo = genePool.get(i + genePool.size()/2);
                List<Triangle> child = new ArrayList<>();
                List<Triangle> parentOneTris = parentOne.triangles();
                List<Triangle> parentTwoTris = parentTwo.triangles();
                double opacityMult = random.nextDouble();
                parentOneTris.forEach(t -> t.setOpacity(opacityMult));
                parentTwoTris.forEach(t -> t.setOpacity(1 - opacityMult));
                System.out.println("parent one size: " + parentOneTris.size());
                System.out.println("parent two size: " + parentTwoTris.size());
                // adding the triangles in order
                for (int k = 0; k < Math.max(parentOneTris.size(), parentTwoTris.size()); k++) {
                    if (k < parentOneTris.size()) child.add(parentOneTris.get(k));
                    if (k < parentTwoTris.size()) child.add(parentTwoTris.get(k));
                }
                // Limiting the size of the child organism
                for (int k = 0; k < child.size() * REMOVAL_PERCENT; k++) {
                    if (random.nextDouble() < (1 - Math.pow(2, -currentGen))) {
                        child.remove(random.nextInt(child.size()));
                    }
                }
                if (random.nextDouble() < MUTATE_RATE)
                    child.add((random.nextDouble() < 0.5)
                            ? parentOne.triangles().get(random.nextInt(parentOne.triangles().size() - 1)).withRandomColor()
                            : parentOne.triangles().get(random.nextInt(parentOne.triangles().size() - 1)).withRandomPoint());
                if (random.nextDouble() < MUTATE_RATE)
                    child.add((random.nextDouble() < 0.5)
                            ? parentTwo.triangles().get(random.nextInt(parentTwo.triangles().size() - 1)).withRandomColor()
                            : parentTwo.triangles().get(random.nextInt(parentTwo.triangles().size() - 1)).withRandomPoint());
                result.add(child);
            }
        }
        System.out.println("result size: " + result.size());
        return result;
    }

    /** Returns the fitness of the best image in a specified generation */
    private double getBestFitness(int gen) {
        return getBestImage(gen).fitness();
    }

    /** Returns the image with the highest fitness in a specified generation */
    public Organism getBestImage(int gen) {
        double fitness = Integer.MAX_VALUE;
        Organism result = getGen(gen).get(0);
        for (int i = 0; i < getGen(gen).size(); i++) {
            if (getGen(gen).get(i).fitness() < fitness) {
                fitness = getGen(gen).get(i).fitness();
                result = getGen(gen).get(i);
            }
        }
        return result;
    }

    private List<Organism> getGen(int gen) {
        return gens.get(gen % 2 == 0 ? 0 : 1);
    }

    /** Returns the fitness of the specified image */
    private double fitness(BufferedImage image) {
        double fitness = 0;
        for (int x = 0; x < image.getWidth() - 1; x++) {
            for (int y = 0; y < image.getHeight() - 1; y++) {
                Color color = new Color(image.getRGB(x, y), true);
                Color targetColor = new Color(targetImage.getRGB(x, y));
                fitness += Math.abs(targetColor.getRed() - color.getRed());
                fitness += Math.abs(targetColor.getGreen() - color.getGreen());
                fitness += Math.abs(targetColor.getBlue() - color.getBlue());
                // fitness += Math.abs(targetColor.getAlpha() - color.getAlpha());
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
    
        // Prioritize rendering speed over quality
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2d.setComposite(AlphaComposite.Src);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
    
        Polygon polygon = new Polygon(new int[3], new int[3], 3); // reuse one Polygon object
    
        for (Triangle t : triangles) {
            polygon.xpoints[0] = t.p1().x;
            polygon.ypoints[0] = t.p1().y;
            polygon.xpoints[1] = t.p2().x;
            polygon.ypoints[1] = t.p2().y;
            polygon.xpoints[2] = t.p3().x;
            polygon.ypoints[2] = t.p3().y;
            polygon.invalidate(); // force recalculation of bounds
    
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) t.a()));
            g2d.setColor(new Color(t.r, t.g, t.b, (int) (t.a * 255)));
            g2d.fillPolygon(polygon);
        }
    
        g2d.dispose();
        System.out.println("generated organism");
        return new Organism(triangles, image, fitness(image));
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
    public record Organism(List<Triangle> triangles, BufferedImage image, double fitness) {}
 
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
