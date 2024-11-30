public class Main {
    public static void main(String[] args) {
        RPGCharacter slime = new RPGCharacter(1, 8.0);
        RPGCharacter ghoul = new RPGCharacter(2, 1.0);
        RPGCharacter character = new RPGCharacter(5, 10.0); // Level 5 with base run speed 10.0
        Sword sword = new Sword(15.0); // Sword with base damage 15
        Sword monsword = new Sword(10.0); // Sword with base damage 15
        Shield shield = new Shield(10.0); // Shield with base defense 10

        // Equip sword and shield
        character.equipSword(sword);
        character.equipShield(shield);
        slime.equipSword(monsword);
        ghoul.equipSword(monsword);

        // Display character stats
        character.getstat(character);
        System.out.println("Slime Run Speed: " + slime.getRunSpeed());
        System.out.println("----------------------------------------");

        character.fight(character, slime);
        character.unequipShield();
        System.out.println("After Unequip Shield Run Speed: " + character.getRunSpeed());
        character.fight(character, slime);
        sword.baseDamage = 1000.0;
        character.fight(character, slime);
        character.getstat(character);
        System.out.println("----------------------------------------");
        sword.baseDamage = 15.0;
        character.fight(character, slime);
        System.out.println("Run Speed: " + character.getRunSpeed());
        character.equipShield(shield);
        System.out.println("After Shield Run Speed: " + character.getRunSpeed());
        System.out.println("Ghoul Run Speed: " + ghoul.getRunSpeed());
        System.out.println("----------------------------------------");
        character.fight(character, ghoul);
        sword.baseDamage = 1000.0;
        character.fight(character, ghoul);
        sword.baseDamage = 15.0;
        character.getstat(character);
        System.out.println("----------------------------------------");
    }
}
