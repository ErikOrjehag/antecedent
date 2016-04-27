package se.orjehag.antecedent.gui;

import se.orjehag.antecedent.placeable.Placeable;

import java.awt.*;

/**
 * Classes that want to have placeables dropped on them can
 * implement this interface.
 */
public interface CompDropTarget {
    Rectangle getCompDropArea();
    void compDrop(Placeable placeable);
}
