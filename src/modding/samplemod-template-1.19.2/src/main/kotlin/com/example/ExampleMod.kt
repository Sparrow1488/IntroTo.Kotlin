package com.example

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.slf4j.LoggerFactory

object ExampleMod : ModInitializer {

	private val FABRIC_ITEM = Item(FabricItemSettings().group(ItemGroup.MISC))

	// How to use logger?
//	private val logger = LoggerFactory.getLogger("samplemod")

	override fun onInitialize() {
		Registry.register(
			Registry.ITEM,
			Identifier("sparrow", "custom_item"), // /give @s sparrow:custom_item
			FABRIC_ITEM
		)
	}
}