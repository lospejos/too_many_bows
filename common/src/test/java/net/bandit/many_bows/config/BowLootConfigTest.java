package net.bandit.many_bows.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BowLootConfigTest {

    @Test
    void defaultsNeedNoFixing() {
        assertFalse(new BowLootConfig().validateAndFillDefaults());
    }

    @Test
    void nullsAreFilledWithDefaults() {
        BowLootConfig c = new BowLootConfig();
        c.easyLootEnabled = null;
        c.hardLootItems = null;
        c.globalBowPullSpeed = null;

        assertTrue(c.validateAndFillDefaults());
        assertEquals(Boolean.TRUE, c.easyLootEnabled);
        assertEquals(new BowLootConfig().hardLootItems, c.hardLootItems);
        assertEquals(16.0F, c.globalBowPullSpeed);
    }

    @Test
    void chancesAreClampedToUnitRange() {
        BowLootConfig c = new BowLootConfig();
        c.easyLootDropChance = 5.0F;
        c.hardLootDropChance = -1.0F;

        assertTrue(c.validateAndFillDefaults());
        assertEquals(1.0F, c.easyLootDropChance);
        assertEquals(0.0F, c.hardLootDropChance);
    }

    @Test
    void negativePowerMultiplierBecomesZero() {
        BowLootConfig c = new BowLootConfig();
        c.globalBowPowerMultiplier = -2.0D;

        assertTrue(c.validateAndFillDefaults());
        assertEquals(0.0D, c.globalBowPowerMultiplier);
    }

    @Test
    void malformedJsonIsRejectedByGsonAsParseException() {
        // loadConfig must catch this type, not only IOException
        assertThrows(com.google.gson.JsonParseException.class,
                () -> new com.google.gson.Gson().fromJson("{ not json", BowLootConfig.class));
    }
}
