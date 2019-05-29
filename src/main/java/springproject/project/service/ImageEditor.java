package springproject.project.service;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinPluginLoader;
import org.springframework.stereotype.Service;

@Service("imageEditor")
public class ImageEditor {
    public void apply(String path, String filter, int value) {
        MarvinImage image = MarvinImageIO.loadImage(path);
        MarvinImagePlugin imagePlugin;

        switch (filter) {
            case "sepia":
                imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.sepia.jar");
                imagePlugin.setAttribute("hsIntensidade", value);
                break;
            case "blur":
                imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.blur.gaussianBlur.jar");
                break;
            case "grayscale":
                imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.grayScale.jar");
                break;
            case "invert":
                imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.invert.jar");
                break;
            case "pixelize":
                imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.blur.pixelize.jar");
                break;
            default:
                return;
        }
        this.applyPlugin(path, image, imagePlugin);
    }

    private void applyPlugin(String path, MarvinImage image, MarvinImagePlugin plugin) {
        plugin.process(image, image);
        image.update();

        MarvinImageIO.saveImage(image, path);
    }

}
