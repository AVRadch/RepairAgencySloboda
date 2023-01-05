package com.my.repairagency007.model.entity;

public enum CopletionStatus {

    NOT_STARTED, IN_PROGRESS, COMPLETED;

    public static CompletionStatus getCompletionStatus(Request request) {
        int completionStatusId = request.getCompletionStatusId();
        return CompletionStatus.values()[--completionStatusId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
