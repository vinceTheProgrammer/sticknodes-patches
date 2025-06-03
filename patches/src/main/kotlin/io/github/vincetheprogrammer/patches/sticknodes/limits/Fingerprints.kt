package io.github.vincetheprogrammer.patches.sticknodes.limits

import app.revanced.patcher.fingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

val touchDownFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC)
    returns("Z")
    opcodes(
        Opcode.INVOKE_VIRTUAL,
        Opcode.MOVE_RESULT,
        Opcode.CONST_16,
        Opcode.IF_GE
    )
    parameters("Lcom/badlogic/gdx/scenes/scene2d/InputEvent;", "F", "F", "I", "I")
    custom { method, classDef -> classDef.type == "Lorg/fortheloss/sticknodes/animationscreen/modules/CanvasModule$2;" }
}