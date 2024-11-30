class RPGCharacter {
    public int level;
    public double maxHP;
    public double maxMana;
    public double baseRunSpeed;
    public Sword sword;
    public Shield shield;

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

    public void fight(RPGCharacter character, RPGCharacter slime) {
        if(character.getRunSpeed() >= slime.getRunSpeed() && character.maxMana >= 8){
            character.maxMana -= 8;
            slime.maxHP -= character.getSwordDamage();
            if(character.shield != null) character.maxHP -= slime.getSwordDamage()*(0.01 * character.getShieldDefense());
            else character.maxHP -= slime.getSwordDamage();
            if(slime.maxHP <= 0){
                character.levelUp();
                System.out.println("Unfortunately, it die, But why you care you just Level up!!! Stats are at its own maxed once again");
                slime.maxHP = 110;
            }
            else {
                System.out.println("You use Sacred Blade Dance lost 8 mana for that move");
                if(character.shield != null) System.out.println("You took " + slime.getSwordDamage()*(0.01 * character.getShieldDefense()) + " damage and have " + character.getMaxHP() + " hp " + "left");
                else System.out.println("You don't have shield so you will took " + slime.getSwordDamage() + " damage and have " + character.getMaxHP() + " hp left");
                System.out.println("You hit him for " + character.getSwordDamage() + " damage and " + "it has only " + slime.maxHP + " hp left");
                System.out.println("----------------------------------------");
            }
        }
        else  System.out.println("Please unequip something, you heavy af can't even hit a single thing");
    }

    public void getstat(RPGCharacter character){
        System.out.println("Level: " + character.getLevel());
        System.out.println("Max HP: " + character.getMaxHP());
        System.out.println("Max Mana: " + character.getMaxMana());
        System.out.println("Sword Damage: " + character.getSwordDamage());
        System.out.println("Shield Defense: " + character.getShieldDefense());
        System.out.println("Run Speed: " + character.getRunSpeed());
    }
}