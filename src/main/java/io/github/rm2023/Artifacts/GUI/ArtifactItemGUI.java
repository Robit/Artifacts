package io.github.rm2023.Artifacts.GUI;

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
import de.themoep.inventorygui.InventoryGui.Close;
import de.themoep.inventorygui.InventoryGui.CloseAction;
import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Reward;

public class ArtifactItemGUI {
    public InventoryGui gui;
    public boolean rewardChosen = false;
    public String[][] guiSetup = { { "    r    " }, { "   r r   " }, { "  r r r  " }, { " r r r r " }, { "r r r r r" }, { " rrr rrr " }, { " rrrrrrr " }, { "rrrr rrrr" }, { "rrrrrrrrr" }, { "prrrrrrrn" } };

    public ArtifactItemGUI(List<Reward> rewardList, Player player, String[] properties) {
        gui = new InventoryGui(Main.plugin, "Choose a reward!", guiSetup[(rewardList.size() > 9) ? 9 : rewardList.size() - 1], new GuiElement[0]);
        gui.addElement(new GuiPageElement('p', new ItemStack(Material.ARROW), PageAction.NEXT, "Go to previous page (%prevpage%)"));
        gui.addElement(new GuiPageElement('n', new ItemStack(Material.ARROW), PageAction.NEXT, "Go to next page (%nextpage%)"));
        GuiElementGroup rewardGroup = new GuiElementGroup('r');
        Collections.sort(rewardList);
        rewardList.forEach((reward) -> {
            DynamicGuiElement rewardElement = new DynamicGuiElement('d', () -> {
                GuiElement element = reward.getRepresentation(true);
                element.setAction(new ElementToggleAction(reward, properties, this));
                return element;
            });
            rewardGroup.addElement(rewardElement);
        });
        gui.addElements(rewardGroup);
        gui.setCloseAction(new GuiCloseAction(this));
        gui.show(player);
    }

    private class ElementToggleAction implements Action {
        private Reward reward;
        private String[] properties;
        private ArtifactItemGUI gui;

        public ElementToggleAction(Reward reward, String[] properties, ArtifactItemGUI gui) {
            this.reward = reward;
            this.properties = properties;
            this.gui = gui;
        }

        @Override
        public boolean onClick(Click click) {
            if (!gui.rewardChosen) {
                click.getGui().playClickSound();
                gui.rewardChosen = true;
                click.getEvent().getWhoClicked().sendMessage(Main.plugin.rewardManager.giveReward((Player) click.getEvent().getWhoClicked(), reward, false, properties));
                gui.gui.close();
            }
            return true;
        }
    }

    private class GuiCloseAction implements CloseAction {
        ArtifactItemGUI gui;

        public GuiCloseAction(ArtifactItemGUI gui) {
            this.gui = gui;
        }

        @Override
        public boolean onClose(Close close) {
            if (!gui.rewardChosen) {
                Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> gui.gui.show(close.getEvent().getPlayer()), 0);
            }
            return true;
        }
    }
}
