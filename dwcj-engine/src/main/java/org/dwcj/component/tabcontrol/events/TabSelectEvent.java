package org.dwcj.component.tabcontrol.events;

import org.dwcj.component.ComponentEvent;
import org.dwcj.component.tabcontrol.TabControl;

public final class TabSelectEvent implements ComponentEvent {
    private final TabControl control;
    private final int index;
    private final String title;

    public TabSelectEvent(TabControl tabControl, int index, String title) {
        this.control = tabControl;
        this.index = index;
        this.title = title;
    }

    @Override
    public TabControl getControl() {
        return control;
    }

    public int getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return "Event: Tab "+index+" Selected";
    }

}
