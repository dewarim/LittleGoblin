package de.dewarim.goblin;

public enum MeleeStatus {

    /**
     * Before a Melee starts, the system has to wait for the participants to register.
     */
    WAITING,

    /**
     * A Melee that has been started is in the RUNNING state.
     */
    RUNNING,

    /**
     * After the last (wo)man standing has been determined (or all Players have left),
     * the Melee is finished.
     */
    FINISHED

}
