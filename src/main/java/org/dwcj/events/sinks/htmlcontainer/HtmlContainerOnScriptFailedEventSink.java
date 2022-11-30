package org.dwcj.events.sinks.htmlcontainer;

import com.basis.bbj.proxies.event.BBjScriptFailedEvent;
import com.basis.bbj.proxies.sysgui.BBjControl;
import org.dwcj.Environment;
import org.dwcj.bridge.ControlAccessor;
import org.dwcj.controls.HtmlContainer;
import org.dwcj.events.htmlcontainer.HtmlContainerOnScriptFailedEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;



public class HtmlContainerOnScriptFailedEventSink {
    
    private ArrayList<Consumer<HtmlContainerOnScriptFailedEvent>> targets;
    private final HtmlContainer htmlContainer;

    @SuppressWarnings({"static-access"})
    public HtmlContainerOnScriptFailedEventSink(HtmlContainer container) {

        this.targets = new ArrayList<>();
        this.htmlContainer = container;

        BBjControl bbjctrl = null;
        try {
            bbjctrl = ControlAccessor.getDefault().getBBjControl(container);
            bbjctrl.setCallback(Environment.getInstance().getBBjAPI().ON_SCRIPT_FAILED,
                    Environment.getInstance().getDwcjHelper().getEventProxy(this, "scriptFailedEvent"),
                    "onEvent");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scriptFailedEvent(BBjScriptFailedEvent ev) { // NOSONAR
        HtmlContainerOnScriptFailedEvent dwcEv = new HtmlContainerOnScriptFailedEvent(this.htmlContainer);
        Iterator<Consumer<HtmlContainerOnScriptFailedEvent>> it = targets.iterator();
        while (it.hasNext())
            it.next().accept(dwcEv);
    }
    
    public void addCallback(Consumer<HtmlContainerOnScriptFailedEvent> callback) {
        targets.add(callback);
    }

}
