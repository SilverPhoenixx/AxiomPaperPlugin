package com.moulberry.axiom.commands;

import com.moulberry.axiom.AxiomPaper;
import com.moulberry.axiom.Restrictions;
import com.moulberry.axiom.integration.IntegrationManager;
import com.moulberry.axiom.integration.plotsquared.PlotSquaredIntegration;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.BukkitCommandManager;
import org.incendo.cloud.parser.standard.StringParser;
import org.incendo.cloud.permission.PredicatePermission;

import java.util.UUID;

public class AxiomDebugCommand {

    /**
     * Command requires either the axiom.debug permission or for you to have the UUID d0e05de7-6067-454d-beae-c6d19d886191
     * The command isn't capable of modifying the world, only checking various properties for debugging purposes
     * It should be 100% safe to give to any player, but is locked behind some restrictions due to the potential
     * for lagging the server by spamming certain commands
     */

    private static final UUID MOULBERRY_UUID = UUID.fromString("d0e05de7-6067-454d-beae-c6d19d886191");

    public static void register(AxiomPaper axiomPaper, BukkitCommandManager<CommandSender> manager) {
        manager.command(
            base(manager, "hasAxiomPermission").handler(context -> {
                if (!(context.sender() instanceof Player player)) return;
                boolean hasAxiomPermission = axiomPaper.hasAxiomPermission(player);
                context.sender().sendMessage(Component.text("hasAxiomPermission: " + hasAxiomPermission));
            })
        );
        manager.command(
            base(manager, "canUseAxiom").handler(context -> {
                if (!(context.sender() instanceof Player player)) return;
                boolean canUseAxiom = axiomPaper.canUseAxiom(player);
                context.sender().sendMessage(Component.text("canUseAxiom: " + canUseAxiom));
            })
        );
        manager.command(
            base(manager, "isMismatchedDataVersion").handler(context -> {
                if (!(context.sender() instanceof Player player)) return;
                boolean isMismatchedDataVersion = axiomPaper.isMismatchedDataVersion(player.getUniqueId());
                context.sender().sendMessage(Component.text("isMismatchedDataVersion: " + isMismatchedDataVersion));
            })
        );
        manager.command(
            base(manager, "canModifyWorld").handler(context -> {
                if (!(context.sender() instanceof Player player)) return;
                boolean canModifyWorld = axiomPaper.canModifyWorld(player, player.getWorld());
                context.sender().sendMessage(Component.text("canModifyWorld: " + canModifyWorld));
            })
        );
        manager.command(
            base(manager, "isClientListening").required("channel", StringParser.greedyStringParser()).handler(context -> {
                if (!(context.sender() instanceof Player player)) return;
                String channel = context.get("channel");
                boolean isClientListening = player.getListeningPluginChannels().contains(channel);
                context.sender().sendMessage(Component.text("listening to " + channel +": " + isClientListening));
            })
        );
        manager.command(
            base(manager, "hasPermission").required("permission", StringParser.greedyStringParser()).handler(context -> {
                String permission = context.get("permission");
                boolean hasPermission = context.sender().hasPermission(permission);
                context.sender().sendMessage(Component.text("has permission " + permission +": " + hasPermission));
            })
        );
        manager.command(
            base(manager, "getRestrictions").handler(context -> {
                if (!(context.sender() instanceof Player player)) return;
                Restrictions restrictions = axiomPaper.playerRestrictions.get(player.getUniqueId());
                if (restrictions == null) {
                    context.sender().sendMessage(Component.text("no restrictions"));
                } else {
                    context.sender().sendMessage(Component.text("restrictions: " + restrictions));
                }
            })
        );
        manager.command(
            base(manager, "canBreakBlockAtCurrentPosition").handler(context -> {
                if (!(context.sender() instanceof Player player)) return;

                Block block = player.getWorld().getBlockAt(player.getLocation());

                boolean canBreakBlock = axiomPaper.integrationManager.canBreakBlock(player, block.getLocation());
                context.sender().sendMessage(Component.text("canBreakBlock: " + canBreakBlock));
            })
        );
        manager.command(
            base(manager, "canPlaceBlockAtCurrentPosition").handler(context -> {
                if (!(context.sender() instanceof Player player)) return;

                Block block = player.getWorld().getBlockAt(player.getLocation());

                boolean canPlaceBlock = axiomPaper.integrationManager.canPlaceBlock(player, block.getLocation());
                context.sender().sendMessage(Component.text("canPlaceBlock: " + canPlaceBlock));
            })
        );
        manager.command(
            base(manager, "isPlotWorld").handler(context -> {
                if (!(context.sender() instanceof Player player)) return;
                boolean isPlotWorld = IntegrationManager.PLOTSQUARED_INTEGRATION.isPlotWorld(player.getWorld());
                context.sender().sendMessage(Component.text("isPlotWorld: " + isPlotWorld));
            })
        );
        manager.command(
            base(manager, "getCurrentEditablePlot").handler(context -> {
                if (!(context.sender() instanceof Player player)) return;
                PlotSquaredIntegration.PlotBounds plotBounds = IntegrationManager.PLOTSQUARED_INTEGRATION.getCurrentEditablePlot(player);
                context.sender().sendMessage(Component.text("plotBounds: " + plotBounds));
            })
        );
    }

    private static Command.Builder<CommandSender> base(BukkitCommandManager<CommandSender> manager, String subcommand) {
        return manager.commandBuilder("axiompaperdebug")
              .literal(subcommand)
              .senderType(CommandSender.class)
              .permission(PredicatePermission.of(sender -> {
                  if (sender instanceof Player player) {
                      return player.hasPermission("axiom.debug") || player.getUniqueId().equals(MOULBERRY_UUID);
                  } else {
                      return false;
                  }
              }));
    }



}
