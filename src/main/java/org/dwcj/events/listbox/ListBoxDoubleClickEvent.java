package org.dwcj.events.listbox;

import org.dwcj.controls.ListBox;
import org.dwcj.events.IDwcEvent;

import java.util.ArrayList;


public class ListBoxDoubleClickEvent implements IDwcEvent{
    
    private final ListBox control;

    private ArrayList<Object> keys = new ArrayList<>(); 


    public ListBoxDoubleClickEvent(ListBox clistBox) {
        this.control = clistBox;
        this.keys.add(control.getSelectedItem().getKey());
    }

    public void addKey(Object key) { keys.add(key); }
    public ArrayList<Object> getKeys() { return keys; }


    @Override
    public ListBox getControl() { return control; }
}
