package se.orjehag.antecedent;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

import javax.swing.JComponent;

class DraggableComponent extends JComponent implements DragGestureListener, DragSourceListener {
  DragSource dragSource;

  public DraggableComponent() {
    dragSource = new DragSource();
    dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
  }

  public void dragGestureRecognized(DragGestureEvent evt) {
    Transferable t = new StringSelection("aString");
    dragSource.startDrag(evt, DragSource.DefaultCopyDrop, t, this);
  }

  public void dragEnter(DragSourceDragEvent evt) {
    System.out.println("enters");
  }

  public void dragOver(DragSourceDragEvent evt) {

    System.out.println("over");
  }

  public void dragExit(DragSourceEvent evt) {
    System.out.println("leaves");
  }

  public void dropActionChanged(DragSourceDragEvent evt) {
    System.out.println("changes the drag action between copy or move");
  }

  public void dragDropEnd(DragSourceDropEvent evt) {
    System.out.println("finishes or cancels the drag operation");
  }

  @Override protected void paintComponent(final Graphics g) {
    super.paintComponent(g);
    final Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.RED);
    g2d.fillRect(0, 0, 100, 100);
  }
}