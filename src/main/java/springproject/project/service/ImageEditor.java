package springproject.project.service;

import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.image.MarvinImageMask;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinPluginLoader;
import org.springframework.stereotype.Service;

@Service("imageEditor")
public class ImageEditor {
    public void apply(String path, String filter, int value) {
        switch (filter) {
            case "sepia":
                sepiaAction(path, value);
                break;
            case "blur":
                blurAction(path, value);
                break;
            case "grayscale":
                grayscaleAction(path);
                break;
            case "invert":
                invertAction(path, value);
                break;
            case "pixelize":
                pixelizeAction(path, value);
                break;
        }
    }

    private void invertAction(String path, int value) {
        MarvinImage image = MarvinImageIO.loadImage(path);
        MarvinImagePlugin imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.invert.jar");

        imagePlugin.process(image, image);
        image.update();

        MarvinImageIO.saveImage(image, path);
    }

    private void pixelizeAction(String path, int value) {
        MarvinImage image = MarvinImageIO.loadImage(path);
        MarvinImagePlugin imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.blur.pixelize.jar");

        imagePlugin.process(image, image);
        image.update();

        MarvinImageIO.saveImage(image, path);
    }

    private void blurAction(String path, int value) {
        MarvinImage image = MarvinImageIO.loadImage(path);
        MarvinImagePlugin imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.blur.gaussianBlur.jar");

        imagePlugin.process(image, image);
        image.update();

        MarvinImageIO.saveImage(image, path);
    }

    private void grayscaleAction(String path) {
        MarvinImage image = MarvinImageIO.loadImage(path);
        MarvinImagePlugin imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.grayScale.jar");

        imagePlugin.process(image, image);
        image.update();

        MarvinImageIO.saveImage(image, path);
    }

    private void sepiaAction(String path, int value) {
        MarvinImage image = MarvinImageIO.loadImage(path);
        MarvinImagePlugin imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.sepia.jar");

        imagePlugin.setAttribute("hsIntensidade", value);
        imagePlugin.process(image, image);
        image.update();

        MarvinImageIO.saveImage(image, path);
    }

}
