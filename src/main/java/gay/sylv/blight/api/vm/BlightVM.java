package gay.sylv.blight.api.vm;

import gay.sylv.blight.api.util.Nothing;
import gay.sylv.blight.api.vm.error.BlightError;
import gay.sylv.blight.api.vm.error.BlightResult;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * <h1>Blight Virtual Machine</h1>
 * The <b>Blight Virtual Machine</b> emulates the language of Blight: Natures and Hexes.
 *
 * <h2>Input</h2>
 * Input can either be an array of Natures or a Hexgrid of Natures.
 */
public final class BlightVM {
	private int pc;
	@Unmodifiable
	private List<Nature> instructions;
	private final Stack<BlightObject> stack = new Stack<>();
	private final Int2ObjectMap<BlightObject> variables = new Int2ObjectOpenHashMap<>();

	// -- VM Operation --

	/**
	 * Increment the program counter.
	 * @param pc The program counter.
	 */
	public void incPc(int pc) {
		this.pc++;
	}

	/**
	 * Get the program counter.
	 * @return The program counter.
	 */
	public int getPc() {
		return pc;
	}

	/**
	 * Set the read-only instructions.
	 * @param instructions An unmodifiable list of instructions.
	 */
	public void setInstructions(@NotNull List<Nature> instructions) {
		this.instructions = instructions;
	}

	/**
	 * Set the read-only instructions using a Hexgrid.
	 * @param hexgrid The Hexgrid to interpret as a list of instructions.
	 */
	public void setInstructions(@NotNull Hexgrid hexgrid) {
		this.instructions = List.of(hexgrid.interpret());
	}

	/**
	 * Clear the stack and variables, delete the list of instructions,
	 * and reset the program counter.
	 */
	public void initialize() {
		pc = 0;
		clearStack();
		clearVariables();
		instructions = null;
	}

	/**
	 * Execute the current instruction.
	 * @return The result of execution.
	 */
	public BlightResult<Nothing> execute() {
		// Execute current instruction

		return BlightResult.success(Nothing.INSTANCE);
	}

	/**
	 * Execute all instructions until a halt happens or execution finishes.
	 * @return The result of execution.
	 */
	public BlightResult<Nothing> executeAll() {
		for (int pc = this.pc; pc < instructions.size(); pc++) {
			BlightResult<Nothing> result = execute();
			if (result.isError()) return result;
		}

		return BlightResult.success(Nothing.INSTANCE);
	}

	// -- Stack --

	/**
	 * Push a {@link BlightObject} to the Stack.
	 * @param object The {@link BlightObject}.
	 */
	public void push(BlightObject object) {
		this.stack.push(object);
	}

	/**
	 * Pop the last {@link BlightObject} from the Stack.
	 * @return The last {@link BlightObject} on the Stack.
	 */
	public BlightObject pop() {
		return this.stack.pop();
	}

	/**
	 * Clear the Stack.
	 */
	public void clearStack() {
		this.stack.clear();
	}

	/**
	 * @return An unmodifiable copy of the Stack.
	 */
	public @Unmodifiable List<BlightObject> getStack() {
		return List.copyOf(this.stack);
	}

	// -- Variables --

	/**
	 * Set a variable.
	 * @param index The variable's index.
	 * @param object The value of the variable.
	 */
	public void setVar(int index, BlightObject object) {
		this.variables.put(index, object);
	}

	/**
	 * Get a variable or fail if it doesn't exist.
	 * @param i The variable's index.
	 * @return The variable wrapped in a result
	 */
	public BlightResult<BlightObject> getVar(int i) {
		return BlightResult.fromNullable(
				this.variables.getOrDefault(i, null),
				BlightError.NO_ENTITY
		);
	}

	/**
	 * Clear the variables.
	 */
	public void clearVariables() {
		this.variables.clear();
	}

	/**
	 * @return The variables as an unmodifiable {@link Int2ObjectMap}.
	 */
	public @Unmodifiable Int2ObjectMap<BlightObject> getVariables() {
		return (Int2ObjectMap<BlightObject>) Map.copyOf(this.variables);
	}
}
