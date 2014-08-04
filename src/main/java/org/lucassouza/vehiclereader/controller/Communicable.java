package org.lucassouza.vehiclereader.controller;

import org.lucassouza.vehiclereader.type.ResourceType;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public interface Communicable {
  void informAmount(ResourceType resourceType, Integer amount);
  void informIncrement(ResourceType resourceType);
}
