package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;



/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHeroLite {
    public static final double CONCERT_A = 440.0;
    public static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);
    public static final double[] notes = new double[37];
    public static final GuitarString[] strings = new GuitarString[37];


    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        for(int i = 0; i < 37; i++){
            notes[i] = 440 * Math.pow(2, ((double) (i - 24) / 12));
            strings[i] = new GuitarString(notes[i]);
        }
        GuitarString stringA = new GuitarString(CONCERT_A);
        GuitarString stringC = new GuitarString(CONCERT_C);

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();

                if (key == 'a') {
                    stringA.pluck();
                } else if (key == 'c') {
                    stringC.pluck();
                }else if (key == 'q') {
                    strings[0].pluck();
                }

                String keyboards = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
                int t;
                if(keyboards.indexOf(key) != -1){
                    t = keyboards.indexOf(key);
                }else continue;
                strings[t].pluck();
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for(int i = 0; i < strings.length; i++){
                sample += strings[i].sample();
            }


            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for(int i = 0; i < strings.length; i++){
                strings[i].tic();
            }

        }
    }
}

