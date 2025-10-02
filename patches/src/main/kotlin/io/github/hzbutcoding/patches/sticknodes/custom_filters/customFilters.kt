package com.HZ.CustomFilters

import app.revanced.patcher.fingerprint
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.util.proxy.mutableTypes.MutableMethod
import app.revanced.patcher.util.proxy.mutableTypes.MutableMethod.Companion.toMutable
import com.HZ.CustomFilters.figureFiltersInitFingerprint

// Fingerprint: tweak class/method checks to match the real target in your APK
val customFilterFingerprint = fingerprint {
    // match a void method (likely constructor or init method that creates the UI)
    returns("V")
    parameters() // no params — change if real target has params

    // Narrow by class + method name. Replace with the real class and method from JADX.
    custom { method, classDef ->
        classDef.type == "Lorg/fortheloss/sticknodes/animationscreen/modules/tooltables/FigureFiltersToolTable;"
                && (method.name == "<init>" || method.name == "initFilters" || method.name == "createUI")
    }
}

@Suppress("unused")
val AddCustomFilterSlot = bytecodePatch(
    name = "Custom Filter slot",
    description = "Adds a placeholder injection so patch project compiles; replace nop with real smali later."
) {
    compatibleWith(
        "org.fortheloss.sticknodes"("4.2.5"),
        "org.fortheloss.sticknodespro"("4.2.5"),
        "org.fortheloss.sticknodesbeta"("4.2.6")
    )

    // If you have an extension file, keep this; otherwise remove
    // extendWith("extensions/extension.rve")

    // inside your bytecodePatch { execute { ... } } block
    execute {
        // fingerprint value declared elsewhere, assumed to be in scope:
        // val figureFiltersInitFingerprint = fingerprint { ... }

        val fpObj = figureFiltersInitFingerprint as Any

        // helper: try to invoke no-arg methods on an object (invoke, getMatch, match, etc.)
        fun tryInvokeNoArg(obj: Any, names: List<String>): Any? {
            for (n in names) {
                try {
                    val m = obj::class.java.getMethod(n)
                    if (m.parameterCount == 0) return m.invoke(obj)
                } catch (_: Throwable) { /* ignore and continue */
                }
            }
            return null
        }

        // helper: try to get declared fields on an object (result, match, etc.)
        fun tryGetField(obj: Any, names: List<String>): Any? {
            for (n in names) {
                try {
                    val f = obj::class.java.getDeclaredField(n)
                    f.isAccessible = true
                    return f.get(obj)
                } catch (_: Throwable) { /* ignore */
                }
            }
            return null
        }

        // 1) obtain the fingerprint match (FingerprintResult?) via several possible APIs
        val match =
            tryInvokeNoArg(fpObj, listOf("invoke", "match", "getMatch", "get")) // try operator invoke or getMatch()
                ?: tryGetField(fpObj, listOf("result", "match", "_result")) // try result field
                ?: tryInvokeNoArg(fpObj, listOf("get")) // last resort

        if (match == null) {
            // fingerprint didn't match or we couldn't access the API — nothing to do
            return@execute
        }

        // 2) obtain the mutable method object from the match via several possible names
        val methodObj = tryInvokeNoArg(
            match,
            listOf("getMutableMethod", "toMutableMethod", "toMutable", "mutableMethod", "getMethod", "method")
        )
            ?: tryGetField(match, listOf("mutableMethod", "method"))

        if (methodObj == null) {
            // couldn't obtain a mutable method proxy — bail out gracefully
            return@execute
        }

        // 3) attempt to add an instruction. We try:
        //   a) call addInstruction(...) directly on the method object (if it exposes it),
        //   b) call the Kotlin extension function InstructionExtensions.addInstruction(method, instr) via reflection,
        //   c) as a last resort, push a string into a mutable 'instructions' field on the method object.
        var injected = false

        // (a) try instance method named addInstruction(String)
        try {
            val addInstMethod =
                methodObj::class.java.methods.firstOrNull { it.name == "addInstruction" && it.parameterCount == 1 && it.parameterTypes[0] == String::class.java }
            if (addInstMethod != null) {
                addInstMethod.invoke(methodObj, "nop") // safe test instruction; replace with real smali later
                injected = true
            }
        } catch (_: Throwable) {
        }

        // (b) try calling a top-level instruction helper (InstructionExtensions) that many patches use
        if (!injected) {
            try {
                // try Kotlin generated holder first (InstructionExtensionsKt), fallback to InstructionExtensions
                val candidateClasses = listOf(
                    "app.revanced.patcher.extensions.InstructionExtensionsKt",
                    "app.revanced.patcher.extensions.InstructionExtensions"
                )
                for (cn in candidateClasses) {
                    try {
                        val extCls = Class.forName(cn)
                        val addStatic =
                            extCls.methods.firstOrNull { it.name == "addInstruction" && it.parameterCount == 2 }
                        if (addStatic != null) {
                            // invoke static ext.addInstruction(methodObj, "nop")
                            addStatic.invoke(null, methodObj, "nop")
                            injected = true
                            break
                        }
                    } catch (_: Throwable) {
                        /* try next candidate */
                    }
                }
            } catch (_: Throwable) {
            }
        }

        // (c) fallback: mutate instructions list field if present
        if (!injected) {
            try {
                val instrField = methodObj::class.java.getDeclaredField("instructions")
                instrField.isAccessible = true
                val instrList = instrField.get(methodObj)
                if (instrList is MutableList<*>) {
                    @Suppress("UNCHECKED_CAST")
                    (instrList as MutableList<Any?>).add("nop")
                    injected = true
                }
            } catch (_: Throwable) {
            }
        }

        // done: if injected == false you can log/inspect, but we return harmlessly
    }
}





//package com.HZ.CustomFilters
//
//import app.revanced.patcher.patch.bytecodePatch
//import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
//import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
//import app.revanced.patcher.extensions.InstructionExtensions.removeInstruction
//import app.revanced.patcher.extensions.InstructionExtensions.removeInstructions
//
//
//@Suppress("unused")
//val AddCustomFilterSlot = bytecodePatch(
//    name = "Custom Filter slot",
//    description = "adds an extra custom filter slot for prototyping new shaders",
//) {
//    compatibleWith(
//        "org.fortheloss.sticknodes"("4.2.3"),
//        "org.fortheloss.sticknodespro"("4.2.3")
//        )
//
////    extendWith("extensions/extension.rve")
//
//    execute {
//        val method = figureFiltersInitFingerprint.resultOrThrow().method;
//
//
//        // Insert call to your helper at the end of the constructor
//        method.addInstruction(
//            method.implementation!!.instructions.size - 1, // right before return-void
//            "invoke-static {}, Lcom/HZ/CustomFilters/CustomMenu;->addCustomFilter()V"
//        )
//    }
//}
