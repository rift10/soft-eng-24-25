package work;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SequentialWork implements Work {

    private final List<Work> works;

    public SequentialWork(Work... works) {
        this.works = Arrays.stream(works).toList();
    }

    @Override
    public double generateEndTime() {
        double result = works.stream().collect(Collectors.summingDouble(work -> work.generateEndTime()));
        return (result > 0 ? result : 0);
    }
}
