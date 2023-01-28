import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Serves as a factory used to parse line data and build Items of various types.
 */
public class ItemFromFileFactory {
//	Used to map item type info given by file data to a corresponding Item builder method
	public static final Map<String, Function<String[], Item>> TYPE_GENERATOR_MAP =
		Map.of(
			"book", ItemFromFileFactory::buildBook,
			"music", ItemFromFileFactory::buildMusicCD,
			"software", ItemFromFileFactory::buildSoftware
		);
	
	/**
	 * Build Item of Book type from file data
	 * @param fileEntry String array containing unparsed data used to initialize a Book
	 * @return Book
	 */
	public static Book buildBook (String[] fileEntry) {
		return new Book(fileEntry[1], Double.parseDouble(fileEntry[6]), fileEntry[2], fileEntry[3],
			fileEntry[4], Integer.parseInt(fileEntry[5]));
	}
	
	/**
	 * Build Item of MusicCD type from file data
	 * @param fileEntry String array containing unparsed data used to initialize a MusicCD
	 * @return MusicCD
	 */
	public static MusicCD buildMusicCD (String[] fileEntry) {
		Date date;
		try {
			date = new Date(fileEntry[3]);
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("Error parsing date from " + fileEntry[3]);
		}
		return new MusicCD(fileEntry[1], Double.parseDouble(fileEntry[8]), fileEntry[2],
			date, fileEntry[4], fileEntry[5], Integer.parseInt(fileEntry[6]),
			fileEntry[7]);
	}
	
	/**
	 * Build Item of Software type from file data
	 * @param fileEntry String array containing unparsed data used to initialize a Software
	 * @return Software
	 */
	static Software buildSoftware (String[] fileEntry) {
		return new Software(fileEntry[1], Double.parseDouble(fileEntry[3]), fileEntry[2]);
	}
	
	/**
	 * Build an Item (of any type mapped in TYPE_GENERATOR_MAP) from file data
	 * @param fileEntry String array containing unparsed data used to initialize an Item
	 * @return Item
	 */
	public static Item build (String[] fileEntry) {
		String itemType = fileEntry[0];
		return TYPE_GENERATOR_MAP.get(itemType).apply(fileEntry);
	}
}