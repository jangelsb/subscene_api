# subscene_api
**This is an Unofficial Java API for Subscene.com** 

This API supports Normal, HI (Hearing Impaired), and Foreign Language Only modes!

### How do I use it? ###
Just download the lastest jar file from the [release page](https://github.com/jangelsb/subscene_api/releases) (or grab a [nightly](https://github.com/jangelsb/subscene_api/tree/master/out/artifacts/subscene_api_jar)) and import it into your project.

Example Usage:
```
import subscene.api.model.Subtitle;

import java.io.File;

public class main {
    public static void main(String args[]) {
        File video = new File("./Whiskey.Tango.Foxtrot.2016.720p.BluRay.x264-DRONES.mkv");
        Subtitle sub = new Subtitle(video, Subtitle.Type.ForeignLangOnly);
        sub.download();
    }
}
```
Output:
``` 
Searching subscene for: Whiskey.Tango.Foxtrot.2016.720p.BluRay.x264-DRONES.mkv
Download page: https://subscene.com/subtitles/whiskey-tango-foxtrot/english/1356892
Subtitle: ./Whiskey.Tango.Foxtrot.2016.720p.BluRay.x264-DRONES.en.srt
```

Other constructors:
```
File video = new File("The.Big.Bang.Theory.S08E22.720p.HDTV.X264-DIMENSION.mkv");

// uses the defaults: "English" subtitle, "en" Filename Suffix, Subtitle Type: Normal
Subtitle sub1 = new Subtitle(video);

// uses the defaults: "English" subtitle, "en" Filename Suffix
Subtitle sub2 = new Subtitle(video, Subtitle.Type.HI);

// uses the default Subtitle Type: Normal
Subtitle sub3 = new Subtitle(video, "English", "en");

Subtitle sub4 = new Subtitle(video, "English", "en", Subtitle.Type.HI);
```

# Want to contribute? #

### Prerequisites ###
* [Intellij Community Version](https://www.jetbrains.com/idea/download/)
* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

### Setup ###

* Clone repository
* Open Intelij
* Open subscene_api project


