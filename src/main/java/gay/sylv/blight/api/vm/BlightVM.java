package gay.sylv.blight.api.vm;

import gay.sylv.blight.api.util.Nothing;
import gay.sylv.blight.api.vm.error.BlightResult;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Blight Virtual Machine</h1>
 * The <b>Blight Virtual Machine</b> emulates the language of Blight: Natures and Hexes.
 *
 * <h2>Input</h2>
 * Input can either be an array of Natures or a Hexgrid of Natures.
 */
public final class BlightVM {
	private int pc;
	private List<Nature> instructions;
	// TODO: (Lily) use a type that a real stack would use. Also implement stack.
	private ArrayDeque<BlightObject> stack;
	// TODO: implement methods for getting and setting variables.
	private BlightObject[] variables;

	public BlightVM() {}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public int getPc() {
		return pc;
	}

	public void setInstructions(List<Nature> instructions) {
		this.instructions = instructions;
	}

	public void setInstructions(Hexgrid hexgrid) {
		this.instructions = Arrays.asList(hexgrid.interpret());
	}

	public BlightResult<Nothing> execute() {
		// Execute current instruction

		pc++;
		
		return BlightResult.success(new Nothing());
	}
}
