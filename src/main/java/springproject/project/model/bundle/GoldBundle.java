package springproject.project.model.bundle;

import javax.persistence.DiscriminatorValue;
import java.util.Arrays;
import java.util.List;

public class GoldBundle extends ABundle {
    private List<String> filters = Arrays.asList("sepia", "blur", "grayscale", "invert", "pixelize");

    private int files = 10;

    @Override
    public String getName() {
        return "gold";
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