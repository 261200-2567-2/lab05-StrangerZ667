public interface MainCharacter {

    /**
     * Healing: Performs a healing action, likely restoring health to the character.
     * Side Effect: The health will be increase 20 trading for 1 flask.
     */
    void Healing();

    /**
     * ReadScroll: Reads a scroll, possibly granting new abilities, spells, or temporary effects.
     * Side Effect: The health and mana will be maxed out trading for 1 scroll and 2 levels.
     */
    void ReadScroll();

    /**
     * StoneSkin: Applies a "Stone Skin" effect to the specified RPGCharacter.
     * Side Effect: Increases the defense of the target character.
     *
     * @param character The character to which the "Stone Skin" effect will be applied.
     */
    void StoneSkin(RPGCharacter character);

    /**
     * Summon: Summons an ally or creature to assist in the game.
     * Side Effect: Introduces a new entity into the game world.
     *
     * @param character The character representing the entity that use their mana to summon.
     */
    void Summon(RPGCharacter character);
}