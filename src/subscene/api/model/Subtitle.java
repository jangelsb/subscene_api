package subscene.api.model;

import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

import static subscene.api.utils.FileUtil.unzip;
import static subscene.api.utils.UrlUtil.*;

public class Subtitle {

    public static enum Type {Normal, HI, ForeignLangOnly}

    private String language  = "English";
    private String lanSuffix = "en";
    private Type type = Type.Normal;
    private File video = null;
    private boolean verbose = true;

    public Subtitle(File video) {
        this.video = video;
    }
    public Subtitle(File video, Type type) {
        this.video = video;
        this.type = type;
    }

    public Subtitle(File video, String language, String lanSuffix) {
        this.video = video;
        this.language  = language;
        this.lanSuffix = lanSuffix;
    }

    public Subtitle(File video, String language, String lanSuffix, Type type) {
        this.video = video;
        this.language  = language;
        this.lanSuffix = lanSuffix;
        this.type = type;
    }

    /**
     * Turns on or off print statements
     * @return a reference to this object, used for chaining
     */

    public Subtitle setVerbose(boolean verbose) {
        this.verbose = verbose;
        return this;
    }

    /**
     * Downloads the subtitle for the File passed into the constructor. The File doesn't need to be a real file, but it
     * DOES need to have a file extension, as of right now. TODO implement a way to pass just a name and not a file
     * @return the File of the new Subtitle if it found one, else null
     */
    public File download() {

        try {
            if(verbose) System.out.println("Searching subscene for: " + video.getName());
            String videoNamePure = video.getName().substring(0, video.getName().lastIndexOf("."));
            Document doc = getDocument("https://subscene.com/subtitles/release?q=" + video.getName());

            String downloadLink = null;

            switch (type) {

                case HI:
                    downloadLink = findFirstHISub(doc, videoNamePure);
                    break;

                case ForeignLangOnly:
                    downloadLink = findFirstForeignLangOnlySub(doc, videoNamePure);
                    break;

                // Normal
                default:
                    downloadLink = findFirstNormalSub(doc, videoNamePure);
            }

            if (downloadLink != null) {
                return downloadSub(videoNamePure, downloadLink);
            }

        } catch (Exception IOException) {
        }

        return null;
    }

    // TODO make each JSOUP call a function call for readability
    private String findFirstNormalSub(Document doc, String videoNamePure) throws IOException {
        // if HI doesn't matter, with no check,
        // link = doc.select("td.a1 span.l.r.neutral-icon:containsOwn(English)").first().parent().parent().select("td.a1 a").first().attr("abs:href");

        Elements allPossibleSubtitles = doc.select("td.a1 span.l.r.neutral-icon:containsOwn(" + language + ")");

        for (Element row : allPossibleSubtitles) {
            // the language info is three deep in the dom tree
            // if a41 exists, then it is an HI sub
            Elements candidate = row.parent().parent().parent().select("td.a40");
            if (!candidate.isEmpty()) {
                String link = candidate.first().parent().select("td.a1 a").first().attr("abs:href");
                Document doc2 = getDocument(link);

                // check if download page has the correct filename in the description
                boolean isCorrectSub = !doc2.select("li.release:contains(" + videoNamePure + ")").isEmpty();
                if(isCorrectSub) {
                    if(verbose) System.out.println("Download page: " + link);
                    return doc2.select("#downloadButton").first().attr("abs:href");
                }
            }
        }
        return null;
    }


    // returns the download link for the subtitle
    private String findFirstHISub(Document doc, String videoNamePure) throws IOException  {
        Elements allPossibleSubtitles = doc.select("td.a1 span.l.r.neutral-icon:containsOwn(" + language + ")");

        for (Element row : allPossibleSubtitles) {
            // the language info is three deep in the dom tree
            // if a41 exists, then it is an HI sub
            Elements candidate = row.parent().parent().parent().select("td.a41");
            if (!candidate.isEmpty()) {
                String link = candidate.first().parent().select("td.a1 a").first().attr("abs:href");
                Document doc2 = getDocument(link);
                // check if download page has the correct filename in the description
                boolean isCorrectSub = !doc2.select("li.release:contains(" + videoNamePure + ")").isEmpty();
                // check if download page has CHI in the description
                boolean hasCHI = !doc2.select("li.release:contains(CHI)").isEmpty();
                if(isCorrectSub && !hasCHI) {
                    if(verbose) System.out.println("Download page: " + link);
                    return doc2.select("#downloadButton").first().attr("abs:href");
                }
            }
        }
        return null;
    }

    private String findFirstForeignLangOnlySub(Document doc, String videoNamePure) throws IOException {

        Elements allPossibleSubtitles = doc.select("td.a1 span.l.r.neutral-icon:containsOwn(" + language + ")");

        for (Element row : allPossibleSubtitles) {
            // the language info is three deep in the dom tree
            // if a41 exists, then it is an HI sub
            String candidatePage = row.parent().parent().parent().select("td.a1 a").first().attr("abs:href");
            Document doc2 = getDocument(candidatePage);

            // check if candidate is Foreign Lang Only (FLO)
            boolean isFLO = !doc2.select("#downloadButton").first().select("span:contains(Foreign parts only").isEmpty();
            // check if download page has the correct filename in the description
            boolean isCorrectSub = !doc2.select("li.release:contains(" + videoNamePure + ")").isEmpty();
            // check if download page has CHI in the description
            boolean isCHI = !doc2.select("li.release:contains(CHI)").isEmpty();

            if(isFLO && isCorrectSub && !isCHI) {
                if(verbose) System.out.println("Download page: " + candidatePage);
                return doc2.select("#downloadButton").first().attr("abs:href");
            }
        }
        return null;
    }

    // TODO make functions for readability
    private File downloadSub(String videoNamePure, String downloadLink) throws IOException {

        // init folder variable
        String videoDirLoc = String.format("%s/tmp", video.getParent());
        File videoDir = new File(videoDirLoc);

        // download and unzip
        downloadFile(downloadLink, "subtitle.zip");
        unzip("subtitle.zip", videoDirLoc);

        // move and rename srt to the current directory
        File subtitle = videoDir.listFiles()[0];
        File toReturn = new File(String.format("%s/%s.%s.srt", video.getParent(), videoNamePure, lanSuffix));
        subtitle.renameTo(toReturn);

        // delete files
        new File("subtitle.zip").delete();
        FileUtils.deleteDirectory(videoDir);

        if(verbose) System.out.println("Subtitle: " + toReturn);

        return toReturn;
    }
}
