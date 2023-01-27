import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemFromFileFactory
{
    public final static Map<String, String> CLASSNAME_TYPENAME_MAP =
            Stream.of(new String[][]{
                {"Book", "book"},
                {"MusicCD", "music"},
                {"Software", "software"}
            }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    private static interface Command {Item execute(String[] fileEntry);
    }
    private static final Map<String, Command> TYPE_GENERATOR_MAP = Map.of(
        "book", ItemFromFileFactory::buildBook,"music", ItemFromFileFactory::buildMusicCD,"software",
        ItemFromFileFactory::buildSoftware);
    
    private static Book buildBook(String[] fileEntry)
    {
        return new Book(fileEntry[1], Double.parseDouble(fileEntry[6]), fileEntry[2], fileEntry[3],
                fileEntry[4], Integer.parseInt(fileEntry[5]));
    }

    private static MusicCD buildMusicCD(String[] fileEntry)
    {
        return new MusicCD(fileEntry[1], Double.parseDouble(fileEntry[8]), fileEntry[2],
                new Date(fileEntry[3]),fileEntry[4], fileEntry[5], Integer.parseInt(fileEntry[6]),
                fileEntry[7]);
    }

    private static Software buildSoftware(String[] fileEntry)
    {
        return new Software(fileEntry[1], Double.parseDouble(fileEntry[3]), fileEntry[2]);
    }

    public static Item build(String[] fileEntry)
    {
        String itemType = fileEntry[0];
        return TYPE_GENERATOR_MAP.get(itemType).execute(fileEntry);
    }
}
