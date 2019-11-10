package model.task;

public enum Urgency {
    HIGH, MID, LOW, UNASSIGNED;

    //EFFECTS: Returns the corresponding string representations of enum Urgency.
    //         HIGH is "high"
    //         MID is "mid"
    //         LOW is "low"
    //         else return "unassigned"
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
