package top.outlands.nonupdate;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.HashSet;
import java.util.Set;

public class URLTransformer implements ITransformer<ClassNode> {

    @Override
    public @NotNull ClassNode transform(ClassNode classNode, ITransformerVotingContext iTransformerVotingContext) {
        if (classNode.methods != null) {
            for (MethodNode methodNode : classNode.methods) {
                if (methodNode.instructions != null) {
                    for (AbstractInsnNode abstractInsnNode : methodNode.instructions) {
                        if (abstractInsnNode instanceof MethodInsnNode methodInsnNode) {
                            if (methodInsnNode.owner.equals("java/net/URL") && methodInsnNode.name.equals("openConnection") && methodInsnNode.desc.equals("()Ljava/net/URLConnection;")) {
                                methodInsnNode.owner = "top/outlands/nonupdate/NonUpdateService";
                                methodInsnNode.desc = "(Ljava/net/URL;)Ljava/net/URLConnection;";
                                methodInsnNode.setOpcode(Opcodes.INVOKESTATIC);
                            }
                            if (methodInsnNode.owner.equals("java/net/URL") && methodInsnNode.name.equals("openStream") && methodInsnNode.desc.equals("()Ljava/io/InputStream;")) {
                                methodInsnNode.owner = "top/outlands/nonupdate/NonUpdateService";
                                methodInsnNode.desc = "(Ljava/net/URL;)Ljava/io/InputStream;";
                                methodInsnNode.setOpcode(Opcodes.INVOKESTATIC);
                            }
                        }
                    }
                }
            }
        }
        return classNode;
    }

    @Override
    public @NotNull TransformerVoteResult castVote(ITransformerVotingContext iTransformerVotingContext) {
        return TransformerVoteResult.YES;
    }

    @Override
    public @NotNull Set<Target> targets() {
        Set<Target> targetSet = new HashSet<>();
        for (String target : NonUpdateService.config.targets) {
            targetSet.add(ITransformer.Target.targetClass(target));
        }
        return targetSet;
    }
}
