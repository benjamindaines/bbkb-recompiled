package dev.bbkb.ime.core.settings.screens.keyeditor

/**
 * Physical-key capture for the PKB custom-symbol page editor.
 *
 * The editor grid and `KeyboardBuilder` both index the custom-symbol layout positionally
 * (`layout[i]` addresses the i-th `customSymbolKeyStyle` key). Hardware key events expose
 * `KeyEvent.getScanCode()`, whose values are the `keyMapScanCode` attributes declared in
 * `rows_symbols_custom_pkb.xml` and are invariant across qwerty/qwertz/azerty physical variants,
 * which alter only the printed glyph. Mapping a captured scancode to the positional layout index
 * therefore reuses the ordering the builder and editor already share, without touching storage,
 * persistence, or keyboard assembly.
 *
 * The order below matches [PkbSymbolPageSpec]'s rows: ten keys (slots 0..9), nine keys (slots
 * 10..18), then the seven letter keys and the currency key (slots 19..26).
 */
internal object PkbSymbolKeyCapture {

    // Scancodes in positional order, as decimal equivalents of the keyMapScanCode values in
    // rows_symbols_custom_pkb.xml.
    private val SCAN_CODES_IN_ORDER = intArrayOf(
        16, 17, 18, 19, 20, 21, 22, 23, 24, 25, // row 0, slots 0..9
        30, 31, 32, 33, 34, 35, 36, 37, 38,     // row 1, slots 10..18
        44, 45, 46, 47, 48, 49, 50,             // row 2 letters, slots 19..25
        5,                                      // row 2 currency key, slot 26
    )

    private val INDEX_BY_SCAN_CODE: Map<Int, Int> =
        SCAN_CODES_IN_ORDER.withIndex().associate { (index, scanCode) -> scanCode to index }

    /**
     * The positional custom-symbol slot a hardware scancode addresses, or -1 for a scancode outside
     * the custom-symbol key set.
     */
    fun mapScanToIndex(scanCode: Int): Int = INDEX_BY_SCAN_CODE[scanCode] ?: -1
}
