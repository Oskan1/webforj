package org.dwcj.component.listbox;

import com.basis.bbj.proxies.sysgui.BBjListBox;
import com.basis.bbj.proxies.sysgui.BBjWindow;
import com.basis.startup.type.BBjException;

import org.dwcj.Environment;
import org.dwcj.annotation.ExcludeFromJacocoGeneratedReport;
import org.dwcj.bridge.WindowAccessor;
import org.dwcj.component.listbox.event.ListBoxDoubleClickEvent;
import org.dwcj.component.listbox.event.ListBoxSelectEvent;
import org.dwcj.component.listbox.sink.ListBoxDoubleClickEventSink;
import org.dwcj.component.listbox.sink.ListBoxSelectEventSink;
import org.dwcj.component.texts.Label;
import org.dwcj.component.window.AbstractWindow;
import org.dwcj.concern.HasEnable;
import org.dwcj.concern.HasFocus;
import org.dwcj.concern.HasHorizontalAlignment;
import org.dwcj.concern.HasMouseWheelCondition;
import org.dwcj.concern.HasReadOnly;
import org.dwcj.concern.HasScrollability;
import org.dwcj.concern.HasTabTraversal;
import org.dwcj.utilities.BBjFunctionalityHelper;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Consumer;

public final class ListBox extends AbstractListBox
    implements HasScrollability, HasEnable, HasReadOnly, HasFocus, HasMouseWheelCondition,
    HasTabTraversal, HasHorizontalAlignment<ListBox> {

  private BBjListBox bbjListBox;

  public enum Expanse {
    LARGE, MEDIUM, SMALL, XLARGE, XSMALL
  }

  private ArrayList<Consumer<ListBoxSelectEvent>> selectEvents = new ArrayList<>();
  private ListBoxSelectEventSink selectEventSink;
  private ArrayList<Consumer<ListBoxDoubleClickEvent>> doubleClickEvents = new ArrayList<>();
  private ListBoxDoubleClickEventSink doubleClickEventSink;
  Boolean multipleSelection = false;
  private SimpleEntry<Integer, String> textAt = null;


  public ListBox() {
    this.horizontalScrollBarPosition = 0;
    this.verticalScrollBarPosition = 0;
    this.readOnly = false;
    this.mouseWheelCondition = MouseWheelCondition.DEFAULT;
    this.tabTraversable = true;
    setComponentDefaultHorizontalAlignment(Alignment.LEFT);
  }

  @Override
  protected void create(AbstractWindow p) {
    try {
      BBjWindow w = WindowAccessor.getDefault().getBBjWindow(p);
      byte[] flags =
          BBjFunctionalityHelper.buildStandardCreationFlags(this.isVisible(), this.isEnabled());
      control = w.addListBox(w.getAvailableControlID(), BASISNUMBER_1, BASISNUMBER_1,
          BASISNUMBER_250, BASISNUMBER_250, "", flags);
      control.setAttribute("max-row-count", "25");
      control.setAttribute("open-width", "2500");
      control.setAttribute("button-height", "auto");
      bbjListBox = (BBjListBox) control;
      populate();
      catchUp();
    } catch (Exception e) {
      Environment.logError(e);
    }
  }

  /**
   * Add an item into the listBox
   *
   * @param key the item key
   * @param item the item's value
   * @return the control itself
   */
  public ListBox addItem(Object key, String item) {
    this.values.put(key, item);
    data2.add(values.get(key));
    populate();
    return this;
  }

  public ListBox insertItemAt(Object key, String item, Integer index) {
    this.values.put(key, item);
    data2.add(index, values.get(key));
    populate();
    return this;
  }

  public ListBox addItems(Map<Object, String> items) {
    this.values.putAll(items);
    Iterator<Object> it = items.keySet().iterator();
    while (it.hasNext()) {
      data2.add(values.get(it.next()));
    }
    populate();
    return this;
  }

  public ListBox insertItemsAt(Map<Object, String> items, Integer index) {
    this.values.putAll(items);
    Iterator<Object> it = items.keySet().iterator();
    Integer counter = 0;
    while (it.hasNext()) {
      data2.add(index + counter++, values.get(it.next()));
    }
    populate();
    return this;
  }

  @SuppressWarnings("unchecked")
  protected void populate() {
    if (this.control != null) {
      try {
        BBjListBox cb = (BBjListBox) control;
        cb.removeAllItems();
        cb.insertItems(0, data2);
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
  }



  public ListBox deselectAll() {
    if (this.control != null) {
      try {
        bbjListBox.deselectAll();
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    return this;
  }

  public ListBox deselectIndex(int index) {
    if (this.control != null) {
      try {
        bbjListBox.deselectIndex(index);
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    return this;
  }

  /**
   * Returns a map of all of the items within the ListBox
   *
   * @return all values in the listBox
   */
  public Map<Object, String> getAllItems() {
    if (this.control != null) {
      return this.values;
    }
    return Collections.emptyMap();
  }

  /**
   * Returns a single String representing the item at the given key.
   *
   * @param key - Object representing the key in the map
   * @return String item at the given key
   */
  public String getItem(Object key) {
    if (this.control != null) {
      return values.get(key);
    }
    return null;
  }

  public String getItemAt(Integer idx) {
    if (this.control != null) {
      try {
        BBjListBox cb = (BBjListBox) control;
        return cb.getItemAt(idx);
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    return null;
  }


  public Integer getItemCount() {
    if (this.control != null) {
      try {
        bbjListBox.getItemCount();
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    return null;
  }

  /**
   * Returns true or false based on the ListBox allows selection of multiple items
   *
   * @return boolean
   */
  public Boolean isMultipleSelection() {
    if (this.control != null) {
      try {
        return bbjListBox.getMultipleSelection();
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    return false;
  }

  /**
   * returns the currently selected item, implemented for one-to-one value maps
   *
   * @return selected entry
   */
  public SimpleEntry<Object, String> getSelectedItem() {
    if (this.control != null) {
      try {
        String value = bbjListBox.getSelectedItem();
        return new SimpleEntry<>(getEntryByValue(value), value);
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    return null;
  }

  public SimpleEntry<Object, String> getEntryByValue(String value) {
    if (this.control != null) {
      Map<Object, String> map = this.values;
      for (Map.Entry<Object, String> entry : map.entrySet()) {
        if (Objects.equals(value, entry.getValue())) {
          return new SimpleEntry<>(entry.getKey(), value);
        }
      }
    }
    return null;
  }

  /**
   * Returns a map of the items that have been selected in the ListBox
   *
   * @param N/A
   * @return Map of the selected items
   */
  public Map<Object, String> getSelectedItems() {
    if (this.control != null) {
      Map<Object, String> map = new HashMap<>();
      try {
        Object[] indices = bbjListBox.getSelectedIndices().toArray();
        for (Object index : indices) {
          String value = bbjListBox.getItemAt((Integer) index);
          SimpleEntry<Object, String> entry = getEntryByValue(value);
          if (entry != null) {
            Object key = entry.getKey();
            if (key != null) {
              map.put(key, value);
            }
          }
        }
      } catch (BBjException e) {
        Environment.logError(e);
      }
      return map;
    }
    return Collections.emptyMap();
  }



  /**
   * Allows you to pass in a map of objects which will replace the objects currently in the ListBox
   *
   * @param values - A map with <Object, String> pairs.
   * @return ListBox
   */
  public ListBox setItems(Map<Object, String> values) {
    this.values = values;
    data2.clear();
    Iterator<Object> it = values.keySet().iterator();
    while (it.hasNext()) {
      data2.add(values.get(it.next()));
    }
    populate();
    return this;
  }



  /**
   * Function that takes another function as parameter which allows functionality to be written when
   * an item is selected within the box
   *
   * @param callback - Function written that implements behavior when an item is selected
   * @return ListBox
   */
  public ListBox onSelect(Consumer<ListBoxSelectEvent> callback) {
    if (this.control != null) {
      if (this.selectEventSink == null) {
        this.selectEventSink = new ListBoxSelectEventSink(this);
      }
      this.selectEventSink.addCallback(callback);
    } else {
      this.selectEvents.add(callback);
    }
    return this;
  }

  public ListBox onDoubleClick(Consumer<ListBoxDoubleClickEvent> callback) {
    if (this.control != null) {
      if (this.doubleClickEventSink == null) {
        this.doubleClickEventSink = new ListBoxDoubleClickEventSink(this);
      }
      this.doubleClickEventSink.addCallback(callback);
    } else {
      this.doubleClickEvents.add(callback);
    }
    return this;
  }

  /**
   * Sets whether or not it is possible to select multiple items within the box
   *
   * @param bool - True or false whether or not to allow multiple selection
   * @return boolean
   */

  public ListBox removeAllItems() {
    if (this.control != null) {
      try {
        ((BBjListBox) this.control).removeAllItems();
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    return this;
  }

  public ListBox removeItemAt(Integer idx) {
    if (this.control != null) {
      try {
        ((BBjListBox) this.control).removeItemAt(idx);
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    return this;
  }

  public ListBox selectIndex(Integer idx) {
    if (this.control != null) {
      try {
        ((BBjListBox) this.control).selectIndex(idx);
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    return this;
  }


  public ListBox setMultipleSelection(Boolean multipleSelection) {
    if (this.control != null) {
      try {
        bbjListBox.setMultipleSelection(multipleSelection);
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    this.multipleSelection = multipleSelection;
    return this;
  }

  public ListBox setTextAt(Integer idx, String text) {
    this.textAt = new SimpleEntry<>(idx, text);
    if (this.control != null) {
      try {
        ((BBjListBox) this.control).setTextAt(idx, text);
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    return this;
  }



  @Override
  public ListBox setText(String text) {
    super.setText(text);
    return this;
  }

  @Override
  public ListBox setVisible(Boolean visible) {
    super.setVisible(visible);
    return this;
  }

  @Override
  public ListBox setEnabled(boolean enabled) {
    super.setComponentEnabled(enabled);
    return this;
  }

  @Override
  public boolean isEnabled() {
    return super.isComponentEnabled();
  }

  @Override
  public ListBox setTooltipText(String text) {
    super.setTooltipText(text);
    return this;
  }

  @Override
  public ListBox setAttribute(String attribute, String value) {
    super.setAttribute(attribute, value);
    return this;
  }

  @Override
  public ListBox setStyle(String property, String value) {
    super.setStyle(property, value);
    return this;
  }

  @Override
  public ListBox addClassName(String selector) {
    super.addClassName(selector);
    return this;
  }

  @Override
  public ListBox removeClassName(String selector) {
    super.removeClassName(selector);
    return this;
  }



  public ListBox setExpanse(Expanse expanse) {
    super.setControlExpanse(expanse);
    return this;
  }



  @Override
  public Integer getHorizontalScrollBarHeight() {
    if (this.control != null) {
      ((BBjListBox) this.control).getHorizontalScrollBarHeight();
    }
    return 0;

  }

  @Override
  public Integer getHorizontalScrollBarPosition() {
    if (this.control != null) {
      ((BBjListBox) this.control).getHorizontalScrollBarPosition();
    }
    return this.horizontalScrollBarPosition;

  }

  @Override
  public Integer getHorizontalScrollBarWidth() {
    if (this.control != null) {
      ((BBjListBox) this.control).getHorizontalScrollBarWidth();
    }
    return 0;

  }

  @Override
  public Integer getVerticalScrollBarHeight() {
    if (this.control != null) {
      ((BBjListBox) this.control).getVerticalScrollBarHeight();
    }
    return 0;

  }

  @Override
  public Integer getVerticalScrollBarPosition() {
    if (this.control != null) {
      ((BBjListBox) this.control).getVerticalScrollBarPosition();
    }
    return this.verticalScrollBarPosition;

  }

  @Override
  public Integer getVerticalScrollBarWidth() {
    if (this.control != null) {
      ((BBjListBox) this.control).getVerticalScrollBarWidth();
    }
    return 0;

  }

  @Override
  public Boolean isHorizontalScrollBarVisible() {
    if (this.control != null) {
      ((BBjListBox) this.control).isHorizontalScrollBarVisible();
    }
    return false;

  }

  @Override
  public Boolean isVerticalScrollBarVisible() {
    if (this.control != null) {
      ((BBjListBox) this.control).isVerticalScrollBarVisible();
    }
    return false;

  }

  @Override
  public ListBox setHorizontalScrollBarPosition(Integer position) {
    if (this.control != null) {
      ((BBjListBox) this.control).setHorizontalScrollBarPosition(position);
    }
    this.horizontalScrollBarPosition = position;
    return this;
  }

  @Override
  public ListBox setVerticalScrollBarPosition(Integer position) {
    if (this.control != null) {
      ((BBjListBox) this.control).setVerticalScrollBarPosition(position);
    }
    this.verticalScrollBarPosition = position;
    return this;
  }



  @Override
  public Boolean isReadOnly() {
    if (this.control != null) {
      try {
        return !((BBjListBox) this.control).isEditable();
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    return this.readOnly;
  }

  @Override
  public ListBox setReadOnly(Boolean editable) {
    if (this.control != null) {
      try {
        ((BBjListBox) this.control).setEditable(!editable);
      } catch (BBjException e) {
        Environment.logError(e);
      }
      return this;
    }
    this.readOnly = editable;
    return this;
  }


  @Override
  public ListBox focus() {
    super.focusComponent();
    return this;
  }


  @Override
  public MouseWheelCondition getScrollWheelBehavior() {
    return this.mouseWheelCondition;
  }

  @Override
  public ListBox setScrollWheelBehavior(MouseWheelCondition condition) {
    if (this.control != null) {
      try {
        ((BBjListBox) this.control).setScrollWheelBehavior(condition.mouseWheelEnabledCondition);
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    this.mouseWheelCondition = condition;
    return this;
  }


  @Override
  public Boolean isTabTraversable() {
    if (this.control != null) {
      try {
        return ((BBjListBox) this.control).isTabTraversable();
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    return this.tabTraversable;
  }

  @Override
  public ListBox setTabTraversable(Boolean traversable) {
    if (this.control != null) {
      try {
        ((BBjListBox) this.control).setTabTraversable(traversable);
      } catch (BBjException e) {
        Environment.logError(e);
      }
    }
    this.tabTraversable = traversable;
    return this;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  @ExcludeFromJacocoGeneratedReport
  public ListBox setHorizontalAlignment(Alignment alignment) {
    setComponentHorizontalAlignment(alignment);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @ExcludeFromJacocoGeneratedReport
  @Override
  public Alignment getHorizontalAlignment() {
    return getComponentHorizontalAlignment();
  }



  @Override
  @SuppressWarnings("java:S3776") // tolerate cognitive complexity for now, it's just a batch list
                                  // of checks
  protected void catchUp() throws IllegalAccessException {

    if (Boolean.TRUE.equals(this.getCaughtUp())) {
      throw new IllegalAccessException("catchUp cannot be called twice");
    }
    super.catchUp();

    if (!this.selectEvents.isEmpty()) {
      this.selectEventSink = new ListBoxSelectEventSink(this);
      while (!this.selectEvents.isEmpty()) {
        this.selectEventSink.addCallback(this.selectEvents.remove(0));
      }
    }

    if (!this.doubleClickEvents.isEmpty()) {
      this.doubleClickEventSink = new ListBoxDoubleClickEventSink(this);
      while (!this.doubleClickEvents.isEmpty()) {
        this.doubleClickEventSink.addCallback(this.doubleClickEvents.remove(0));
      }
    }

    if (Boolean.TRUE.equals(this.multipleSelection)) {
      this.setMultipleSelection(this.multipleSelection);
    }

    if (this.textAt != null) {
      this.setTextAt(this.textAt.getKey(), this.textAt.getValue());
    }

    if (this.horizontalScrollBarPosition != 0) {
      this.setHorizontalScrollBarPosition(this.horizontalScrollBarPosition);
    }

    if (this.verticalScrollBarPosition != 0) {
      this.setVerticalScrollBarPosition(this.horizontalScrollBarPosition);
    }

    if (Boolean.TRUE.equals(this.readOnly)) {
      this.setReadOnly(this.readOnly);
    }

    if (this.mouseWheelCondition != MouseWheelCondition.DEFAULT) {
      this.setScrollWheelBehavior(this.mouseWheelCondition);
    }

    if (Boolean.FALSE.equals(this.tabTraversable)) {
      this.setTabTraversable(this.tabTraversable);
    }
  }


}
