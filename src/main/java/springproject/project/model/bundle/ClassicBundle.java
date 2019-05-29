package springproject.project.model.bundle;

import java.util.Arrays;
import java.util.List;

public class ClassicBundle extends ABundle {
    private List<String> filters = Arrays.asList("sepia", "blur", "grayscale");

    private int files = 5;

    @Override
    public String getName() {
        return "classic";
    }

    @Override
    public boolean asFilterAccess(String filter) {
        return filters.contains(filter);
    }

    @Override
    public int numberOfFile() {
        return files;
    }
}
