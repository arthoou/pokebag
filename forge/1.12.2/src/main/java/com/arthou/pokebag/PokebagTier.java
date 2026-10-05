package com.arthou.pokebag;

public enum PokebagTier {
    NORMAL("pokebag_normal", "normal", 18, "Pok\u00e9bag Normal"),
    GREAT("pokebag_great", "great", 27, "Great Pok\u00e9bag"),
    ULTRA("pokebag_ultra", "ultra", 36, "Ultra Pok\u00e9bag"),
    MASTER("pokebag_master", "master", 54, "Master Pok\u00e9bag");

    private final String registryName;
    private final String translationName;
    private final int slots;
    private final String displayName;

    PokebagTier(String registryName, String translationName, int slots, String displayName) {
        this.registryName = registryName;
        this.translationName = translationName;
        this.slots = slots;
        this.displayName = displayName;
    }

    public String getRegistryName() {
        return registryName;
    }

    public String getTranslationName() {
        return translationName;
    }

    public int getSlots() {
        return slots;
    }

    public String getDisplayName() {
        return displayName;
    }
}
