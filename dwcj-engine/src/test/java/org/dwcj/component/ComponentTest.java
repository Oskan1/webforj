package org.dwcj.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.dwcj.component.window.Window;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ComponentTest {
  ComponentMock component;

  @BeforeEach
  void setUp() {
    component = new ComponentMock();
  }

  @Test
  @DisplayName("Test getComponentId")
  void testGetComponentId() {
    String componentId = component.getComponentId();
    assertNotNull(componentId);
    assertNotEquals("", componentId);
  }

  @Test
  @DisplayName("Test setUserData and getUserData")
  void testSetAndGetUserData() {
    String key = "testKey";
    String value = "testValue";

    component.setUserData(key, value);
    Object retrievedValue = component.getUserData(key);

    assertNotNull(retrievedValue);
    assertEquals(value, retrievedValue);
  }

  @Test
  @DisplayName("Test destroy")
  void testDestroy() {
    assertFalse(component.isDestroyed());

    component.destroy();

    assertTrue(component.isDestroyed());
    assertNull(component.getComponentId());
    assertFalse(component.isAttached());
    assertNull(component.getWindow());
  }

  @Test
  @DisplayName("Test isAttached")
  void testIsAttached() throws IllegalAccessException {
    assertFalse(component.isAttached());

    component.create(mock(Window.class));
    assertTrue(component.isAttached());
  }

  @Test
  @DisplayName("Test getWindow")
  void testGetWindow() throws IllegalAccessException {
    assertNull(component.getWindow());

    component.create(mock(Window.class));
    assertNotNull(component.getWindow());
  }

  @Test
  @DisplayName("If a component is destroyed, it can be created again")
  void testComponentCreateThrowsIllegalAccessExceptionIfDestroyed() throws IllegalAccessException {
    Window window = mock(Window.class);

    String id = component.getComponentId();
    component.create(window);
    component.destroy();
    component.create(window);

    assertNotEquals(id, component.getComponentId());
  }

  @Test
  @DisplayName("Test component lifecycle methods")
  void testComponentLifecycleMethods() throws IllegalAccessException {
    ComponentMock spy = spy(ComponentMock.class);
    Window window = mock(Window.class);

    spy.create(window);

    verify(spy).onCreate(window);
    verify(spy).onAttach();

    spy.destroy();
    verify(spy).onDestroy();
  }
}