package de.steallight.rangplugin.commands;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutoCompleteListener extends ListenerAdapter {

    private final String[] platform = new String[]{
            "Java",
            "Bedrock"
    };


    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent e) {
        if (e.getName().equals("whitelist") && e.getFocusedOption().getName().equals("platform")) {
            List<Command.Choice> platformOptions = Stream.of(platform)
                    .filter(plattform -> plattform.startsWith(e.getFocusedOption().getValue()))
                    .map(plattform -> new Command.Choice(plattform, plattform))
                    .collect(Collectors.toList());
            e.replyChoices(platformOptions).queue();
        }
    }
}
