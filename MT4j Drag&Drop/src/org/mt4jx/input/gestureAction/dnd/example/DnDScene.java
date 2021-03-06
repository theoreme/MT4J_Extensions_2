package org.mt4jx.input.gestureAction.dnd.example;

import org.mt4j.MTApplication;
import org.mt4j.components.interfaces.IMTController;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.logging.ILogger;
import org.mt4j.util.logging.MTLoggerFactory;
import org.mt4j.util.math.Vector3D;
import org.mt4jx.input.gestureAction.dnd.DragAndDropAction;

public class DnDScene extends AbstractScene {

    /** The Constant logger. */
    private static final ILogger LOG = MTLoggerFactory.getLogger(DnDScene.class.getName());

    static {
        LOG.setLevel(ILogger.WARN);
    }

    public DnDScene(MTApplication mtApplication, String name) {
        super(mtApplication, name);

        // Disable frustum culling for this scene - optional
        getCanvas().setFrustumCulling(false);

        final IFont font = FontManager.getInstance().createFont(mtApplication, "arial.ttf",
                30, // Font size
                new MTColor(0, 0, 0, 255), // Font fill color
                new MTColor(0, 0, 0, 255));

        // Set the background color
        setClearColor(new MTColor(146, 150, 100, 255));

        // ///FRAMELABEL
        final MTTextArea framerateLabel = new MTTextArea(getMTApplication(), font);
        framerateLabel.setController(new IMTController() {
            private int cnt = 0;

            private double sum = 0;

            @Override
            public void update(long millis) {
                sum += (int) getMTApplication().frameRate;
                cnt++;
                if ((cnt % 10) == 0) {
                    final int avrg = (int) Math.round(sum / cnt);
                    framerateLabel.setText("Frames: " + avrg);
                    cnt = 0;
                    sum = 0;
                }
            }
        });
        framerateLabel.setPositionGlobal(new Vector3D(200, 600));
        getCanvas().addChild(framerateLabel);
        // ///FRAMELABEL

        final MyDragAndDropTarget target1 = new MyDragAndDropTarget(mtApplication, font); // Font outline color
        target1.setFillColor(new MTColor(0, 255, 0, 255));
        target1.setStrokeColor(new MTColor(0, 255, 0, 255));
        target1.setPositionGlobal(new Vector3D(mtApplication.width / 1.5f, mtApplication.height / 2f));
        target1.setName("D&Dtarget1");
        target1.setText();

        final MyDragAndDropTarget target2 = new MyDragAndDropTarget(mtApplication, font); // Font outline color
        target2.setFillColor(new MTColor(0, 0, 255, 255));
        target2.setStrokeColor(new MTColor(0, 0, 255, 255));
        target2.setPositionGlobal(new Vector3D(mtApplication.width / 4f, mtApplication.height / 2f));
        target2.setName("D&Dtarget2");
        target2.setText();

        // Create a Drag&Drop-Component
        final MTEllipse bright = new MTEllipse(mtApplication, new Vector3D(100, 100), 50, 50);
        // add drag and drop action to detect d&d of this component
        bright.addGestureListener(DragProcessor.class, new DragAndDropAction());
        bright.setStrokeColor(new MTColor(0, 0, 0, 127));
        bright.setFillColor(new MTColor(255, 255, 255, 200));
        bright.setName("brightCircle");

        // Create a Drag&Drop-Component
        final MTEllipse dark = new MTEllipse(mtApplication, new Vector3D(300, 100), 50, 50);
        dark.addGestureListener(TapAndHoldProcessor.class, new IGestureEventListener() {
            @Override
            public boolean processGestureEvent(MTGestureEvent ge) {
                LOG.debug(ge);
                return false;
            }
        });
        // add drag and drop action to detect d&d of this component
        dark.addGestureListener(DragProcessor.class, new DragAndDropAction());
        dark.setFillColor(new MTColor(0, 0, 0, 150));
        dark.setName("darkCircle");

        // add elements
        getCanvas().addChild(target1);
        getCanvas().addChild(target2);
        getCanvas().addChild(bright);
        getCanvas().addChild(dark);
    }

    @Override
    public void init() {
    }

    @Override
    public void shutDown() {
    }
}