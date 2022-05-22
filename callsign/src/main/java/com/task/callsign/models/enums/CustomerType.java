package com.task.callsign.models.enums;

/**
 * Types of Customers
 */
public enum CustomerType {
  VIP(TicketPriority.HIGH),
  LOYAL(TicketPriority.MEDIUM),
  NEW(TicketPriority.LOW);

  final TicketPriority ticketPriority;

  CustomerType(TicketPriority ticketPriority) {
    this.ticketPriority = ticketPriority;
  }

  public TicketPriority getTicketPriority() {
    return this.ticketPriority;
  }
}
