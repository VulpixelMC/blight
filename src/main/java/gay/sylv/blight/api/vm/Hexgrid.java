package gay.sylv.blight.api.vm;

import org.jetbrains.annotations.NotNull;

// TODO: Design and implement Hexgrid API
public class Hexgrid {
	private boolean textMode = true;
	private @NotNull String content;
	private boolean dirty;
	private Nature[] cachedNatures = new Nature[0];

	public Hexgrid(@NotNull String content) {
		this.content = content;
	}

	public Hexgrid() {
		this.content = "";
	}

	public @NotNull String getContent() {
		return content;
	}

	public void setContent(@NotNull String content) {
		this.content = content;
		this.dirty = true;
	}

	public Nature[] interpret() {
		if (!dirty && cachedNatures != null) return cachedNatures;

		// Interpret Hexgrid as array of Natures
		final String parsed = content
				.replaceAll("#.*", "")
				.replaceAll("\n", "");
		this.cachedNatures = new Nature[parsed.length()];
		for (int i = 0; i < parsed.length(); i++) {
			cachedNatures[i] = Nature.fromSymbol(parsed.charAt(i));
		}

		return cachedNatures;
	}
}
