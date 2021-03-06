/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ISoldierUpgrade
{
    @Nonnull
    ItemStack[] getStacks();

    @Nonnull
    EnumUpgradeType getType(IHandedUpgradeable checker);

    String getModId();

    String getShortName();

    default int getPriority() { return 0; }

    default boolean syncData() { return false; }

    default boolean syncNbtData() { return false; }

    default void writeSyncData(ByteBuf buf, NBTTagCompound nbt) { }

    default void readSyncData(ByteBuf buf, NBTTagCompound nbt) { }

    default boolean isApplicable(ISoldier<?> soldier, ItemStack stack) { return true; }

    default void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) { }

    default void onPickup(ISoldier<?> soldier, EntityItem item, ISoldierUpgradeInst upgradeInst) { }

    default void onTick(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst) { }

    default void onLoad(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, NBTTagCompound nbt) { }

    default void onSave(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, NBTTagCompound nbt) { }

    default void onDestroyed(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst) { }

    default void onUpgradeDestroyed(ISoldier soldier, ISoldierUpgradeInst upgradeInst, ISoldierUpgradeInst destroyedUpgInst) { }

    default void onUpgradeAdded(ISoldier soldier, ISoldierUpgradeInst upgradeInst, ISoldierUpgradeInst addedUpgInst) { }

    default void onAttack(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target, @Nullable DamageSource dmgSource, @Nullable MutableFloat damage) { }

    default void onAttackSuccess(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target) { }

    default void onDamaged(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity attacker, DamageSource dmgSource, MutableFloat damage) { }

    default void onDamagedSuccess(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity attacker, DamageSource dmgSource, float damage) { }

    default void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) { }

    default void onSetFire(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, MutableInt fireInTicks) { }
}
