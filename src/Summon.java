public interface Summon {

    /**
     * Dust: Performs a dust-related action, possibly an effect to their enemy.
     * Side Effect: Reduce an opponent's defense and increase owner strength.
     *
     */
    void Dust();

    /**
     * Pulse: Executes a pulse action on the enemy.
     * Side Effect: Do some damage to their opponent.
     *
     * @param slime The character that will be affected by the pulse action.
     */
    void Pulse(RPGCharacter slime);
}