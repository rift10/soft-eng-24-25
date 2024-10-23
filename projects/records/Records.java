package projects.records;

import java.util.Arrays;
import java.util.List;

public class Records {
    
    public record Album(String name, int numSongs, String artist) {}

    public record Artist(String stageName, int age) {

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
    private static final Artist artistOne = new Artist("A", 25);
    private static final Artist artistTwo = new Artist("B", 35);

    private static final Album one = new Album("a", 10, artistOne.stageName);
    private static final Album two = new Album("b", 11, artistOne.stageName);
    private static final Album three = new Album("c", 12, artistOne.stageName);
    private static final Album four = new Album("d", 13, artistTwo.stageName);
    private static final Album five = new Album("e", 14, artistTwo.stageName);

    // arrays for all albums and artists for looping through
    private static final Artist[] allArtists = new Artist[] {artistOne, artistTwo};
    private static final Album[] allAlbums = new Album[] {one, two, three, four, five};
    
    /**
     * @param artist the artist to get the albums created by
     * @return a list of the albums created by the specified artist
     */
    private static List<Album> getListByArtist(Artist artist) {
        return Arrays.stream(allAlbums)
                .filter(a -> a.artist.equals(artist.stageName))
                .toList();
    }

    /**
     * @param minAge the minimum age the artist has to be
     * @return a list of artists that are at least the specified number of years old
     */
    private static List<Artist> getArtistsWithMinAge(int minAge) {
        return Arrays.stream(allArtists)
                .filter(a -> a.age >= minAge)
                .toList();
    }

    /**
     * @param minNumSongs the minimum number of songs that needs to be on an albu
     * @return a list of the artists that have at least the specified amount of songs
     */
    private static List<Artist> getArtistsWithMinSongsOnAlbum(int minNumSongs) {
        return Arrays.stream(allArtists)
                .filter(a -> a.hasAlbumWithMinSongs(minNumSongs))
                .toList();
    }

    public static void main(String[] args) {

        System.out.println("albums by artist one:");
        for (Album album : getListByArtist(artistOne)) {
            System.out.println(album.name());
        }

        System.out.println("albums by artist two:");
        for (Album album : getListByArtist(artistTwo)) {
            System.out.println(album.name());
        }

        final int minAge = 30;
        System.out.println("artists that are at least " + minAge + " years old:");
        for (Artist artist : getArtistsWithMinAge(minAge)) {
            System.out.println(artist.stageName());
        }

        final int minNumSongs = 13;
        System.out.println("artists with at least " + minNumSongs + " songs on an album:");
        for (Artist artist : getArtistsWithMinSongsOnAlbum(minNumSongs)) {
            System.out.println(artist.stageName());
        }
    }
}
