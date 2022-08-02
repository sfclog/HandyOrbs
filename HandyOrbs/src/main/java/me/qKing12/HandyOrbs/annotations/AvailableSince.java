package me.qKing12.HandyOrbs.annotations;


import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;

import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;

@Retention(RUNTIME)

public @interface AvailableSince {

	MinecraftVersion version();

}