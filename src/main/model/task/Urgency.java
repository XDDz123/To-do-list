package model.task;

public enum Urgency {
    HIGH, MID, LOW, UNASSIGNED;

    public String getString() {
        if (this.equals(HIGH)) {
            return "high";
        } else if (this.equals(MID)) {
            return "mid";
        } else if (this.equals(LOW)) {
            return "low";
        } else {
            return "unassigned";
        }
    }
}
