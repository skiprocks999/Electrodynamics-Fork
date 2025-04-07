package voltaicapi.api.radiation.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;

public record BlockPosVolume(BlockPos start, BlockPos end) {

    public static final Codec<BlockPosVolume> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.fieldOf("start").forGetter(BlockPosVolume::start),
            BlockPos.CODEC.fieldOf("end").forGetter(BlockPosVolume::end)
    ).apply(instance, BlockPosVolume::new));

    public boolean isIn(BlockPos pos) {
        if (start().getX() > pos.getX() || end().getX() < pos.getX()) {
            return false;
        }
        if (start().getY() > pos.getY() || end().getY() < pos.getY()) {
            return false;
        }
        if (start().getZ() > pos.getZ() || end().getZ() < pos.getZ()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BlockPosVolume other) {
            return other.start().equals(this.start()) && other.end().equals(this.end());
        }
        return false;
    }
}
