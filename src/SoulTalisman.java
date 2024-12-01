public interface SoulTalisman extends VolcanicRing {

    /**
     * Ritual: Performs a ritual involving the specified character and enemy.
     * Side Effect: Causing all entity that participate in the action to take damage instantly.
     *
     * @param character The primary character involved in the ritual.
     * @param slime     The enemy character involved in the ritual.
     */
    void Ritual(RPGCharacter character, RPGCharacter slime);

    /**
     * BleedTouch: Applies a bleeding effect to the specified enemy.
     * Side Effect: Likely inflicts permanent damage that wound target and loss its health.
     *
     * @param character The character initiating the bleed touch.
     * @param slime     The enemy character affected by the bleed touch.
     */
    void BleedTouch(RPGCharacter character, RPGCharacter slime);
}