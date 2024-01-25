//package lilypuree.decorative_blocks.compat;
//
//import com.google.common.collect.Lists;
//import dev.architectury.fluid.FluidStack;
//import lilypuree.decorative_blocks.core.DBNames;
//import lilypuree.decorative_blocks.core.Registration;
//import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
//import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
//import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
//import me.shedaniel.rei.api.common.entry.EntryIngredient;
//import me.shedaniel.rei.api.common.entry.EntryStack;
//import me.shedaniel.rei.api.common.entry.type.EntryDefinition;
//import me.shedaniel.rei.api.common.entry.type.EntryType;
//import me.shedaniel.rei.api.common.plugins.PluginManager;
//import me.shedaniel.rei.api.common.registry.ReloadStage;
//import me.shedaniel.rei.api.common.util.EntryStacks;
//import me.shedaniel.rei.plugin.client.BuiltinClientPlugin;
//import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.function.UnaryOperator;
//
//public class DBClientPlugin implements REIClientPlugin {
//    @Override
//    public void registerDisplays(DisplayRegistry registry) {
//        EntryStack<FluidStack> thatch = EntryStacks.of(Registration.STILL_THATCH.get());
//        Component name = Component.literal("Thatch");
//        Component line = Component.translatable("wiki.decorative_blocks.thatch");
//        DefaultInformationDisplay info = DefaultInformationDisplay.createFromEntry(thatch, name);
//        info.line(line);
//        registry.add(info);
//    }
//}
