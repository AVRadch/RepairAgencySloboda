package com.my.repairagency007.model.entity;

public enum CompletionStatus {

    NOT_STARTED, IN_PROGRESS, COMPLETED;

    public static CompletionStatus getCompletionStatusId(Request request) {
        int completedStatusId = request.getCompletionStatusId();
        return CompletionStatus.values()[--completedStatusId];
    }

    public String getName() {
        return name().toLowerCase();
    }

    public CompletionStatus getCompletionStatus(String name){
        for(CompletionStatus completionStatus : CompletionStatus.values()){
            if(completionStatus.getName().equals(name)){
                return completionStatus;
            }
        }
        return CompletionStatus.NOT_STARTED;
    }

}
