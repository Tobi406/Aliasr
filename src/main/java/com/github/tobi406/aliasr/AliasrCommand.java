package com.github.tobi406.aliasr;

import com.velocitypowered.api.command.RawCommand;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class AliasrCommand implements RawCommand {

    @Override
    public void execute(Invocation invocation) {
        if (invocation.source().hasPermission("aliasr.reload"))
            AliasrPlugin.getInstance().reload();

        invocation.source().sendMessage(
            Component.text((invocation.source().hasPermission("aliasr.reload") ? "Reloaded " : "Running "))
                .append(Component.text("Aliasr").color(NamedTextColor.AQUA))
                .append(Component.text(" version "))
                .append(Component.text("1.1-BETA").color(NamedTextColor.AQUA))
        );
    }
}
