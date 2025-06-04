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

val setNodeCountToolTableFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC)
    returns("V")
    parameters("I")
    opcodes(
        Opcode.CONST_16,
        Opcode.IF_LE,
        Opcode.IPUT
    )
    custom { method, classDef -> classDef.type == "Lorg/fortheloss/sticknodes/animationscreen/modules/tooltables/PermanentCreationToolTable;" && method.name == "setNodeCount" }
}

val setInfoLabelToolTableFingerprint = fingerprint {
    accessFlags(AccessFlags.PRIVATE)
    returns("V")
    parameters()
    opcodes(
        Opcode.INVOKE_STATIC,
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.INVOKE_VIRTUAL
    )
    custom { method, classDef -> classDef.type == "Lorg/fortheloss/sticknodes/animationscreen/modules/tooltables/PermanentCreationToolTable;" && method.name == "setInfoLabel" }
}

val setPasteSegment1Fingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC)
    returns("V")
    parameters("Lorg/fortheloss/sticknodes/stickfigure/StickNode;", "Lorg/fortheloss/sticknodes/stickfigure/StickNode;", "Z", "Ljava/util/ArrayList;", "Lorg/fortheloss/sticknodes/stickfigure/StickNode;")
    opcodes(
        Opcode.IF_GT
    )
    custom { method, classDef -> classDef.type == "Lorg/fortheloss/sticknodes/animationscreen/modules/CanvasModule;" && method.name == "pasteSegment" }
}

val setPasteSegment2Fingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC)
    returns("V")
    parameters("Lorg/fortheloss/sticknodes/stickfigure/StickNode;", "Lorg/fortheloss/sticknodes/stickfigure/StickNode;", "Z", "Ljava/util/ArrayList;", "Lorg/fortheloss/sticknodes/stickfigure/StickNode;", "Ljava/util/ArrayList;", "Ljava/util/ArrayList;")
    opcodes(
        Opcode.IF_GT
    )
    custom { method, classDef -> classDef.type == "Lorg/fortheloss/sticknodes/animationscreen/modules/CanvasModule;" && method.name == "pasteSegment" }
}

val splitSegmentFingerprint1 = fingerprint {
    accessFlags(AccessFlags.PUBLIC)
    returns("V")
    parameters("Lorg/fortheloss/sticknodes/stickfigure/StickNode;", "I")
    custom { method, classDef -> classDef.type == "Lorg/fortheloss/sticknodes/animationscreen/modules/CanvasModule;" && method.name == "splitSegment" }
    opcodes(
        Opcode.IF_LE
    )
}

val splitSegmentFingerprint2 = fingerprint {
    accessFlags(AccessFlags.PUBLIC)
    returns("V")
    parameters("Lorg/fortheloss/sticknodes/stickfigure/StickNode;", "I")
    custom { method, classDef -> classDef.type == "Lorg/fortheloss/sticknodes/animationscreen/modules/CanvasModule;" && method.name == "splitSegment" }
    opcodes(
        Opcode.CONST_16,
        Opcode.IF_GT
    )
}