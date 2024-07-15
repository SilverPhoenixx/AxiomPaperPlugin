package com.moulberry.axiom.integration.worldguard;

import com.moulberry.axiom.integration.Integration;
import com.moulberry.axiom.integration.SectionPermissionChecker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WorldGuardIntegration extends Integration {

    private WorldGuardIntegrationImpl worldGuardIntegrationImpl;
    private final boolean worldGuardEnabled;
    public WorldGuardIntegration() {
        this.worldGuardEnabled = Bukkit.getPluginManager().isPluginEnabled("WorldGuard");
        if(!worldGuardEnabled) return;
        this.worldGuardIntegrationImpl = new WorldGuardIntegrationImpl();
    }

    @Override
    public boolean isActive() {
        return worldGuardEnabled;
    }

    @Override
    public boolean canBreakBlock(Player player, Location location) {
        return worldGuardIntegrationImpl.canBreakBlock(player, location);
    }

    @Override
    public boolean canPlaceBlock(Player player, Location location) {
        return worldGuardIntegrationImpl.canPlaceBlock(player, location);
    }

    public SectionPermissionChecker checkSection(Player player, World world, int cx, int cy, int cz) {
        return worldGuardIntegrationImpl.checkSection(player, world, cx, cy, cz);
    }

}
