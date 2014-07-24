package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeGunpowder
    extends AUpgradeMisc
{
    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgInst, ItemStack stack) {
        if( stack.getItem() == Items.gunpowder ) {
            this.consumeItem(stack, upgInst);
            clayMan.playSound("random.pop", 1.0F, 1.0F);
        } else if( stack.getItem() == Item.getItemFromBlock(Blocks.tnt) ) {
            clayMan.playSound("dig.glass", 1.0F, 1.0F);
        }
    }

    @Override
    public void onSoldierDeath(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source) {
        if( !(source.getEntity() instanceof EntityPlayer) ) {
            clayMan.worldObj.createExplosion(clayMan, clayMan.posX, clayMan.posY, clayMan.posZ, 0.5F, false);
        }
    }
}