public class RPGCharacter implements MainCharacter, Summon, VolcanicRing, SoulTalisman {

    // Fields for stats and items
    private int level; // Character's level; side effects: leveling up adjusts stats (maxHP and maxMana).
    private double maxHP; // Maximum HP; affected by healing and damage.
    private double maxMana; // Maximum Mana; consumed by skills and affects spell usage.
    private double baseRunSpeed; // Base running speed; used in calculations of current run speed.
    private int flask = 8; // Healing item count; decrement affects healing capability.
    private int scroll = 4; // Special stat-reset item count; decrement affects usability.

    // Equipments for each character
    public Sword sword; // Sword equipped; affects attack damage and speed penalties.
    public Shield shield; // Shield equipped; affects defense and speed penalties.

    // Turn for every buff that last for an amount of time
    private int turn = 0; // Tracks how many turns buffs/debuffs are active; side effects on stat changes.

    // Summon's Mana
    private double Mana = 0; // Mana for summon skills; affects usability of summon-based skills.

    // States for every situation to check either it do something or not
    private boolean StoneSkinOn = false; // If true, buffs defense; resets when duration ends.
    private boolean DustOn = false; // If true, buffs sword damage; resets when duration ends.
    private boolean SumOn = false; // If true, indicates summon is active.
    private boolean BleedOn = false; // Tracks bleeding status (currently unused but could impact HP).
    private boolean VR = true; // If false, Volcanic Ring has been destroyed; limits skill usage.
    private boolean ST = true; // If false, Soul Talisman has been destroyed; limits skill usage.

    // Constructor:  Initializes stats and recalculates based on level
    public RPGCharacter(int level, double baseRunSpeed) {
        this.level = level;
        this.baseRunSpeed = baseRunSpeed;
        calculateStats(); // Updates maxHP and maxMana based on level; any stat recalculation errors propagate.
    }

    // Calculates stats; side effects: updates maxHP and maxMana
    private void calculateStats() {
        this.maxHP = 100 + 10 * level; // Increases HP based on level.
        this.maxMana = 50 + 2 * level; // Increases Mana based on level.
    }

    // Equip a sword
    public void equipSword(Sword sword) {
        this.sword = sword; // Affects attack damage and running speed penalties.
    }

    // Equip a shield
    public void equipShield(Shield shield) {
        this.shield = shield; // Affects defense and running speed penalties.
    }

    // Unequip a shield
    public void unequipShield() {
        this.shield = null; // Removes defense bonus and running speed penalty.
    }

    // Unequip a sword
    public void unequipSword() {
        this.sword = null; // Removes attack bonus and running speed penalty.
    }

    // Damage calculation from sword
    public double getSwordDamage() {
        if (sword == null) return 0; // No sword means no damage.
        return sword.getBaseDamage() * (1 + 0.1 * level); // Side effect: damage scales with level.
    }

    // Defense calculation from shield
    public double getShieldDefense() {
        if (shield == null) return 0; // No shield means no defense.
        return shield.getBaseDefense() * (1 + 0.05 * level); // Side effect: defense scales with level.
    }

    // Calculates current run speed; affected by level and equipment penalties
    public double getRunSpeed() {
        double runSpeed = baseRunSpeed * (1 + 0.03 * level); // Scales with level.

        if (sword != null) {
            runSpeed -= (baseRunSpeed * (0.1 + 0.04 * level)); // Side effect: decreases speed with sword.
        }

        if (shield != null) {
            runSpeed -= (baseRunSpeed * (0.1 + 0.08 * level)); // Side effect: decreases speed with shield.
        }

        return runSpeed; // Running slower could prevent actions like attacking.
    }

    // Getters
    public int getLevel() {
        return level;
    }

    public double getMaxHP() {
        return maxHP;
    }

    public double getMaxMana() {
        return maxMana;
    }

    // Level up; side effect: boosts level, recalculates stats
    public void levelUp() {
        level++;
        calculateStats(); // Updates maxHP and maxMana after leveling up.
    }

    // Fight: Main combat logic; side effects include HP, Mana, and level changes
    public void fight(RPGCharacter character, RPGCharacter slime) {
        if(character.getRunSpeed() >= slime.getRunSpeed() && character.maxMana >= 8){ // Can attack monster when your speed is higher than monster
            character.maxMana -= 8; // Every attack use your own mana for 8 to cast attack
            slime.maxHP -= character.getSwordDamage();
            if(character.shield != null) character.maxHP -= slime.getSwordDamage()*(0.01 * character.getShieldDefense()); // If shield is not equip, take full damage
            else character.maxHP -= slime.getSwordDamage(); // If you equip shield, you take portion of damage
            if(slime.maxHP <= 0){ // Its die when hp is lower than 0
                character.levelUp();
                System.out.println("Unfortunately, it die, But why you care you just Level up!!! Stats are at its own maxed once again");
            }
            else { // Cast an attack and show how the things going
                System.out.println("You use Sacred Blade Dance lost 8 mana for that move");
                if(character.shield != null) System.out.println("You took " + slime.getSwordDamage()*(0.01 * character.getShieldDefense()) + " damage and have " + character.getMaxHP() + " hp " + "left");
                else System.out.println("You don't have shield so you will took " + slime.getSwordDamage() + " damage and have " + character.getMaxHP() + " hp left");
                System.out.println("You hit him for " + character.getSwordDamage() + " damage and " + "it has only " + slime.maxHP + " hp left");
                System.out.println("----------------------------------------");
                // Buff effects diminish over turns
                if(turn != 0){
                    if(StoneSkinOn) shield.baseDefense -= 2;
                    if(DustOn) sword.baseDamage -= 2;
                    turn -= 1;
                }
                else {
                    StoneSkinOn = false;
                    DustOn = false;
                }
            }
        }
        // You slower than monster, that mean you need to unequip something to lose your weight
        else  System.out.println("Please unequip something, you heavy af can't even hit a single thing");
    }

    // Display stats; side effect: purely for debugging
    public void getstat(RPGCharacter character){
        System.out.println("Level: " + character.getLevel());
        System.out.println("Max HP: " + character.getMaxHP());
        System.out.println("Max Mana: " + character.getMaxMana());
        System.out.println("Sword Damage: " + character.getSwordDamage());
        System.out.println("Shield Defense: " + character.getShieldDefense());
        System.out.println("Run Speed: " + character.getRunSpeed());
    }

    // Healing function; side effect: consumes flask, restores HP
    @Override
    public void Healing(){
        if(flask > 0) {
            flask -= 1; // Use 1 flask for 20 HP
            maxHP += 20; // Restores 20 HP; potential for overhealing.
            System.out.println("You have used your flask to heal for 20 HP");
        }
        else System.out.println("You don't have any flasks in your inventory");
    }

    // ReadScroll from MainCharacter interface
    @Override
    public void ReadScroll(){
        if(scroll > 0){
            scroll -= 1; // Use 1 scroll for max all stats out trading for 2 levels
            level -= 2;
            calculateStats();
            System.out.println("You have used your scrolls to max health and mana for 2 levels of yours");
        }
        else System.out.println("You don't have any scrolls in your inventory");
    }

    // StoneSkin from MainCharacter interface
    @Override
    public void StoneSkin(RPGCharacter character){
        if(character.maxMana >= 10){
            character.maxMana -= 10;
            shield.baseDefense += 10; // Get defense and last for 5 turn
            turn += 5;
            StoneSkinOn = true;
            System.out.println("You used StoneSkin your defense has been increase 10 point for 5 actions");
        }
        else System.out.println("You don't have any mana left");
    }

    // Summon from MainCharacter interface
    @Override
    public void Summon(RPGCharacter character){
        if(SumOn && Mana != 0) System.out.println("You have your own summon already");
        else if(character.maxMana < 20) System.out.println("You don't have any mana left");
        else {
            character.maxMana -= 20;
            SumOn = true;
            Mana = 40;
            System.out.println("You have spawn your summon to fight with you");
        }
    }

    // Dust from Summon interface
    @Override
    public void Dust(){
        if(Mana < 8) System.out.println("Summon's mana is not enough to cast this skill");
        else{
            Mana -= 8; // This is its own mana
            DustOn = true;
            sword.baseDamage += 10;
            turn += 2;
            System.out.println("Your summon used dust their defense is lower and your sword is sharper now");
        }
    }

    // Pulse from Summon interface
    @Override
    public void Pulse(RPGCharacter slime){
        if(Mana < 8) System.out.println("Summon's mana is not enough to cast this skill");
        else {
            Mana -= 10;
            slime.maxHP -= 4;
            System.out.println("Your summon used pulse and did damage to an enemy");
        }
    }

    // Demolish from VolcanicRing and SoulTalisman interface
    @Override
    public void Demolish(RPGCharacter slime){
        System.out.println("You Demolish Your Item");
    }

    // Ritual from SoulTalisman interface
    @Override
    public void Ritual(RPGCharacter character,RPGCharacter slime){
        if(character.maxMana < 8) System.out.println("You don't have any mana left");
        else{
            character.maxMana -= 8;
            slime.maxHP -= 20;
            character.maxHP -= 20;
            System.out.println("You used Ritual cause 20 damage to everyone");
        }
    }

    // Ritual from BleedTouch interface
    @Override
    public void BleedTouch(RPGCharacter character,RPGCharacter slime){
        if(character.maxHP < 30) System.out.println("You don't have any HP left");
        else{
            character.maxHP -= 30;
            slime.maxHP -= 4;
            System.out.println("You used BleedTouch damage yourself 30 and cause 4 damage to slime and leave wound with it");
        }

    }

    // VolcanicRing specific Demolish
    public void volcanicDemolish(RPGCharacter slime) {
        VR = false;
        slime.maxHP -= slime.maxHP - 1;
        System.out.println("VolcanicRing Demolish causes lava destruction and broke itself");
    }

    // SoulTalisman specific Demolish
    public void talismanDemolish(RPGCharacter slime) {
        ST = false;
        slime.maxHP -= slime.maxHP - 1;
        System.out.println("SoulTalisman Demolish drains soul energy and broke itself");
    }
}