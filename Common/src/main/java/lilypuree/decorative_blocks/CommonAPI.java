package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class CommonAPI {

    public static EntityType.EntityFactory<DummyEntityForSitting> dummyEntityFactory;
}
