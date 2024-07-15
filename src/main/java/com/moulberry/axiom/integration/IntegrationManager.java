package com.moulberry.axiom.integration;

import com.moulberry.axiom.integration.plotsquared.PlotSquaredIntegration;
import com.moulberry.axiom.integration.worldguard.WorldGuardIntegration;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IntegrationManager {

    public static WorldGuardIntegration WORLDGUARD_INTEGRATION = new WorldGuardIntegration();
    public static PlotSquaredIntegration PLOTSQUARED_INTEGRATION = new PlotSquaredIntegration();

    private final List<Integration> integrations;

    public IntegrationManager() {
        this.integrations = new ArrayList<>();
        this.integrations.add(WORLDGUARD_INTEGRATION);
        this.integrations.add(PLOTSQUARED_INTEGRATION);
    }
    public void addIntegration(Integration integration) {
        integrations.add(integration);
    }

    public boolean canBreakBlock(Player player, Location location) {
        return integrations.stream().allMatch(integration -> integration.canBreakBlock(player, location));
    }

    public boolean canPlaceBlock(Player player, Location location) {
        return integrations.stream().allMatch(integration -> integration.canPlaceBlock(player, location));
    }

    @Nullable
    public SectionPermissionChecker checkSection(Player player, World world, int cx, int cy, int cz) {
       List<SectionPermissionChecker> sectionCheckers = integrations.stream().map(integration -> integration.checkSection(player, world, cx, cy, cz)).toList();
        Optional<SectionPermissionChecker> permissionChecker = sectionCheckers.stream().reduce(SectionPermissionChecker::combine);

        return permissionChecker.orElse(null);
    }
}
