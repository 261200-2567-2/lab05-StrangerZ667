public class Main {
    public static void main(String[] args) {
        RPGCharacter slime = new RPGCharacter(1, 8.0); // Level 1 with base run speed 8.0
        RPGCharacter ghoul = new RPGCharacter(2, 1.0); // Level 2 with base run speed 1.0
        RPGCharacter character = new RPGCharacter(5, 10.0); // Level 5 with base run speed 10.0
        Sword sword = new Sword(15.0); // Sword with base damage 15
        Sword monsword = new Sword(10.0); // Sword with base damage 10
        Shield shield = new Shield(10.0); // Shield with base defense 10

        // Equip sword and shield
        character.equipSword(sword);
        character.equipShield(shield);
        slime.equipSword(monsword);
        ghoul.equipSword(monsword);

        // Display character stats
        character.getstat(character);
        System.out.println("----------------------------------------");

        // debug for BleedTouch
        character.fight(character,ghoul);
        character.BleedTouch(character,ghoul);
        System.out.println(character.getMaxHP());
        System.out.println(ghoul.getMaxHP());
        System.out.println("----------------------------------------");

        // debug for Healing
        character.Healing();
        System.out.println(character.getMaxHP());
        System.out.println("----------------------------------------");

        // debug for ReadScroll
        character.ReadScroll();
        System.out.println("----------------------------------------");
        character.getstat(character);
        System.out.println("----------------------------------------");

        // debug for StoneSkin
        character.StoneSkin(character);
        character.getstat(character);
        System.out.println("----------------------------------------");
        character.fight(character,ghoul);
        character.getstat(character);
        System.out.println("----------------------------------------");

        // debug for Summon and Dust
        character.Summon(character);
        character.Dust();
        character.getstat(character);
        System.out.println("----------------------------------------");
        character.fight(character,ghoul);
        character.getstat(character);
        System.out.println("----------------------------------------");

        // debug for Pulse
        System.out.println(ghoul.getMaxHP());
        character.Pulse(ghoul);
        System.out.println(ghoul.getMaxHP());
        System.out.println("----------------------------------------");

        // debug for Ritual
        System.out.println(character.getMaxHP());
        System.out.println(ghoul.getMaxHP());
        character.Ritual(character,ghoul);
        System.out.println(character.getMaxHP());
        System.out.println(ghoul.getMaxHP());
        System.out.println("----------------------------------------");

        // debug for Demolish
        character.volcanicDemolish(ghoul);
        System.out.println(ghoul.getMaxHP());
        character.getstat(character);
        System.out.println("----------------------------------------");
        character.ReadScroll();
        character.fight(character,ghoul);
        System.out.println("----------------------------------------");
        character.getstat(character);
        System.out.println("----------------------------------------");
    }
}
