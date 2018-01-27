/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import de.sanandrew.mods.sanlib.lib.Tuple;

import java.util.Objects;

@FunctionalInterface
public interface ParticleFunc
{
    void accept(EnumParticle particle, int dim, double x, double y, double z, Tuple additData);

    default ParticleFunc andThen(ParticleFunc after) {
        Objects.requireNonNull(after);
        return (particle, dim, x, y, z, additData) -> {accept(particle, dim, x, y, z, additData); after.accept(particle, dim, x, y, z, additData);};
    }
}
