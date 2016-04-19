package se.orjehag.antecedent.gui;

import se.orjehag.antecedent.placable.Placeable;

import java.awt.*;

/**
 * Created by erik on 19/04/16.
 */
public interface CompDropTarget {
    Rectangle getCompDropArea();
    void compDrop(Placeable placable);
}
