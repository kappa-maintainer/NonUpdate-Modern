package top.outlands.nonupdate.mixin;

import com.mna.config.SpellConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;
import java.util.function.Function;

@Mixin(value = SpellConfig.class, remap = false)
public class MixinSpellConfig {
    @Redirect(method = "getConfiguredValue", at = @At(value = "INVOKE", target = "Ljava/util/HashMap;computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;"))
    private static Object cia(HashMap instance, Object k, Function f) {
        if (instance.containsKey(k)) {
            return instance.get(k);
        } else {
            var v = f.apply(k);
            instance.put(k, v);
            return v;
        }
    }
}
