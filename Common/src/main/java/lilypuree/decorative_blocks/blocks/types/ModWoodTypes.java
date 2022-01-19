package lilypuree.decorative_blocks.blocks.types;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ModWoodTypes {
    private static final Set<IWoodType> modWoodTypes = findWoodTypes();

    private static Set<IWoodType> findWoodTypes(){
        Set<IWoodType> woodTypes = Arrays.stream(VanillaWoodTypes.values()).collect(Collectors.toSet());
//        if(ModList.get().isLoaded("biomesoplenty")){
//            woodTypes.addAll(Arrays.stream(BOPWoodTypes.values()).collect(Collectors.toSet()));
//        }
        return woodTypes;
    }

    public static Set<IWoodType> allWoodTypes(){
        return modWoodTypes;
    }
}
