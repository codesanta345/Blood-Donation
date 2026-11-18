package ui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

/** Opens a page maximized and scales it to fill the window. */
public final class Screens {

    /** Set to false if you prefer windows to open at their normal small size. */
    public static boolean OPEN_MAXIMIZED = true;

    private Screens() {
    }

    public static void show(Stage stage, Parent page, String title) {
        Region root = (Region) page;

        // The size the page was designed at
        double baseW = Math.max(root.minWidth(-1), root.prefWidth(-1));
        double baseH = Math.max(root.minHeight(-1), root.prefHeight(-1));
        if (baseW <= 0) baseW = 500;
        if (baseH <= 0) baseH = 500;

        // The red bars that span the whole page width
        List<Rectangle> bars = new ArrayList<>();
        for (Node n : root.getChildrenUnmodifiable()) {
            if (n instanceof Rectangle) {
                Rectangle r = (Rectangle) n;
                if (r.getLayoutX() <= 1 && r.getWidth() >= baseW - 2) {
                    bars.add(r);
                }
            }
        }

        Pane holder = new Pane(root);
        holder.setStyle("-fx-background-color: -fx-background;");
        root.setManaged(false);
        root.resize(baseW, baseH);

        Scale scale = new Scale(1, 1, 0, 0);
        root.getTransforms().add(scale);

        final double w0 = baseW;
        final double h0 = baseH;
        Runnable fit = () -> {
            double W = holder.getWidth();
            double H = holder.getHeight();
            if (W <= 0 || H <= 0) {
                return;
            }
            double s = Math.min(W / w0, H / h0);
            scale.setX(s);
            scale.setY(s);
            double offsetX = (W - w0 * s) / 2;
            double offsetY = (H - h0 * s) / 2;
            root.relocate(offsetX, offsetY);
            for (Rectangle bar : bars) {
                bar.setLayoutX(-offsetX / s);
                bar.setWidth(W / s);
            }
        };
        holder.widthProperty().addListener((obs, a, b) -> fit.run());
        holder.heightProperty().addListener((obs, a, b) -> fit.run());

        stage.setScene(new Scene(holder, baseW, baseH));
        if (title != null) {
            stage.setTitle(title);
        }
        stage.setMinWidth(400);
        stage.setMinHeight(400);
        stage.setMaximized(OPEN_MAXIMIZED);
        stage.show();
        fit.run();
    }
}
