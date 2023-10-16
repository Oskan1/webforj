package org.dwcj.component.htmlcontainer.event;

import org.dwcj.component.ControlEvent;
import org.dwcj.component.htmlcontainer.HtmlContainer;

public class HtmlContainerScriptFailEvent implements ControlEvent {

  private final HtmlContainer control;

  public HtmlContainerScriptFailEvent(HtmlContainer cHtmlContainer) {
    this.control = cHtmlContainer;
  }

  @Override
  public HtmlContainer getControl() {
    return control;
  }

  public String getUrl() {
    return this.control.getUrl();
  }

  public String toString() {
    return "Event: HtmlContainer Script Failed";
  }

}
