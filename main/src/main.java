import subscene.api.model.Subtitle;

import java.io.File;

public class main {
    public static void main(String args[]) {
        File video = new File("../moviemover/playground/import/The.Big.Bang.Theory.S08E22.720p.HDTV.X264-DIMENSION.mkv");
        Subtitle sub = new Subtitle(video, Subtitle.Type.HI);
        sub.download();
    }
}
