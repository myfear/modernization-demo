package com.example.employee.model.event;

import java.time.LocalDateTime;

public class AuditEvent {
    private final String eventId;
    private final LocalDateTime timestamp;
    private final String userId;
    private final String eventType;
    
    public AuditEvent(String eventId, LocalDateTime timestamp, String userId, String eventType) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.userId = userId;
        this.eventType = eventType;
    }
    
    public String getEventId() { return eventId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getUserId() { return userId; }
    public String getEventType() { return eventType; }
}

// Made with Bob
