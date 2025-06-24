package gay.sylv.blight.api.vm.error;

import java.util.function.Predicate;

import gay.sylv.blight.api.vm.BlightVM;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The result of an operation in a {@link BlightVM}.
 * @param <T> The result's type.
 */
public final class BlightResult<T> {
	private final T result;
	private final BlightError error;
	private final Object[] errorArgs;

	private BlightResult(@NotNull T result) {
		this.result = result;
		this.error = null;
		this.errorArgs = null;
	}

	private BlightResult(@NotNull BlightError error, Object ...errorArgs) {
		this.result = null;
		this.error = error;
		this.errorArgs = errorArgs;
	}

	/**
	 * Create a {@link BlightResult} for a successful result.
	 * @param result The result.
	 * @return The result wrapped in a {@link BlightResult}.
	 * @param <T> The result's type.
	 */
	public static <T> BlightResult<T> success(@NotNull T result) {
		return new BlightResult<>(result);
	}

	/**
	 * Create a {@link BlightResult} for an error.
	 * @param error The error that occurred.
	 * @param errorArgs The error's arguments.
	 * @return The error wrapped in a {@link BlightResult}.
	 * @param <T> The normal result's type.
	 */
	public static <T> BlightResult<T> error(@NotNull BlightError error, Object ...errorArgs) {
		return new BlightResult<>(error, errorArgs);
	}

	/**
	 * Creates a {@link BlightResult} from a nullable result where {@code null} indicates failure.
	 * @param result The result or {@code null} if failed.
	 * @param error The error if the result is {@code null}.
	 * @return The error or result wrapped in a {@link BlightResult}.
	 * @param <T> The normal result's type.
	 */
	public static <T> BlightResult<T> fromNullable(@Nullable T result, BlightError error) {
		return result == null ? error(error) : success(result);
	}

	/**
	 * Creates a {@link BlightResult} from a nullable result where {@code null} or the predicate returning {@code false} indicates failure.
	 * @param result The result or {@code null} if failed.
	 * @param error The error if the result is {@code null} or if the predicate is {@code false}.
	 * @return The error or result wrapped in a {@link BlightResult}.
	 * @param <T> The normal result's type.
	 */
	public static <T> BlightResult<T> fromPredicate(@Nullable T result, BlightError error,  Predicate<T> predicate) {
		if (result != null) {
			return predicate.test(result) ? error(error) : success(result);
		} else {
			return error(error);
		}
	}

	/**
	 * Asserts that the result exists.
	 * @return The result.
	 * @throws AssertionError If the result does not exist.
	 */
	public @NotNull T assertResult() {
		if (result == null) {
			throw new AssertionError("Result is null");
		}

		return result;
	}

	/**
	 * Asserts that an error occurred.
	 * @return The error.
	 * @throws AssertionError If the error does not exist.
	 */
	public @NotNull BlightError assertError() {
		if (error == null) {
			throw new AssertionError("Error is null");
		}

		return error;
	}

	/**
	 * @return Whether an error occurred.
	 */
	public boolean isError() {
		return error != null;
	}
}
