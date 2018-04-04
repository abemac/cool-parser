package symbolHandling;

public class IdTable extends AbstractTable<IdSymbol> {
	/**
	 * Creates a new IdSymbol object.
	 * 
	 * @see IdSymbol
	 */
	protected IdSymbol getNewSymbol(String s, int len, int index) {
		return new IdSymbol(s, len, index);
	}
}
