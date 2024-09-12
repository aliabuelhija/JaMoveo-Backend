package org.example.server.logic.services;

import org.example.server.logic.song.Song;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongService {

    public SongService() {}

    public List<Song> searchForSongs(String query) {
        List<Song> songResults = new ArrayList<>();

        try {
            String searchUrl = "https://www.tab4u.com/resultsSimple?tab=songs&q=" + query;
            Document doc = Jsoup.connect(searchUrl).get();
            Elements songElements = doc.select("div.recUpUnit.ruSongUnit");

            for (Element songElement : songElements) {
                String songTitle = songElement.select("div.aNameI19").text();
                String artistName = songElement.select("div.sNameI19").text().replaceAll("\\s*/\\s*$", "").trim();
                String backgroundStyle = songElement.select("span.ruArtPhoto").attr("style");
                String imageUrl = extractImageUrl(backgroundStyle);

                if (imageUrl.startsWith("/")) {
                    imageUrl = "https://www.tab4u.com" + imageUrl;
                }

                Song song = new Song(songTitle, artistName, imageUrl);
                songResults.add(song);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return songResults;
    }

    private String extractImageUrl(String styleAttr) {
        int start = styleAttr.indexOf("url(") + 4;
        int end = styleAttr.indexOf(")", start);
        return (start != -1 && end != -1) ? styleAttr.substring(start, end) : "";
    }

    private String getSongPageUrl(String songName, String artistName, boolean isLyrics) throws IOException {
        String searchUrl = "https://www.tab4u.com/resultsSimple?tab=songs&q=" + songName;
        Document doc = Jsoup.connect(searchUrl).get();
        Elements songElements = doc.select("div.recUpUnit.ruSongUnit");

        for (Element songElement : songElements) {
            String songTitle = songElement.select("div.sNameI19").text().replaceAll("\\s*/\\s*$", "").trim();
            String artistNameFromWeb = songElement.select("div.aNameI19").text().replaceAll("\\s*/\\s*$", "").trim();

            if (artistNameFromWeb.equals(artistName) && songTitle.equals(songName)) {
                String songPageUrl = songElement.select("a.ruSongLink").attr("href");
                songPageUrl = isLyrics ? songPageUrl.replace("tabs", "lyrics") : songPageUrl;
                return "https://www.tab4u.com/" + songPageUrl + "#song";
            }
        }
        return null;
    }

    private String getSongContent(String songPageUrl, String songTitle, String artistName) throws IOException {
        Document doc = Jsoup.connect(songPageUrl).get();
        Element songContent = doc.getElementById("songContentTPL");

        if (songContent != null) {
            String songHtml = songContent.html();
            return "<h1>" + songTitle + " - " + artistName + "</h1>" + songHtml;
        }
        return "No song content found";
    }

    public String getSongWithChords(String songName, String artistName) {
        try {
            String songPageUrl = getSongPageUrl(songName, artistName, false);
            if (songPageUrl != null) {
                return getSongContent(songPageUrl, songName, artistName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSongWithLyrics(String songName, String artistName) {
        try {
            String songPageUrl = getSongPageUrl(songName, artistName, true);
            if (songPageUrl != null) {
                return getSongContent(songPageUrl, songName, artistName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
