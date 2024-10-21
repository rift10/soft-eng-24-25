package projects.records;

import java.util.ArrayList;

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

    public record Artist(String stageName, String realName, String[] albums, int age) {}

    // generic names for now, might fill out later
    private static final Album one = new Album("a", 10);
    private static final Album two = new Album("b", 11);
    private static final Album three = new Album("c", 12);
    private static final Album four = new Album("d", 13);
    private static final Album five = new Album("e", 14);

    private static final Artist artistOne = new Artist("A", "", new String[] {one.name(), two.name(), three.name()}, 0);
    private static final Artist artistTwo = new Artist("B", "", new String[] {four.name(), five.name()}, 0);

    private static final Album[] allAlbums = new Album[] {one, two, three, four, five};
    
    // lists to contain albums created by a certain artist
    private static final ArrayList<Album> listByOne = new ArrayList<>();
    private static final ArrayList<Album> listByTwo= new ArrayList<>();

    public static void main(String[] args) {
        for (Album album : allAlbums) {
            if (album.isCreatedBy(artistOne)) listByOne.add(album);
            if (album.isCreatedBy(artistTwo)) listByTwo.add(album);
        }

        System.out.println("albums by artist one:");
        for (Album album : listByOne) {
            System.out.println(album.name());
        }

        System.out.println("albums by artist two:");
        for (Album album : listByTwo) {
            System.out.println(album.name());
        }
    }
}
