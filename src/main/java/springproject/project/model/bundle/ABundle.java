package springproject.project.model.bundle;

public abstract class ABundle {
    abstract boolean asFilterAccess(String filter);

    public abstract int numberOfFile();

    public abstract String getName();

    public static ABundle create(String bundleName) {
        switch (bundleName) {
            case "classic":
                return new ClassicBundle();
            case "gold":
                return new GoldBundle();
            case "platinum":
                return new PlatinumBundle();
             default:
                 return null;
        }
    }
}
