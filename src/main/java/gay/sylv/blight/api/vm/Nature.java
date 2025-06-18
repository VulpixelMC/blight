package gay.sylv.blight.api.vm;

import it.unimi.dsi.fastutil.chars.Char2IntArrayMap;
import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.Char2IntOpenHashMap;

/**
 * <h1>Nature</h1>
 * Defines the characteristics of Natures used in constructing Spells and Hexes within the {@code gay.sylv.blight.api.vm} package.
 *
 * <p>A Nature represents a type of plant that can be used to construct Spells. When Perception is consumed within a Spell, it produces Blighted Ash. Each Spell and Hex is composed of specific Natures, with Spells being groups of these Natures.</p>
 *
 * <p>Hexes are particular types of Spells that end with {@code E} and contain only one {@code E}. These letters represent each Nature involved in their composition.
 * <br>
 * Natures are instructions that the {@link BlightVM} executes.
 */
public enum Nature {
	Inverse('I'),
	Negative('N'),
	Creative('C'),
	Successive('S'),
	Double('2'),
	Triple('3'),
	Quintuple('5'),
	Septuple('7'),
	Additive('A'),
	Multiplicative('M'),
	Duplicative('D'),
	Permutative('P'),
	Executive('E'),
	Reflective('R'),
	Functional('F'),
	Grouping('G'),
	Semantic('Z'),;

	private static final Char2IntMap lookup = new Char2IntOpenHashMap();
	private final char symbol;

	static {
		for (Nature n : Nature.values()) {
			lookup.put(n.symbol, n.ordinal());
		}
	}

	Nature(char symbol) {
		this.symbol = symbol;
		if (!Character.isUpperCase(symbol)) {
			throw new IllegalArgumentException("Nature symbols must not be lowercase characters");
		}
	}

	public char getSymbol() {
		return symbol;
	}

	public static Nature fromSymbol(char symbol) {
		return Nature.values()[lookup.get(symbol)];
	}
}
