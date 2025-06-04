package io.github.vincetheprogrammer.patches.sticknodes.limits

import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patcher.extensions.InstructionExtensions.removeInstruction
import app.revanced.patcher.extensions.InstructionExtensions.removeInstructions
import app.revanced.patcher.patch.rawResourcePatch

val removeNodeLimitPatch = bytecodePatch(
    name = "Remove Node Limit v1.1.0",
    description = "Removes the node limit.",
) {
    compatibleWith(
        "org.fortheloss.sticknodes"("4.2.3"),
        "org.fortheloss.sticknodespro"("4.2.3")
    )

    execute {

        val ifTouchDownMatchEnd = touchDownFingerprint.patternMatch!!.endIndex;
        touchDownFingerprint.method.removeInstruction(ifTouchDownMatchEnd);

        val ifToolTableMatch = setNodeCountToolTableFingerprint.patternMatch!!;
        val ifToolTableMatchStart = ifToolTableMatch.startIndex;
        val ifToolTableMatchEnd = ifToolTableMatch.endIndex;
        val countToolTable = (ifToolTableMatchEnd - ifToolTableMatchStart) + 1;
        setNodeCountToolTableFingerprint.method.removeInstructions(ifToolTableMatchStart, countToolTable)

        val setInfoLabelMatchStart = setInfoLabelToolTableFingerprint.patternMatch!!.startIndex;
        setInfoLabelToolTableFingerprint.method.addInstructions(setInfoLabelMatchStart + 2, """
            const-string v2, "/"
            invoke-virtual {v1, v2}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I
            move-result v3

            const/4 v2, 0x0
            invoke-virtual {v1, v2, v3}, Ljava/lang/String;->substring(II)Ljava/lang/String;
            move-result-object v1
            """)

        val paste1MatchStart = setPasteSegment1Fingerprint.patternMatch!!.startIndex;
        setPasteSegment1Fingerprint.method.removeInstruction(paste1MatchStart);

        val paste2MatchStart = setPasteSegment2Fingerprint.patternMatch!!.startIndex;
        setPasteSegment2Fingerprint.method.removeInstruction(paste2MatchStart);

        val split1MatchStart = splitSegmentFingerprint1.patternMatch!!.startIndex;
        splitSegmentFingerprint1.method.addInstruction(split1MatchStart, "const/4 v1, 0x0");

        val split2MatchEnd = splitSegmentFingerprint2.patternMatch!!.endIndex + 1;
        splitSegmentFingerprint2.method.removeInstruction(split2MatchEnd);

    }
}
