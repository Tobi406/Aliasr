package com.github.tobi406.aliasr;

import com.moandjiezana.toml.Toml;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;

import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Plugin(
        id = "aliasr",
        name = "Aliasr",
        version = "1.1-BETA",
        description = "Simple Alias manager using RegEx",
        authors = { "Tobi406" }
)
public class AliasrPlugin {
    private static AliasrPlugin instance;

    private CommandManager commandManager;
    private Logger logger;
    private Toml config;

    private Path folder;

    List<String> registeredCommands = new ArrayList<>();

    @Inject
    public AliasrPlugin(CommandManager commandManager, Logger logger, @DataDirectory final Path folder) {
        this.commandManager = commandManager;
        this.logger = logger;
        this.folder = folder;

        this.config = this.loadConfig(folder);

        this.registerCommands();


        instance = this;
    }
    public static AliasrPlugin getInstance() {
        return instance;
    }

    private Toml loadConfig(Path path) {
        File folder =  path.toFile();
        File file = new File(folder, "config.toml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            try (InputStream input = getClass().getResourceAsStream("/" + file.getName())) {
                if (input != null) {
                    Files.copy(input, file.toPath());
                } else {
                    file.createNewFile();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
                return null;
            }
        }

        return new Toml().read(file);
    }

    public void reload() {
        this.config = this.loadConfig(this.folder);

        this.unregisterCommands();
        this.registerCommands();
    }

    public void registerCommands() {
        this.commandManager.register("aliasr", new AliasrCommand());
        this.registeredCommands.add("aliasr");
        this.logger.info("Registered plugin command \"aliasr\"");

        List<HashMap<String, String>> aliases = this.config.getList("aliases");

        aliases.forEach(hashMap -> {
            this.commandManager.register(hashMap.get("name"), new SimpleCommand() {
                private String args = hashMap.get("args");
                private String command = hashMap.get("command");
                private String commandArgs = hashMap.get("commandArgs");

                @Override
                public void execute(Invocation invocation) {
                    String joinedArgs = String.join(" ", invocation.arguments());

                    AliasrPlugin.getInstance().commandManager.executeAsync(
                        invocation.source(),
                        this.command
                            + (args.length() > 0 ? (" " + joinedArgs.replaceAll(this.args, this.commandArgs)) : "")
                    );
                }
            });

            this.registeredCommands.add(hashMap.get("name"));
            this.logger.info("Registered alias command \"" + hashMap.get("name") + "\"");
        });
    }

    public void unregisterCommands() {
        this.registeredCommands.forEach(this.commandManager::unregister);
    }
}
