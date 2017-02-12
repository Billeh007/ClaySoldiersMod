/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import de.sanandrew.core.manpack.managers.SAPUpdateManager;
import de.sanandrew.core.manpack.managers.SAPUpdateManager.Version;
import de.sanandrew.mods.claysoldiers.dispenser.BehaviorDisruptorDispenseItem;
import de.sanandrew.mods.claysoldiers.dispenser.BehaviorDollDispenseItem;
import de.sanandrew.mods.claysoldiers.network.PacketManager;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffects;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc.UpgradeFood;
import de.sanandrew.mods.claysoldiers.world.gen.WorldGenerator;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = ClaySoldiersMod.MOD_ID, version = ClaySoldiersMod.VERSION, name = "Clay Soldiers Mod", guiFactory = ClaySoldiersMod.MOD_GUI_FACTORY,
     dependencies = "required-after:sapmanpack@[2.4.0,)")
public final class ClaySoldiersMod
{
    public static final String MOD_ID = "claysoldiers";
    public static final String VERSION = "2.0.0-beta.2";
    public static final String MOD_LOG = "ClaySoldiers";
    public static final String MOD_CHANNEL = "ClaySoldiersNWCH";
    public static final String MOD_GUI_FACTORY = "de.sanandrew.mods.claysoldiers.client.gui.ModGuiFactory";
    public static final EventBus EVENT_BUS = new EventBus();

    private static final String MOD_PROXY_CLIENT = "de.sanandrew.mods.claysoldiers.client.util.ClientProxy";
    private static final String MOD_PROXY_COMMON = "de.sanandrew.mods.claysoldiers.util.CommonProxy";

    @Instance(ClaySoldiersMod.MOD_ID)
    public static ClaySoldiersMod instance;
    @SidedProxy(modId = ClaySoldiersMod.MOD_ID, clientSide = ClaySoldiersMod.MOD_PROXY_CLIENT, serverSide = ClaySoldiersMod.MOD_PROXY_COMMON)
    public static CommonProxy proxy;

    public static CreativeTabs clayTab = new CreativeTabClaySoldiers();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        SAPUpdateManager.createUpdateManager("Clay Soldiers Mod", new Version(VERSION),
                                             "https://raw.githubusercontent.com/SanAndreasP/ClaySoldiersMod/master/update.json",
                                             "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/1286957",
                                             event.getSourceFile());

        event.getModMetadata().autogenerated = false;

        ModConfig.config = new Configuration(event.getSuggestedConfigurationFile());
        ModConfig.syncConfig();

        RegistryItems.initialize();
        RegistryBlocks.initialize();
        ClaymanTeam.initialize();

        UpgradeFood.excludeFood((ItemFood) Items.potato);
        UpgradeFood.excludeFood((ItemFood) Items.carrot);

        GameRegistry.registerWorldGenerator(new WorldGenerator(), 100);

        PacketManager.registerPackets();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        SoldierEffects.initialize();
        SoldierUpgrades.initialize();
        RegistryRecipes.initialize();

        FMLCommonHandler.instance().bus().register(this);

        proxy.modInit();

        RegistryEntities.registerEntities(this);

        BehaviorDollDispenseItem bdiDoll = new BehaviorDollDispenseItem();
        BehaviorDisruptorDispenseItem bdiDisrupt = new BehaviorDisruptorDispenseItem();
        BlockDispenser.dispenseBehaviorRegistry.putObject(RegistryItems.dollSoldier, bdiDoll);
        BlockDispenser.dispenseBehaviorRegistry.putObject(RegistryItems.dollHorseMount, bdiDoll);
        BlockDispenser.dispenseBehaviorRegistry.putObject(RegistryItems.dollGeckoMount, bdiDoll);
        BlockDispenser.dispenseBehaviorRegistry.putObject(RegistryItems.dollTurtleMount, bdiDoll);
        BlockDispenser.dispenseBehaviorRegistry.putObject(RegistryItems.dollBunnyMount, bdiDoll);
        BlockDispenser.dispenseBehaviorRegistry.putObject(RegistryItems.disruptor, bdiDisrupt);
        BlockDispenser.dispenseBehaviorRegistry.putObject(RegistryItems.disruptorHardened, bdiDisrupt);
    }

    @EventHandler
    public void modIMC(IMCEvent event) {
        //TODO here comes the code for registering stuff recieved from external mods
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        SoldierUpgrades.logUpgradeCount();
    }

    @SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent eventArgs) {
        if( eventArgs.modID.equals(MOD_ID) ) {
            ModConfig.syncConfig();
        }
    }
}