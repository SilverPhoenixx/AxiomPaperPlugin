package com.moulberry.axiom.integration;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public abstract class Integration {

    public abstract boolean canBreakBlock(Player player, Location location);
    public abstract boolean canPlaceBlock(Player player, Location location);
    public abstract SectionPermissionChecker checkSection(Player player, World world, int cx, int cy, int cz);

}
