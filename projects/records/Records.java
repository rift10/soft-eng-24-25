package projects.records;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Records {
    
    public record Album(String name, int numSongs, String artist) {}

    public record Artist(String stageName, int age) {

    /**
     * @param minNumSongs the minimum number of songs to return true
     * @return true if this artist has released an album with at least the specified amount of songs
     */
    private boolean hasAlbumWithMinSongs(int minNumSongs) {
        return getListByArtist(this)
                .stream()
                .filter(a -> a.numSongs >= minNumSongs)
                .count() > 0;
    }
    
    private boolean hasAlbumStartingWithVowel() {
        return getListByArtist(this)
                .stream()
                // TODO: fix this
                .filter(a -> a.name.substring(0, 1).contains("aeiou"))
                .count() > 0;
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

    // arraylists for all albums and artists for looping through
    private static final List<Artist> allArtists = new ArrayList<>();
    private static final List<Album> allAlbums = new ArrayList<>();

    private static <T> List<T> filterList(List<T> initialList, Predicate<T> predicate) {
        return initialList.stream()
                .filter(predicate)
                .toList();
    }

    /**
     * @param artist the artist to get the albums created by
     * @return a list of the albums created by the specified artist
     */
    private static List<Album> getListByArtist(Artist artist) {
        return filterList(allAlbums, a -> a.artist.equals(artist.stageName));
    }

    /**
     * @param minAge the minimum age the artist has to be
     * @return a list of artists that are at least the specified number of years old
     */
    private static List<Artist> getArtistsWithMinAge(int minAge) {
        return filterList(allArtists, a -> a.age >= minAge);
    }

    /**
     * @param minNumSongs the minimum number of songs that needs to be on an albu
     * @return a list of the artists that have at least the specified amount of songs
     */
    private static List<Artist> getArtistsWithMinSongsOnAlbum(int minNumSongs) {
        return filterList(allArtists, a -> a.hasAlbumWithMinSongs(minNumSongs));
    }

    private static List<Artist> getArtistsWithAlbumsStartingWithVowels() {
        return filterList(allArtists, a -> a.hasAlbumStartingWithVowel());
    }

    public static void main(String[] args) {

        allArtists.add(artistOne);
        allArtists.add(artistTwo);

        allAlbums.add(one);
        allAlbums.add(two);
        allAlbums.add(three);
        allAlbums.add(four);
        allAlbums.add(five);

        System.out.println("albums by artist one:");
        for (Album album : getListByArtist(artistOne)) {
            System.out.println(album.name());
        }

        System.out.println("albums by artist two:");
        for (Album album : getListByArtist(artistTwo)) {
            System.out.println(album.name());
        }

        final int minAge = 20;
        System.out.println("artists that are at least " + minAge + " years old:");
        for (Artist artist : getArtistsWithMinAge(minAge)) {
            System.out.println(artist.stageName());
        }

        final int minNumSongs = 13;
        System.out.println("artists with at least " + minNumSongs + " songs on an album:");
        for (Artist artist : getArtistsWithMinSongsOnAlbum(minNumSongs)) {
            System.out.println(artist.stageName());
        }

        System.out.println("artists that have released albums starting with a vowel:");
        for (Artist artist : getArtistsWithAlbumsStartingWithVowels()) {
            System.out.println(artist.stageName());
        }
    }
}
