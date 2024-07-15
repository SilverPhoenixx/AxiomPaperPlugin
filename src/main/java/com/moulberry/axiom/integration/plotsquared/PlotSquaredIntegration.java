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

    private PlotSquaredIntegrationImpl plotSquareIntegrationImpl;
    private final boolean plotSquaredEnabled;

    public PlotSquaredIntegration() {
        this.plotSquaredEnabled = Bukkit.getPluginManager().isPluginEnabled("PlotSquared");
        if(!plotSquaredEnabled) return;
        this.plotSquareIntegrationImpl = new PlotSquaredIntegrationImpl();
    }

    @Override
    public SectionPermissionChecker checkSection(Player player, World world, int sectionX, int sectionY, int sectionZ) {
        return plotSquareIntegrationImpl.checkSection(player, world, sectionX, sectionY, sectionZ);
    }


    @Override
    public boolean isActive() {
        return plotSquaredEnabled;
    }

    @Override
    public boolean canBreakBlock(Player player, Location location) {
        return plotSquareIntegrationImpl.canBreakBlock(player, location);
    }

    @Override
    public boolean canPlaceBlock(Player player, Location location) {
        return plotSquareIntegrationImpl.canPlaceBlock(player, location);
    }

    public boolean isPlotWorld(World world) {
        if (!isActive()) {
            return false;
        }
        return plotSquareIntegrationImpl.isPlotWorld(world);
    }

    public PlotSquaredIntegration.PlotBounds getCurrentEditablePlot(Player player) {
        if (!isActive()) {
            return null;
        }
        return plotSquareIntegrationImpl.getCurrentEditablePlot(player);
    }
}
