package org.dwcj.addons.table.event.selection;

import java.util.Map;
import org.dwcj.addons.table.Table;
import org.dwcj.addons.table.event.item.TableItemEvent;
import org.dwcj.component.element.annotation.EventName;

/**
 * Emitted when a row is deselected.
 *
 * <p>
 * Please note that this event is not triggered when multiple selection mode is active and the
 * selection is made via the header checkbox. In such cases, the {@link TableItemSelectionChange}
 * should be used instead.
 * </p>
 *
 * @param <T> the table item type
 *
 * @see TableItemSelectionChange
 * @see TableItemSelectEvent
 *
 * @author Hyyan Abo Fakher
 * @since 24.00
 */
@EventName("bbj-row-deselected")
public class TableItemDeselectEvent<T> extends TableItemEvent<T> {

  /**
   * Creates a new row deselect event.
   *
   * @param table the table
   * @param eventMap the event map
   */
  public TableItemDeselectEvent(Table<T> table, Map<String, Object> eventMap) {
    super(table, eventMap);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "TableItemDeselectEvent" + super.toString();
  }
}