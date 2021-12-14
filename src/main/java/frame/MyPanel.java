package frame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MyPanel extends JPanel{

    protected List<JComponent> componentsToDraw;

    public MyPanel(){
        componentsToDraw = Collections.synchronizedList(new ArrayList<>());
    }

    public List<JComponent> getComponentsToDraw() {
        return Collections.unmodifiableList(componentsToDraw);
    }
}
