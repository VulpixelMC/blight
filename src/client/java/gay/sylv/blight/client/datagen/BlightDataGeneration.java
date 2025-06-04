package gay.sylv.blight.client.datagen;

import gay.sylv.blight.api.item.BlightItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import org.jetbrains.annotations.NotNull;

/**
 * <h1>Blight Data Generation</h1>
 * Blight uses <b>Data Generation</b> (also known as <b>datagen</b>) to alleviate the burden of development.
 * <h2>API Stability</h2>
 * The classes in {@link gay.sylv.blight.client.datagen} and {@link BlightDataGeneration} are not
 * guaranteed to be stable between minor releases. <b>Use these classes at your own risk!</b>
 * However, unlike {@link gay.sylv.blight.impl}, you will get support should any issues arise.
 */
public class BlightDataGeneration implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(BlightModelProvider::new);
	}

	public static class BlightModelProvider extends FabricModelProvider {
		public BlightModelProvider(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
		}

		@Override
		public @NotNull String getName() {
			return "Blight Model Provider";
		}

		@Override
		public void generateItemModels(ItemModelGenerators itemModelGenerators) {
			itemModelGenerators.generateFlatItem(BlightItems.ECHO_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
		}
	}
}
