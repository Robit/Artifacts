package io.github.rm2023.Artifacts;

import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.GuiElement;
import de.themoep.inventorygui.GuiElement.Action;
import de.themoep.inventorygui.GuiElement.Click;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.GuiPageElement;
import de.themoep.inventorygui.GuiPageElement.PageAction;
import de.themoep.inventorygui.InventoryGui;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class PassiveManagementGUI {
    public InventoryGui gui;
    public String[] guiSetup = { "rrrrrrrrr", "rrrrrrrrr", "rrrrrrrrr", "rrrrrrrrr", "rrrrrrrrr", "p       n", };

    public PassiveManagementGUI(Player player) {
        gui = new InventoryGui(Main.plugin, "Click a passive to toggle it!", guiSetup, new GuiElement[0]);
        gui.addElement(new GuiPageElement('p', new ItemStack(Material.ARROW), PageAction.NEXT, "Go to previous page (%prevpage%)"));
        gui.addElement(new GuiPageElement('n', new ItemStack(Material.ARROW), PageAction.NEXT, "Go to next page (%nextpage%)"));
        GuiElementGroup passiveGroup = new GuiElementGroup('r');
        List<Passive> passives = Main.plugin.rewardManager.listPassives(player);
        Collections.sort(passives);
        passives.forEach((passive) -> {
            DynamicGuiElement passiveElement = new DynamicGuiElement('d', () -> {
                GuiElement element = passive.getRepresentation(Main.plugin.rewardManager.isEnabled(passive, player));
                element.setAction(new ElementToggleAction(passive));
                return element;
            });
            passiveGroup.addElement(passiveElement);
        });
        gui.addElements(passiveGroup);
        gui.show(player);
    }

    private class ElementToggleAction implements Action {
        private Passive passive;

        public ElementToggleAction(Passive passive) {
            this.passive = passive;
        }

        @Override
        public boolean onClick(Click click) {
            click.getGui().playClickSound();
            click.getEvent().getWhoClicked().sendMessage(Main.plugin.rewardManager.togglePassive((Player) click.getEvent().getWhoClicked(), passive, false));
            click.getGui().draw();
            return true;
        }
    }
}
