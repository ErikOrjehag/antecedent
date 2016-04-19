package se.orjehag.antecedent;

import java.awt.event.MouseEvent;

public interface ComponentGrabListener
{
    void grabComponent(ComponentDrawerItem item);
    void mousePressed(MouseEvent e);
    void mouseDragged(MouseEvent e);
}
