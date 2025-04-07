package voltaicapi.common.item.gear;

import java.util.List;

import voltaicapi.common.inventory.container.ContainerGuidebook;
import voltaicapi.common.item.ItemElectrodynamics;
import voltaicapi.prefab.utilities.ModularElectricityTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ItemGuidebook extends ItemElectrodynamics {

	private static final String LINK = "https://wiki.aurilis.dev";
	private static final Component CONTAINER_TITLE = Component.translatable("container.guidebook");

	public ItemGuidebook(Properties properties, Holder<CreativeModeTab> creativeTab) {
		super(properties, creativeTab);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag flag) {
		tooltips.add(ModularElectricityTextUtils.tooltip("info.guidebookuse").withStyle(ChatFormatting.LIGHT_PURPLE));
		tooltips.add(ModularElectricityTextUtils.tooltip("guidebookname").withStyle(ChatFormatting.LIGHT_PURPLE));
		super.appendHoverText(stack, context, tooltips, flag);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand handIn) {
		if (world.isClientSide) {
			if (player.isShiftKeyDown()) {
				player.sendSystemMessage(ModularElectricityTextUtils.chatMessage("guidebookclick").withStyle(ChatFormatting.BOLD, ChatFormatting.RED).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, LINK))));
				return InteractionResultHolder.pass(player.getItemInHand(handIn));
			}
		} else if(!player.isShiftKeyDown()) {
			player.openMenu(getMenuProvider(world, player));
		}
		return super.use(world, player, handIn);
	}

	public MenuProvider getMenuProvider(Level world, Player player) {
		return new SimpleMenuProvider((id, inv, play) -> new ContainerGuidebook(id, player.getInventory()), CONTAINER_TITLE);
	}

}
