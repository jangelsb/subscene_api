# subscene_api
**This is an Unofficial Java API for Subscene.com** 

This is still a WIP. It currently only supports the HI (Hearing Impaired) mode. I will update this API soon with Normal and Foreign Language Only modes. 

### How does I use it? ###
Just download the lastest jar file from the [release page](https://github.com/jangelsb/subscene_api/releases) (or grab a [nightly](https://github.com/jangelsb/subscene_api/tree/master/out/artifacts/subscene_api_jar)) and import it into your project.

Example Usage:
```
import subscene.api.model.Subtitle;

import java.io.File;

public class main {
    public static void main(String args[]) {
        File video = new File("./The.Big.Bang.Theory.S08E22.720p.HDTV.X264-DIMENSION.mkv");
        Subtitle sub = new Subtitle(video, Subtitle.Type.HI);
        sub.download();
    }
}
```
Output:
``` 
Searching subscene for: The.Big.Bang.Theory.S08E22.720p.HDTV.X264-DIMENSION.mkv
Download page: https://subscene.com/subtitles/the-big-bang-theory-eighth-season/english/1103453
Subtitle: ./The.Big.Bang.Theory.S08E22.720p.HDTV.X264-DIMENSION.en.srt
```

Other constructors:
```
File video = new File("The.Big.Bang.Theory.S08E22.720p.HDTV.X264-DIMENSION.mkv");

// uses the defaults: "English" subtitle, "en" Filename Suffix, Subtitle Type: HI
Subtitle sub1 = new Subtitle(video);

// uses the defaults: "English" subtitle, "en" Filename Suffix
Subtitle sub2 = new Subtitle(video, Subtitle.Type.HI);

// uses the default Subtitle Type: HI
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


