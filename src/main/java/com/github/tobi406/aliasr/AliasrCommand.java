package com.github.tobi406.aliasr;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;

import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;

import org.checkerframework.checker.nullness.qual.NonNull;

public class AliasrCommand implements Command {

    @Override
    public void execute(@NonNull CommandSource source, @NonNull String[] args) {
        if (source.hasPermission("aliasr.reload")) AliasrPlugin.getInstance().reload();
        source.sendMessage(TextComponent.of(
                (source.hasPermission("aliasr.reload") ? "Reloaded " : "Running ")).append(
                TextComponent.of("Aliasr").color(TextColor.AQUA)).append(
                TextComponent.of(" version ")).append(
                TextComponent.of("1.0-BETA").color(TextColor.AQUA))
        );
    }
}
