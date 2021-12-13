package frame;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    private static final String TITLE = "Game Of Live";
    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 1000;
    private static final boolean VISIBILITY = true;
    private static final Component LOCATION_RELATIVE_TO = null;
    private static final boolean ALWAYS_ON_TOP = false;

    public MyFrame() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.setLocationRelativeTo(LOCATION_RELATIVE_TO);
        this.setAlwaysOnTop(ALWAYS_ON_TOP);
    }

    public void setVisible(){
        this.setVisible(VISIBILITY);
    }

}
