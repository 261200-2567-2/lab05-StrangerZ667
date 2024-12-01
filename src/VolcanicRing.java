public interface VolcanicRing {

    /**
     * Demolish: Executes a destructive action with this accessory.
     * Side Effect: Likely inflicts significant damage to the target slime
     *              causing it to broke.
     *
     * @param slime The character that will be the target of the demolish action.
     */
    void Demolish(RPGCharacter slime);
}