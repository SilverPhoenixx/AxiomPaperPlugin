package com.moulberry.axiom.integration.worldguard;

import com.moulberry.axiom.integration.Integration;
import com.moulberry.axiom.integration.SectionPermissionChecker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WorldGuardIntegration extends Integration {

    private final WorldGuardIntegrationImpl worldGuardIntegrationImpl;
    private final boolean worldGuardEnabled;
    public WorldGuardIntegration() {
        this.worldGuardIntegrationImpl = new WorldGuardIntegrationImpl();
        this.worldGuardEnabled = Bukkit.getPluginManager().isPluginEnabled("WorldGuard");
    }

    @Override
    public boolean canBreakBlock(Player player, Location location) {
        if (!worldGuardEnabled) {
            return true;
        }
        return worldGuardIntegrationImpl.canBreakBlock(player, location);
    }

    @Override
    public boolean canPlaceBlock(Player player, Location location) {
        if (!worldGuardEnabled) {
            return true;
        }
        return worldGuardIntegrationImpl.canPlaceBlock(player, location);
    }

    public SectionPermissionChecker checkSection(Player player, World world, int cx, int cy, int cz) {
        if (!worldGuardEnabled) {
            return SectionPermissionChecker.ALL_ALLOWED;
        }
        return worldGuardIntegrationImpl.checkSection(player, world, cx, cy, cz);
    }

}
