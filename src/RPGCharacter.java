public class RPGCharacter implements MainCharacter, Summon, VolcanicRing, SoulTalisman {

    // Each character own stats and items for uses
    private int level;
    private double maxHP;
    private double maxMana;
    private double baseRunSpeed;
    private int flask = 8;
    private int scroll = 4;

    // Equipments for each character
    public Sword sword;
    public Shield shield;

    // Turn for every buff that last for an amount of time
    private int turn = 0;

    // Summon's Mana
    private double Mana = 0;

    // States for every situation to check either it do something or not
    private boolean StoneSkinOn = false;
    private boolean DustOn = false;
    private boolean SumOn = false;
    private boolean BleedOn = false;
    private boolean VR = true;
    private boolean ST = true;

    // Constructor
    public RPGCharacter(int level, double baseRunSpeed) {
        this.level = level;
        this.baseRunSpeed = baseRunSpeed;
        calculateStats();
    }

    // Calculate stats based on level
    private void calculateStats() {
        this.maxHP = 100 + 10 * level;
        this.maxMana = 50 + 2 * level;
    }

    // Equip a sword
    public void equipSword(Sword sword) {
        this.sword = sword;
    }

    // Equip a shield
    public void equipShield(Shield shield) {
        this.shield = shield;
    }

    // Unequip a shield
    public void unequipShield() {
        this.shield = null;
    }

    // Unequip a sword
    public void unequipSword() {
        this.sword = null;
    }

    // Get damage dealt by sword
    public double getSwordDamage() {
        if (sword == null) return 0;
        return sword.getBaseDamage() * (1 + 0.1 * level);
    }

    // Get defense provided by shield
    public double getShieldDefense() {
        if (shield == null) return 0;
        return shield.getBaseDefense() * (1 + 0.05 * level);
    }

    // Calculate current run speed
    public double getRunSpeed() {
        double runSpeed = baseRunSpeed * (1 + 0.03 * level);

        if (sword != null) {
            runSpeed -= (baseRunSpeed * (0.1 + 0.04 * level));
        }

        if (shield != null) {
            runSpeed -= (baseRunSpeed * (0.1 + 0.08 * level));
        }

        return runSpeed;
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

    // Level up the character
    public void levelUp() {
        level++;
        calculateStats();
    }

    // Fight function is the main action
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
                // Every buff is calculated here and last for its own time
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

    // Display all of your own stats especially for debug
    public void getstat(RPGCharacter character){
        System.out.println("Level: " + character.getLevel());
        System.out.println("Max HP: " + character.getMaxHP());
        System.out.println("Max Mana: " + character.getMaxMana());
        System.out.println("Sword Damage: " + character.getSwordDamage());
        System.out.println("Shield Defense: " + character.getShieldDefense());
        System.out.println("Run Speed: " + character.getRunSpeed());
    }

    // Healing function from MainCharacter interface
    @Override
    public void Healing(){
        if(flask > 0) {
            flask -= 1; // Use 1 flask for 20 HP
            maxHP += 20;
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