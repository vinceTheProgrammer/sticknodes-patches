package io.github.vincetheprogrammer.patches.sticknodes.limits

import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patcher.extensions.InstructionExtensions.removeInstruction

val removeNodeLimitPatch = bytecodePatch(
    name = "Remove Node Limit",
    description = "Removes the node limit.",
) {
    compatibleWith(
        "org.fortheloss.sticknodes"("4.2.3"),
        "org.fortheloss.sticknodespro"("4.2.3")
    )

    execute {

        val ifInstruction = touchDownFingerprint.patternMatch!!.endIndex;

        touchDownFingerprint.method.removeInstruction(ifInstruction);

    }
}
