package org.dwcj.component;

/**
 * Interface used to include all common properties of new controls/components that will be added to
 * the engine, but exclude some of the more specific properties not common across every control.
 */
public interface Component {

  /**
   * Returns the control's ID.
   *
   * @return Control's ID
   */
  public String getId();

  /**
   * Assigns the ID of a control.
   *
   * @param elementId Desired ID designation
   * @return The control itself
   */
  public Component setId(String elementId);


  /**
   * Allows user to fetch extraneous, user-included information from the control.
   *
   * @param key Key of the data
   * @return Desired piece of user data
   */
  public Object getUserData(String key);

  /**
   * Allows user to include extraneous information in the control.
   *
   * @param key Key of the data
   * @param data Desired piece of information
   * @return The control itself
   */
  public Component setUserData(String key, Object data);

}
