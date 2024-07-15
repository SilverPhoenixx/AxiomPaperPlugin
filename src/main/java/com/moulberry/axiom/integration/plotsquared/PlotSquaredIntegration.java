package com.moulberry.axiom.integration.plotsquared;


import com.moulberry.axiom.integration.Integration;
import com.moulberry.axiom.integration.SectionPermissionChecker;
import net.minecraft.core.BlockPos;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Set;


public class PlotSquaredIntegration extends Integration {

    public record PlotBox(BlockPos min, BlockPos max) {}
    public record PlotBounds(Set<PlotBox> boxes, String worldName) {}

    private final PlotSquaredIntegrationImpl plotSquareIntegrationImpl;
    private final boolean plotSquaredEnabled;

    public PlotSquaredIntegration() {
        this.plotSquareIntegrationImpl = new PlotSquaredIntegrationImpl();
        this.plotSquaredEnabled = Bukkit.getPluginManager().isPluginEnabled("PlotSquared");
    }

    @Override
    public SectionPermissionChecker checkSection(Player player, World world, int sectionX, int sectionY, int sectionZ) {
        if (!plotSquaredEnabled) {
            return SectionPermissionChecker.ALL_ALLOWED;
        }
        return plotSquareIntegrationImpl.checkSection(player, world, sectionX, sectionY, sectionZ);
    }


    @Override
    public boolean canBreakBlock(Player player, Location location) {
        if (!plotSquaredEnabled) {
            return true;
        }
        return plotSquareIntegrationImpl.canBreakBlock(player, location);
    }

    @Override
    public boolean canPlaceBlock(Player player, Location location) {
        if (!plotSquaredEnabled) {
            return true;
        }
        return plotSquareIntegrationImpl.canPlaceBlock(player, location);
    }

    public boolean isPlotWorld(World world) {
        if (!plotSquaredEnabled) {
            return false;
        }
        return plotSquareIntegrationImpl.isPlotWorld(world);
    }

    public PlotSquaredIntegration.PlotBounds getCurrentEditablePlot(Player player) {
        if (!plotSquaredEnabled) {
            return null;
        }
        return plotSquareIntegrationImpl.getCurrentEditablePlot(player);
    }
}
