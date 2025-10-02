package com.HZ.CustomFilters

import app.revanced.patcher.fingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

val figureFiltersInitFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC)
    returns("V") // constructor/initializer methods are void
    parameters()
    // The method we’re after has many `INVOKE_DIRECT` and `INVOKE_VIRTUAL`
    // calls for LabelInputIncrementField and LabelColorInputIncrementField
    opcodes(
        Opcode.NEW_INSTANCE,
        Opcode.INVOKE_DIRECT,
        Opcode.IPUT_OBJECT,
        Opcode.INVOKE_VIRTUAL
    )
    custom { method, classDef ->
        classDef.type == "Lorg/fortheloss/sticknodes/animationscreen/modules/tooltables/FigureFiltersToolTable;" &&
                method.name == "<init>" // constructor is where UI widgets are built
    }
}
