package frame;

import main.IntWrapper;

import javax.swing.*;
import java.util.*;

public abstract class MyPanel extends JPanel {

    private final Collection<MyPanel> innerMyPanels;
    private final Collection<JComponent> componentsToDraw;
    private final Map<JLabel, Object> labelCouples;

    public MyPanel() {
        this.innerMyPanels = Collections.synchronizedSet(new HashSet<>());
        this.componentsToDraw = Collections.synchronizedSet(new HashSet<>());
        this.labelCouples = Collections.synchronizedMap(new HashMap<>());
    }

    public final Collection<JComponent> getComponentsToDraw() {
        this.innerMyPanels.forEach(myPanel -> componentsToDraw.addAll(myPanel.getComponentsToDraw()));
        return Collections.unmodifiableCollection(componentsToDraw);
    }

    public final Map<JLabel, ?> getLabelCouples() {
        this.innerMyPanels.forEach(myPanel -> labelCouples.putAll(myPanel.getLabelCouples()));
        return Collections.unmodifiableMap(labelCouples);
    }

    public final void initAll() {
        this.init();
        this.innerMyPanels.forEach(MyPanel::initAll);
    }

    protected abstract void init();

    protected final void addInnerMyPanel(MyPanel myPanel) {
        this.innerMyPanels.add(myPanel);
    }

    protected final void addComponentToDraw(JComponent jComponent) {
        this.componentsToDraw.add(jComponent);
    }

    protected final void addLabelCouple(JLabel jLabel, Object coupledObject) {
        this.labelCouples.put(jLabel, coupledObject);
    }


}
