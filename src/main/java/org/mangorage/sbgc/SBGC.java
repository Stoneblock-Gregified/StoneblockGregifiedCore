package org.mangorage.sbgc;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SBGC.MOD_ID)
public final class SBGC {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "sbgc";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public SBGC() {

    }

}
