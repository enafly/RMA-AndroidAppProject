package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;


import java.util.HashMap;

public class GenreFactory {
    private static HashMap<String, Integer> genres;

    static {
        genres = new HashMap<>();
        genres.put("folk", R.drawable.folk);
        genres.put("house", R.drawable.house);
        genres.put("metal", R.drawable.metal);
        genres.put("pop", R.drawable.pop);
        genres.put("punk", R.drawable.punk);
        genres.put("classical", R.drawable.classical);
    }

    public static int GetImageResForGenre(String genre) {
        genre = genre.toLowerCase();
        if ( genres.containsKey(genre) ) return genres.get(genre);

        return R.drawable.download;
    }
}
