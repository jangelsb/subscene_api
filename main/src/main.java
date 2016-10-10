import subscene.api.model.Subtitle;

import java.io.File;

public class main {
    public static void main(String args[]) {
        File video = new File("./Whiskey.Tango.Foxtrot.2016.720p.BluRay.x264-DRONES.mkv");
        Subtitle sub = new Subtitle(video, Subtitle.Type.ForeignLangOnly);
        sub.setVerbose(false);
        sub.download();
    }
}
