package projects.records;

import java.util.ArrayList;
import java.util.List;

public class Records {
    
    public record Album(String name, int numSongs) {

        private boolean isCreatedBy(Artist artist) {
            // to avoid null errors
            if (artist.albums() == null) return false;
            for (String i : artist.albums()) {
                if (i.equals(this.name())) return true;
            }
            return false; 
        }
    }

    public record Artist(String stageName, String realName, String[] albums, int age) {

        /**
         * @param minNumSongs the minimum number of songs to return true
         * @return true if this artist has released an album with at least the specified amount of songs
         */
        private boolean hasAlbumWithMinSongs(int minNumSongs) {
            var albumsByArtist = getListByArtist(this);
            for (Album album : albumsByArtist) {
                if (album.numSongs >= minNumSongs) return true;
            }
            return false;
        }
    }

    // generic names for now, might fill out later
    private static final Album one = new Album("a", 10);
    private static final Album two = new Album("b", 11);
    private static final Album three = new Album("c", 12);
    private static final Album four = new Album("d", 13);
    private static final Album five = new Album("e", 14);

    private static final Artist artistOne = new Artist("A", "", new String[] {one.name(), two.name(), three.name()}, 0);
    private static final Artist artistTwo = new Artist("B", "", new String[] {four.name(), five.name()}, 0);

    // arrays for all albums and artists for looping through
    private static final Album[] allAlbums = new Album[] {one, two, three, four, five};
    private static final Artist[] allArtists = new Artist[] {artistOne, artistTwo};
    
    // lists to contain albums created by a certain artist
    private static List<Album> listByOne = new ArrayList<>();
    private static List<Album> listByTwo= new ArrayList<>();

    /**
     * @param artist the artist to get the albums created by
     * @return an arraylist of the albums created by the specified artist
     */
    private static List<Album> getListByArtist(Artist artist) {
        var result = new ArrayList<Album>();
        for (Album album : allAlbums) {
            if (album.isCreatedBy(artist)) result.add(album);
        }
        return result;
    }

    public static void main(String[] args) {

        listByOne = getListByArtist(artistOne);
        listByTwo = getListByArtist(artistTwo);

        System.out.println("albums by artist one:");
        for (Album album : listByOne) {
            System.out.println(album.name());
        }

        System.out.println("albums by artist two:");
        for (Album album : listByTwo) {
            System.out.println(album.name());
        }

        final int minNumSongs = 13;
        System.out.println("artists with at least " + minNumSongs + " songs on an album:");
        for (Artist artist : allArtists) {
            if (artist.hasAlbumWithMinSongs(minNumSongs)) System.out.println(artist.stageName());
        }
    }
}
