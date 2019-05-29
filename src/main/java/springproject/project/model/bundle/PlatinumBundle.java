package springproject.project.model.bundle;

public class PlatinumBundle extends ABundle {

    private int files = 1000000;

    @Override
    public String getName() {
        return "platinum";
    }

    @Override
    public boolean asFilterAccess(String filter) {
        return true;
    }

    @Override
    public int numberOfFile() {
        return files;
    }
}
