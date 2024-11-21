package work;

import java.util.Arrays;
import java.util.List;

public class ParallelWork extends Work {

    private final List<Work> works;

    public ParallelWork(Work... works) {
        super(0, 0);
        this.works = Arrays.stream(works).toList();
    }

    @Override
    public double generateEndTime() {
        double result = works.stream().mapToDouble(work -> work.generateEndTime()).max().getAsDouble();
        return (result > 0 ? result : 0);
    }
}
