package com.denanu.litematicaBuilder.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.denanu.litematicaBuilder.builder.BuildManager;

import fi.dy.masa.litematica.gui.GuiSchematicPlacementsList;
import fi.dy.masa.litematica.gui.widgets.WidgetListSchematicPlacements;
import fi.dy.masa.litematica.gui.widgets.WidgetSchematicPlacement;
import fi.dy.masa.litematica.schematic.placement.SchematicPlacement;
import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;

@Mixin(GuiSchematicPlacementsList.class)
public abstract class LitematicatPlacementListMixin extends GuiListBase<SchematicPlacement, WidgetSchematicPlacement, WidgetListSchematicPlacements> implements ISelectionListener<SchematicPlacement> {
	protected LitematicatPlacementListMixin(int listX, int listY) {
		super(listX, listY);
	}
	
	private class BuildButtonListener implements IButtonActionListener {

		@Override
		public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
			if (mouseButton != 0) {
				return;
			}
			BuildManager.getInstance().start();
		}
	}

	@Inject(at = @At("TAIL"), method = "initGui()V")
	private void initGui(CallbackInfo info) {
        int y = 12;
		//ButtonListenerChangeMenu.ButtonType type = ButtonListenerChangeMenu.ButtonType.LOADED_SCHEMATICS;
		String label = "Build Currently Selected";
        int buttonWidth = ((GuiSchematicPlacementsList)(Object)this).getStringWidth(label) + 20;
        int x = ((GuiSchematicPlacementsList)(Object)this).width - buttonWidth - 12;
        ButtonGeneric button = new ButtonGeneric(x, y, buttonWidth, 20, label);
        ((GuiSchematicPlacementsList)(Object)this).addButton(button, new BuildButtonListener());
	}
}
