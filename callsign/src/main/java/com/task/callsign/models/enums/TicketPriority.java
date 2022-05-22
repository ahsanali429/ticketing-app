package com.task.callsign.models.enums;

/**
 * Priorities of tickets
 */

public enum TicketPriority {
    HIGH(1),
    MEDIUM(2),
    LOW(3);

    private final int priority;

    TicketPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}

